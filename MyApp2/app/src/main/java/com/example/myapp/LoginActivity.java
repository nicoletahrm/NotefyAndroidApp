package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Models.User;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button toSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        toSignupButton = findViewById(R.id.toSignupButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);

                User user = new User();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                if(userValidation(user)) {
                    Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    myIntent.putExtra("username", usernameEditText.getText().toString());
                    Toast.makeText(getApplicationContext(),"You are connected", Toast.LENGTH_SHORT).show();
                    startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private Boolean userValidation(User user) {
        DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);

        if(user.getUsername().length() == 0) {
            usernameEditText.setError("This field is required");
            return false;
        }
        else if(!dbHelper.findUserByUsername(user.getUsername())) {
            usernameEditText.setError("Invalid username");
            return false;
        }

        if(!dbHelper.findUserByUsernameAndPassword(user)) {
            passwordEditText.setError("Invalid password");
            return false;
        }

        return true;
    }
}