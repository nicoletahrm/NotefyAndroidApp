package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;

import java.io.Serializable;
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
        setContentView(R.layout.activity_note);

        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        dateTextView = findViewById(R.id.dateTextView);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        String currentDate = currentDateToString();

        dateTextView.setText(currentDate);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper dbHelper = new DatabaseHelper(NoteActivity.this);

                Note newNote = new Note();
                newNote.setTitle(titleEditText.getText().toString());
                newNote.setContent(contentEditText.getText().toString());
                newNote.setDate(currentDate);

                dbHelper.addNote(newNote);

                Intent intent = new Intent(NoteActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public String currentDateToString() {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(currentDate);
    }
}
