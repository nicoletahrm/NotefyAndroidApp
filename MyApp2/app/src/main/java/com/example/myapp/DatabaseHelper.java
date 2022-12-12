package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import com.example.myapp.Models.Note;
import com.example.myapp.Models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notes.dp";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";

    public static final String NOTE_TABLE = "note";
    public static final String TITLE_COLUMN = "title";
    public static final String CONTENT_COLUMN = "content";
    public static final String DATE_COLUMN = "date";
    //public static final Boolean IS_PINNED_COLUMN = "isPinned";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //create database one time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable =
                "CREATE TABLE " + USER_TABLE
                        + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + USERNAME_COLUMN + " TEXT, "
                        + PASSWORD_COLUMN + " TEXT);";

        String createNoteTable =
                "CREATE TABLE " + NOTE_TABLE
                + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COLUMN + " TEXT, "
                + CONTENT_COLUMN + " TEXT, "
                + DATE_COLUMN + " TEXT);";

        db.execSQL(createUserTable);
        db.execSQL(createNoteTable);
    }

    //update database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        onCreate(db);
    }

    //add new user for signup
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvUser = new ContentValues();

        cvUser.put(USERNAME_COLUMN, user.getUsername());
        cvUser.put(PASSWORD_COLUMN, user.getPassword());

        long insert = db.insert(USER_TABLE, null, cvUser);

        return insert != -1;
    }

    //add new user for signup
    public boolean addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvNote = new ContentValues();

        cvNote.put(TITLE_COLUMN, note.getTitle());
        cvNote.put(CONTENT_COLUMN, note.getContent());
        cvNote.put(DATE_COLUMN, note.getDate());

        long insert = db.insert(NOTE_TABLE, null, cvNote);

        return insert != -1;
    }

    //find existing user for login
    public boolean findUserByUsernameAndPassword(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + USER_TABLE
                + " WHERE username = \""
                + user.getUsername()
                + "\" and password = \""
                + user.getPassword()
                + "\";";

        boolean exists = false;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            exists = true;
        }

        cursor.close();
        db.close();

        return exists;
    }

    public boolean findUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + USER_TABLE
                + " WHERE username = \""
                + username
                + "\";";

        boolean exists = false;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            exists = true;
        }

        cursor.close();
        db.close();

        return exists;
    }

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
                String noteTitle = cursor.getString(1);
                String noteContent = cursor.getString(2);
                String noteDate = cursor.getString(3);

                Note note = new Note(noteTitle, noteContent, noteDate);
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
