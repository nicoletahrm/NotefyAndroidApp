package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private TextView dateTextView;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        dateTextView = findViewById(R.id.dateTextView);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        String currentDate = currentDateToString();
        dateTextView.setText(currentDate);

        Intent myIntent = getIntent();

        boolean isNoteSelected = (boolean)myIntent.getBooleanExtra("isNoteSelected", false);
        Note note = (Note) myIntent.getParcelableExtra("note");

        if(isNoteSelected) {
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
            dateTextView.setText(currentDate);
        }

        String s = Boolean.valueOf(isNoteSelected).toString();
        Log.d("isNoteSelected", s);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(NoteActivity.this);

                if (isNoteSelected) {
                    dbHelper.updateNote(note.getId(), titleEditText.getText().toString(), contentEditText.getText().toString(), dateTextView.getText().toString());
                } else {
                    Note newNote = new Note();

                    newNote.setTitle(titleEditText.getText().toString());
                    newNote.setContent(contentEditText.getText().toString());
                    newNote.setDate(currentDate);

                    dbHelper.addNote(newNote);
                }

                finish();

                Intent intent = new Intent(NoteActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public String currentDateToString() {
        Date currentDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
    }
}
