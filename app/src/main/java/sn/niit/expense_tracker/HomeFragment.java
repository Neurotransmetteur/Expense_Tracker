package sn.niit.expense_tracker;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sn.niit.expense_tracker.domain.Transaction;
import sn.niit.expense_tracker.utils.DbConnect;
import sn.niit.expense_tracker.utils.SessionManager;

public class HomeFragment extends Fragment {
    TextView userBalanceText;
    SessionManager sessionManager;
    ImageButton showBalanceBottomSheetBtn;
    DbConnect dbConnect;
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userBalanceText = view.findViewById(R.id.balanceAmount);
        showBalanceBottomSheetBtn = view.findViewById(R.id.btn_add_balance);
        sessionManager = new SessionManager(requireContext()); // Initialize the session manager
        dbConnect = new DbConnect(requireContext()); // Initialize the DbConnect
        recyclerView = view.findViewById(R.id.recyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionList = new ArrayList<>();
        adapter = new TransactionAdapter(transactionList);
        recyclerView.setAdapter(adapter);


        if (sessionManager.isLoggedIn()) {

            // Update the user balance display
            updateBalance();

            // Retrieve and display transactions
            loadTransactions();

            showBalanceBottomSheetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
                    View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_layout, null);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();

                    TextInputLayout textInputLayout = bottomSheetView.findViewById(R.id.textFieldLayout);
                    TextInputEditText editText = bottomSheetView.findViewById(R.id.editTextBalance);
                    MaterialButton dismissBtn = bottomSheetView.findViewById(R.id.btn_add_balance);

                    dismissBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Objects.requireNonNull(editText.getText()).toString().isEmpty()) {
                                textInputLayout.setError("Please enter an amount");
                            } else {
                                double amount = Double.parseDouble(editText.getText().toString());
                                int userId = sessionManager.getUserId(); // Assume this method exists
                                String category = "revenue"; // Example category
                                String description = "Salary"; // Example description

                                // Create the transaction as income
                                dbConnect.createTransaction(userId, amount, "income", category, description);

                                double userBalance = dbConnect.getCurrentBalance(userId);


                                // Refresh the balance in the session manager
                                sessionManager.setCurrentBalance(userBalance);

                                // Update the balance displayed in the UI
                                userBalanceText.setText(sessionManager.getCurrentBalance() + " FCFA");

                                Toast.makeText(requireContext(), "Transaction recorded", Toast.LENGTH_SHORT).show();

                                bottomSheetDialog.dismiss();
                            }
                        }
                    });


                    bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
//                            Toast.makeText(requireContext(), "Ajout annulé", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }

    private void updateBalance() {
        if (userBalanceText != null) {
            userBalanceText.setText(sessionManager.getCurrentBalance() + " FCFA");
        }
    }

    private void loadTransactions() {
        int userId = sessionManager.getUserId();  // Assume this method exists in SessionManager
        transactionList.clear();
        transactionList.addAll(dbConnect.getTransactions(userId));
        adapter.notifyDataSetChanged();
    }

//    public static void exposeLoadTransactions(){
//        loadTransactions();
//    }
}
