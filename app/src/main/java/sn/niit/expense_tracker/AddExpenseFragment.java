package sn.niit.expense_tracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;


public class AddExpenseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        Spinner categorySpinner = view.findViewById(R.id.spinner_category);

        // Find and set up the Toolbar
        Toolbar myToolbar = view.findViewById(R.id.my_toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);

            if (categorySpinner != null) {
                // Example categories
                String[] categories = {"Bill & Utility", "Groceries", "Transport", "Entertainment", "Other"};

                // Creating an ArrayAdapter with the string array and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                categorySpinner.setAdapter(adapter);
            }
        }

        return view;
    }
}
