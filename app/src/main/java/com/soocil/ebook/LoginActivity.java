package com.soocil.ebook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    EditText usernameInput, passwordInput;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            try {
                String enteredUsername = usernameInput.getText().toString().trim();
                String enteredPassword = passwordInput.getText().toString().trim();

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enteredUsername.equals(USERNAME) && enteredPassword.equals(PASSWORD)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
