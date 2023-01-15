package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.Models.Note;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ListView searchListView;
    private Button searchNoteButton;
    private EditText searchNoteEditText;
    private ArrayList<Note> notes = new ArrayList<>();
    private ArrayAdapter<Note> adapter;

    private DatabaseHelper dbHelper = new DatabaseHelper(SearchActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchListView = findViewById(R.id.searchListView);
        searchNoteButton = findViewById(R.id.searchNoteButton);
        searchNoteEditText = findViewById(R.id.searchNoteEditText);

        adapter = new ArrayAdapter<Note>(this, R.layout.item_list, notes);

        searchListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = searchNoteEditText.getText().toString();
                Log.d("SEARCHTEXT", title);

                notes = dbHelper.findNotes(title);
                adapter = new ArrayAdapter<Note>(SearchActivity.this, R.layout.item_list, notes);

                searchListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note selectedNote = (Note) adapterView.getItemAtPosition(i);
                Intent myIntent = new Intent(SearchActivity.this, NoteActivity.class);

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
