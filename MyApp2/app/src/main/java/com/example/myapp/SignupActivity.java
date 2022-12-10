package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button toLoginButton;
    private Button signupButton;

    private boolean isAllFieldsChecked = false;

    DatabaseHelper dbHelper = new DatabaseHelper(SignupActivity.this);

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

                isAllFieldsChecked = CheckAllFields();

                if (isAllFieldsChecked) {
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

    //TODO: all validation for password woth regex
    private boolean CheckAllFields() {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(passwordEditText.getText().toString());

        if (usernameEditText.length() == 0) {
            usernameEditText.setError("This field is required");
            return false;
        }
        else if(dbHelper.findUserByUsername(usernameEditText.getText().toString())) {
                usernameEditText.setError("Username already exist");
                return false;
        }

        if (passwordEditText.length() == 0) {
            passwordEditText.setError("Password is required");
            return false;
        }
        // else if (passwordEditText.length() < 8) {
//            passwordEditText.setError("Password must be minimum 8 characters");
//            return false;
//        }
        else if(passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
            passwordEditText.setError("Passwords doesn't match");
            return false;
        }

        return true;
    }
}
