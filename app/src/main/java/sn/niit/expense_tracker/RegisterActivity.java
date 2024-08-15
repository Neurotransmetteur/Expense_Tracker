package sn.niit.expense_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sn.niit.expense_tracker.utils.DbConnect;

public class RegisterActivity extends AppCompatActivity {
    EditText eusername, eemail, epassword;
    Button register;
    boolean isAllFields = false;
    DbConnect dbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbConnect = new DbConnect(this); // Initialize the database connection

        eusername = findViewById(R.id.username);
        eemail = findViewById(R.id.email);
        epassword = findViewById(R.id.password);
        register = findViewById(R.id.regibutton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFields = register();
                if (isAllFields) {
                    String username = eusername.getText().toString();
                    String password = epassword.getText().toString();
                    if (dbConnect.addUser(username, password)) { // Save user to database
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        eusername.setText("");
                        eemail.setText("");
                        epassword.setText("");
                        // Navigate to the login screen
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//                        Intent
//                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean register() {
        String username = eusername.getText().toString().trim();
        String email = eemail.getText().toString().trim();
        String password = epassword.getText().toString().trim();
        if (checkAllFields(username, email, password)) {
            return true;
        }
        return false;
    }

    private boolean checkAllFields(String username, String email, String password) {
        if (TextUtils.isEmpty(username)) {
            eusername.setError("Please enter username");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            eemail.setError("Please enter a valid email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            epassword.setError("Please enter a valid password");
            return false;
        }
        return true;
    }
}
