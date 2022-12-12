package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.User;

public class HomeActivity extends AppCompatActivity {

    private TextView helloUserTextView;
    private Button createNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloUserTextView = findViewById(R.id.helloUserTextView);
        createNoteButton = findViewById(R.id.createNoteButton);

        Intent intent = getIntent();
        helloUserTextView.setText("Hello " + intent.getStringExtra("username") + "!");

        createNoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
