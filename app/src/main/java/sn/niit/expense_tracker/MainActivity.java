package sn.niit.expense_tracker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import sn.niit.expense_tracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//            switch (item.getItemId()) {
//                case (id == R.id.home):
//                    replaceFragment(new HomeFragment());
//                    break;
//                case R.id.fab: replaceFragment(new AddExpenseFragment());
//                    break;
////                case R.id.subscriptions:
////                    replaceFragment(new SubscriptionFragment());
////                    break;
////                case R.id.library:
////                    replaceFragment(new LibraryFragment());
////                    break;
//            }
            return true;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionBtn);

       // Set the OnClickListener
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               Toast.makeText(MainActivity.this, "FAB Clicked", Toast.LENGTH_SHORT).show();
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