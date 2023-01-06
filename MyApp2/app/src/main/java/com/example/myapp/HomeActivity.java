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

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements Serializable {

    private ListView notesListView;
    private Button createNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        notesListView = findViewById(R.id.notesListView);
        createNoteButton = findViewById(R.id.createNoteButton);

        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);
                myIntent.putExtra("isNoteSelected", false);
                myIntent.putExtra("createButtonClick", true);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                finish();
                startActivity(myIntent);
            }
        });

        DatabaseHelper dbHelper = new DatabaseHelper(HomeActivity.this);

        ArrayList<Note> notes = dbHelper.getAllNotes();
        ArrayAdapter<Note> arrayAdapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);

        notesListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note selectedNote = (Note) adapterView.getItemAtPosition(i);
                Intent myIntent = new Intent(HomeActivity.this, NoteActivity.class);

                myIntent.putExtra("note", (Parcelable) selectedNote);
                myIntent.putExtra("isNoteSelected", true);
                myIntent.putExtra("createButtonClick", false);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                finish();
                startActivity(myIntent);
            }
        });
    }
}
