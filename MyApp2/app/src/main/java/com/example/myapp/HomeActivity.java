package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements Serializable {

    private TextView helloUserTextView;
    private ListView notesListView;
    private Button createNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helloUserTextView = findViewById(R.id.helloUserTextView);

        Intent intent = getIntent();
        helloUserTextView.setText("Hello " + intent.getStringExtra("username") + "!");

        notesListView = findViewById(R.id.notesListView);
        createNoteButton = findViewById(R.id.createNoteButton);

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);
                startActivity(myIntent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);

        ArrayList<Note> notes = dbHelper.getAllNotes();

        ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<Note>
                (this, android.R.layout.simple_list_item_1, notes);

        notesListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }
}
