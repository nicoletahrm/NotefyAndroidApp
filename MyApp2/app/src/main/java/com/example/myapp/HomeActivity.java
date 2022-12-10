package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView helloUserTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloUserTextView = findViewById(R.id.helloUserTextView);
        Intent intent = getIntent();
        helloUserTextView.setText("Hello " + intent.getStringExtra("username") + "!");

    }
}
