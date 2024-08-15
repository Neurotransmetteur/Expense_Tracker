package sn.niit.expense_tracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sn.niit.expense_tracker.domain.User;
import sn.niit.expense_tracker.utils.DbConnect;
import sn.niit.expense_tracker.utils.SessionManager;

public class LoginActivity extends Activity {
    EditText username, password;
    Button eregister, elogin;
    DbConnect dbConnect;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        dbConnect = new DbConnect(this); // Initialize the database connection
        sessionManager = new SessionManager(this); // Initialize the session manager

        eregister = findViewById(R.id.register1);
        elogin = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        Intent i = getIntent();
        String a = i.getStringExtra("number1");
        String b = i.getStringExtra("number2");
        username.setText(a != null ? a : "");
        password.setText(b != null ? b : "");

        eregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(in);
            }
        });

        elogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = username.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();

                User user = dbConnect.verifyUser(usernameInput, passwordInput);

                if (user != null) {
                    // Set login state and store user info
                    sessionManager.setLogin(true);
                    sessionManager.setUsername(user.getUsername());
                    sessionManager.setUserId(user.getId());
                    sessionManager.setCurrentBalance(user.getCurrentBalance());

                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Start MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
