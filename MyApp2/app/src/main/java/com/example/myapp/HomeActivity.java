package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;
import com.example.myapp.Models.User;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements Serializable {

    private TextView helloUserTextView;
    private ListView notesListView;
    private Button createNoteButton;
    public Boolean isNoteSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        helloUserTextView = findViewById(R.id.helloUserTextView);

        notesListView = findViewById(R.id.notesListView);
        createNoteButton = findViewById(R.id.createNoteButton);

        Intent intent = getIntent();
        User u = (User)intent.getParcelableExtra("user");
        helloUserTextView.setText(String.format("Hello %s!", u.getUsername()));

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);
                startActivity(myIntent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);

        ArrayList<Note> notes = dbHelper.getAllNotes();
        ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);

        notesListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        //notesListView.getSelectedItemId();

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note selectedNote = (Note) adapterView.getItemAtPosition(i);
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);

                isNoteSelected = true;
                myIntent.putExtra("note", (Parcelable) selectedNote);
                myIntent.putExtra("isNoteSelected", isNoteSelected);
                startActivity(myIntent);
            }
        });
    }
}
