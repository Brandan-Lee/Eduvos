package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.TripActivityDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;

public class TripActivityDAO {

    private MyDatabaseHelper databaseHelper;

    public TripActivityDAO(MyDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long saveTripActivity(TripActivityDTO tripActivityDTO) throws SQLiteException {

        SQLiteDatabase db = null;
        ContentValues values = new ContentValues();
        long result = -1;

        try {

            db = databaseHelper.getWritableDatabase();
            values.put(MyDatabaseHelper.COLUMN_TRIP_ACTIVITY_TRIP_ID, tripActivityDTO.getTripId());
            values.put(MyDatabaseHelper.COLUMN_TRIP_ACTIVITY_NAME, tripActivityDTO.getName());

            result = db.insert(MyDatabaseHelper.TRIP_ACTIVITY_TABLE_NAME, null, values);

            if (result == -1) {
                throw new SQLiteException("Failed to save trip activity");
            }

        } catch (SQLiteException e) {

            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error saving trip activity in TripActivityDAO.", e);

        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;

    }


}
