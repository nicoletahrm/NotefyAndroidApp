package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapp.Models.Note;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.dp";
    public static final int DB_VERSION = 1;

    public static final String NOTE_TABLE = "note";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";
    public static final String DATE_COLUMN = "date";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create database one time
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createNoteTable =
                "CREATE TABLE " + NOTE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COLUMN + " TEXT, "
                + CONTENT_COLUMN + " TEXT, "
                + DATE_COLUMN + " TEXT);";

        db.execSQL(createNoteTable);
    }

    //update database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        onCreate(db);
    }

    //add new note
    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvNote = new ContentValues();

        cvNote.put(TITLE_COLUMN, note.getTitle());
        cvNote.put(CONTENT_COLUMN, note.getContent());
        cvNote.put(DATE_COLUMN, note.getDate());

        long insert = db.insert(NOTE_TABLE, null, cvNote);

        return insert != -1;
    }

    //return notes list
    public ArrayList<Note> getAllNotes() {

        ArrayList<Note> notes = new ArrayList<Note>();

        String query =
                "SELECT * FROM "
                + NOTE_TABLE
                + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Integer noteId = cursor.getInt(0);
                String noteTitle = cursor.getString(1);
                String noteContent = cursor.getString(2);
                String noteDate = cursor.getString(3);

                Note note = new Note(noteId, noteTitle, noteContent, noteDate);
                notes.add(note);

            }while(cursor.moveToNext());
        }
        else {
        }

        cursor.close();
        db.close();

        return notes;
    }

    //update a note
    public boolean updateNote(Integer noteId, String noteTitle, String noteContent, String noteDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TITLE_COLUMN, noteTitle);
        cv.put(CONTENT_COLUMN, noteContent);
        cv.put(DATE_COLUMN, noteDate);

        long update = db.update(NOTE_TABLE, cv, "ID=?", new String[] { noteId.toString() } );

        db.close();

        return update == -1;
    }

    //delete a note
    public boolean deleteNote(Integer noteId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(NOTE_TABLE, "ID=?", new String[]{ noteId.toString() } );

        db.close();

        return delete == -1;
    }

    //find
    public ArrayList<Note> findNotes(String title) {
        ArrayList<Note> notes = new ArrayList<Note>();

        String query =
                "SELECT * FROM "
                        + NOTE_TABLE
                        + " WHERE "
                        + TITLE_COLUMN
                        + " LIKE \"%"
                        + title
                        + "%\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Integer noteId = cursor.getInt(0);
                String noteTitle = cursor.getString(1);
                String noteContent = cursor.getString(2);
                String noteDate = cursor.getString(3);

                Note note = new Note(noteId, noteTitle, noteContent, noteDate);
                notes.add(note);

            }while(cursor.moveToNext());
        }
        else {
        }

        cursor.close();
        db.close();

        return notes;
    }
}
