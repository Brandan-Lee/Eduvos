package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.MediaDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;

public class MediaDAO {

    private SQLiteDatabase db;

    public MediaDAO(SQLiteDatabase db) {
        this.db = db;
    }


    public long saveMedia(long memoryId, int tripId, String uri, String type) throws SQLiteException {

        ContentValues values = new ContentValues();
        long result = -1;

        try {

            values.put(MyDatabaseHelper.COLUMN_MEMORY_MEDIA_ID, memoryId);
            values.put(MyDatabaseHelper.COLUMN_MEDIA_TRIP_ID, tripId);
            values.put(MyDatabaseHelper.COLUMN_MEDIA_URI, uri);
            values.put(MyDatabaseHelper.COLUMN_MEDIA_TYPE, type);

            result = db.insert(MyDatabaseHelper.MEDIA_TABLE_NAME, null, values);

            Log.d("MediaDAO", "Saving media: memoryId=" + memoryId + ", tripId=" + tripId + ", uri=" + uri + ", type=" + type);

            if (result == -1) {
                throw new SQLiteException("Media save failed");
            }

        } catch (SQLiteException e) {

            Log.e("SQLExcception: ", e.getMessage());
            throw new SQLiteException("Error saving a media in MediaDAO.", e);

        }

        return result;

    }

}
