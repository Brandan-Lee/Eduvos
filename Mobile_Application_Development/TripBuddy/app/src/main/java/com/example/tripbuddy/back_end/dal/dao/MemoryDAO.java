package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.MemoryDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;

public class MemoryDAO {

    private SQLiteDatabase db;

    public MemoryDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long saveMemory(MemoryDTO memoryDTO) throws SQLiteException {

        ContentValues values = new ContentValues();
        long result = -1;

        try {

            values.put(MyDatabaseHelper.COLUMN_MEMORY_TRIP_ID, memoryDTO.getTripId());
            values.put(MyDatabaseHelper.COLUMN_MEMORY_TEXT, memoryDTO.getText());
            values.put(MyDatabaseHelper.COLUMN_MEMORY_SONG, memoryDTO.getSong());

            result = db.insert(MyDatabaseHelper.MEMORY_TABLE_NAME, null, values);

            if (result == -1) {
                throw new SQLiteException("Memory save failed");
            }

        } catch (SQLiteException e) {

            Log.e("SQLExcception: ", e.getMessage());
            throw new SQLiteException("Error saving a memory in MemoryDAO.", e);

        }

        return result;

    }

    public MemoryDTO getMemoryById(int memoryId) {

        String query =
                "SELECT * FROM " + MyDatabaseHelper.MEMORY_TABLE_NAME +
                " WHERE " + MyDatabaseHelper.COLUMN_MEMORY_ID + " = ?";

        String[] selectionArgs = {String.valueOf(memoryId)};
        MemoryDTO memoryDTO = null;

        Cursor cursor = db.rawQuery(query, selectionArgs);

        try {

            if (cursor.moveToNext()) {

                int tripId = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEMORY_TRIP_ID));
                String text = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEMORY_TEXT));
                String song = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEMORY_SONG));
                memoryDTO = new MemoryDTO(memoryId, tripId, text, song, null);

            }


        } catch (SQLiteException e) {
            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error getting a memory in MemoryDAO.", e);
        } finally {

            if (cursor != null) {
                cursor.close();
            }

        }

        return memoryDTO;

    }

    public String getPhotoUri(int memoryId) {

        String photoUri = null;
        Cursor cursor = null;

        String query = "SELECT " + MyDatabaseHelper.COLUMN_MEDIA_URI +
                " FROM " + MyDatabaseHelper.MEDIA_TABLE_NAME +
                " WHERE " + MyDatabaseHelper.COLUMN_MEMORY_MEDIA_ID +  " = ? AND " +
                MyDatabaseHelper.COLUMN_MEDIA_TYPE+ " = ?";

        String selectionArgs[] = {String.valueOf(memoryId), "image"};

        try {

            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToNext()) {
                photoUri = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEDIA_URI));
            }

        } catch (SQLiteException e) {
            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error getting a memory in MemoryDAO.", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return photoUri;

    }
}
