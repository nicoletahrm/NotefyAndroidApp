package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapp.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String userTable = "user";
    public static final String usernameColumn = "username";
    public static final String passwordColumn = "password";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "app_db", null, 1);
    }

    //create database one time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement =
                "CREATE TABLE " + userTable
                        + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + usernameColumn + " TEXT, "
                        + passwordColumn + " TEXT);";

        db.execSQL(createTableStatement);
    }

    //update database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //add new user for signup
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(usernameColumn, user.getUsername());
        cv.put(passwordColumn, user.getPassword());

        long insert = db.insert(userTable, null, cv);

        if(insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    //find existing user for login
    public boolean findUserByUsernameAndPassword(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + userTable
                + " WHERE username = \""
                + user.getUsername()
                + "\" and password = \""
                + user.getPassword()
                + "\";";

        Boolean exists = false;
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

        String query = "SELECT * FROM " + userTable
                + " WHERE username = \""
                + username
                + "\";";

        Boolean exists = false;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            exists = true;
        }

        cursor.close();
        db.close();

        return exists;
    }
}
