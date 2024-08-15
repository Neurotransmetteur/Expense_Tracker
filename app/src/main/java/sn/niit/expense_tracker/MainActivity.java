package sn.niit.expense_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import sn.niit.expense_tracker.databinding.ActivityMainBinding;
import sn.niit.expense_tracker.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SessionManager
        sessionManager = new SessionManager(this);

        // Check if the user is logged in
        if (!sessionManager.isLoggedIn()) {
            // User is not logged in, redirect to login activity
            Log.d(TAG, "onCreate: User not logged in, redirecting to LoginActivity");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close MainActivity so the user cannot return to it
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate: MainActivity started");

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Log.d(TAG, "onItemSelected: itemId = " + id);

            if (id == R.id.home) {
                Log.d(TAG, "onItemSelected: Home selected");
                replaceFragment(new HomeFragment());

            } else if (id == R.id.fab) {
                Log.d(TAG, "onItemSelected: FAB selected");
                replaceFragment(new AddExpenseFragment());

            }
            return true;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionBtn);

        // Set the OnClickListener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddExpenseFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
