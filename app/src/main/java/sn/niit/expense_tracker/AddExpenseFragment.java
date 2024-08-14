package sn.niit.expense_tracker;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.text.DateFormat;
import java.util.Calendar;

public class AddExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

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
}
