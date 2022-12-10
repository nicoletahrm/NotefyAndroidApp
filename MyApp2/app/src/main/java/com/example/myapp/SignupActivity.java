package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.User;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button toLoginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        toLoginButton = findViewById(R.id.toLoginButton);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            User user;

            @Override
            public void onClick(View view) {
                try {
                    user = new User(1, usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    Toast.makeText(SignupActivity.this, user.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_LONG).show();
                    user = new User(-1, "error", "error");
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(SignupActivity.this);

                boolean success = databaseHelper.addUser(user);
                Toast.makeText(SignupActivity.this, "Success = " + success, Toast.LENGTH_LONG).show();
            }
        });

        toLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void validate(String username, String password) {

    }
}
