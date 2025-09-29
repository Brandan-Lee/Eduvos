package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.UserDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;

public class UserDAO {

    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long registerUser(UserDTO userDTO) throws SQLiteException {

        ContentValues values = new ContentValues();
        long result = -1;

        try {

            values.put(MyDatabaseHelper.COLUMN_EMAIL, userDTO.getEmail());
            values.put(MyDatabaseHelper.COLUMN_FIRST_NAME, userDTO.getUserFirstName());
            values.put(MyDatabaseHelper.COLUMN_LAST_NAME, userDTO.getUserLastName());
            values.put(MyDatabaseHelper.COLUMN_AGE, userDTO.getUserAge());
            values.put(MyDatabaseHelper.COLUMN_PASSWORD, userDTO.getPassword());

            result = db.insert(MyDatabaseHelper.USER_TABLE_NAME, null, values);


            if (result == -1) {
                throw new SQLiteException("User registration failed");
            }

        } catch (SQLiteException e) {

            Log.e("SQL Exception", e.getMessage());
            throw new SQLiteException("Error registering a user in CustomerDAO.", e);

        }

        return result;

    }

    public Cursor getAllUsers() {

        String query = "SELECT * FROM " + MyDatabaseHelper.USER_TABLE_NAME;
        Cursor cursor = null;

        try {

            if (db != null) {
                cursor =  db.rawQuery(query, null);
            }

        } catch (SQLiteException e) {

            Log.e("SQL Exception", e.getMessage());
            throw new SQLiteException("Error getting all users in CustomerDAO.", e);

        }

        return cursor;

    }

    public UserDTO getUserByEmail(String email) {

        String query = "SELECT * FROM " + MyDatabaseHelper.USER_TABLE_NAME + " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + " = ?";

        String[] args = {email};

        Cursor cursor = db.rawQuery(query, args);

        try {

            if (cursor.moveToFirst()) {

                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_USER_ID));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_EMAIL));
                String userFirstName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_FIRST_NAME));
                String userLastName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_LAST_NAME));
                int userAge = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_AGE));
                String userPassword = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_PASSWORD));

                return new UserDTO(userId,
                        userEmail,
                        userFirstName,
                        userLastName,
                        userAge,
                        userPassword);

            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }
}
