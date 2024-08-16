package sn.niit.expense_tracker;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.util.Calendar;

import sn.niit.expense_tracker.domain.Transaction;
import sn.niit.expense_tracker.utils.DbConnect;
import sn.niit.expense_tracker.utils.SessionManager;

public class AddExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private TextView datePicker;
    private Spinner categorySpinner;
    private EditText descriptionEditText;
    private EditText totalEditText;
    private Button saveExpenseButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Initialize views
        datePicker = view.findViewById(R.id.tv_date_picker);
        categorySpinner = view.findViewById(R.id.spinner_category);
        descriptionEditText = view.findViewById(R.id.et_description);
        totalEditText = view.findViewById(R.id.et_total);
        saveExpenseButton = view.findViewById(R.id.save_expense_btn);

        saveExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });

        // Set up the spinner
        Spinner categorySpinner = view.findViewById(R.id.spinner_category);
        setupCategorySpinner(categorySpinner);

        // Set up the date picker button
        ImageButton datePickerBtn = view.findViewById(R.id.btn_date_picker);
        setupDatePickerButton(datePickerBtn);

        // Set up the Toolbar
        setupToolbar(view);

        return view;
    }

    private void setupCategorySpinner(Spinner categorySpinner) {
        if (categorySpinner != null) {
            // Example categories
            String[] categories = {"Facture et services publics", "Logement", "Nourriture", "Santé", "Transports", "Divertissement", "Autres"};

            // Creating an ArrayAdapter with the string array and a default spinner layout
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            categorySpinner.setAdapter(adapter);
        }
    }

    private void setupDatePickerButton(ImageButton datePickerBtn) {
        if (datePickerBtn != null) {
            datePickerBtn.setOnClickListener(v -> {
                DialogFragment datePicker = new DatePickerFragment(AddExpenseFragment.this);
                datePicker.show(requireActivity().getSupportFragmentManager(), "datePicker");
            });
        }
    }

    private void setupToolbar(View view) {
        Toolbar myToolbar = view.findViewById(R.id.my_toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        String pickedDate = DateFormat.getDateInstance().format(calendar.getTime());

        TextView datePickerTextView = requireActivity().findViewById(R.id.tv_date_picker);
        if (datePickerTextView != null) {
            datePickerTextView.setText(pickedDate);
        }
    }

    private void saveTransaction() {
        String date = datePicker.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString();
        String totalString = totalEditText.getText().toString();

        // Validate required fields
        if (TextUtils.isEmpty(date) || TextUtils.isEmpty(category) || TextUtils.isEmpty(totalString)) {
            Toast.makeText(getContext(), "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = Double.parseDouble(totalString);

        // Assuming it's an expense; you could add logic to toggle between expense and income
        Transaction.TransactionType type = Transaction.TransactionType.EXPENSE;

        // Create the transaction
        Transaction transaction = new Transaction(date, category, description, total, type);

        // Access the database
        DbConnect dbConnect = new DbConnect(getContext());
        SessionManager sessionManager = new SessionManager(getContext());

        // Get the current user ID from the session
        int userId = sessionManager.getUserId();

        // Save the transaction to the database
        dbConnect.createTransaction(userId, transaction.getTotal(),transaction.getType().toString(),transaction.getCategory(),transaction.getDescription(),transaction.getDate());

        // Update the user's balance in the database
        double newBalance = dbConnect.getCurrentBalance(userId);

        // Update the balance in the session manager
        sessionManager.setCurrentBalance(newBalance);

//        HomeFragment.exposeLoadTransactions();

        // Show a success message
        Toast.makeText(getContext(), "Transaction saved: " + transaction.getCategory(), Toast.LENGTH_SHORT).show();

        // Optionally, you can reset the input fields or navigate back
        resetFields();
    }

    private void resetFields() {
        datePicker.setText("");
        categorySpinner.setSelection(0);
        descriptionEditText.setText("");
        totalEditText.setText("");
    }

}
