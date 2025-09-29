package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.TripDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.ui.Models.BaseGalleryItem;
import com.example.tripbuddy.ui.Models.GalleryHeaderItem;
import com.example.tripbuddy.ui.Models.ViewGalleryItem;

import java.util.ArrayList;

public class TripDAO {

    private MyDatabaseHelper databaseHelper;

    public TripDAO(MyDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long saveTrip(TripDTO tripDTO) throws SQLiteException {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        long result = -1;

        try {

            values.put(MyDatabaseHelper.COLUMN_TRIP_USER_ID, tripDTO.getUserId());
            values.put(MyDatabaseHelper.COLUMN_TRIP_DESTINATION, tripDTO.getDestination());
            values.put(MyDatabaseHelper.COLUMN_TRIP_START_DATE, tripDTO.getStartDate());
            values.put(MyDatabaseHelper.COLUMN_TRIP_END_DATE, tripDTO.getEndDate());
            values.put(MyDatabaseHelper.COLUMN_TRIP_NOTES, tripDTO.getNotes());
            values.put(MyDatabaseHelper.COLUMN_TRIP_IMAGE, tripDTO.getImageUri());

            result = db.insert(MyDatabaseHelper.TRIP_TABLE_NAME, null, values);;

            if (result == -1) {
                throw new SQLiteException("Trip registration failed");
            }

        } catch (SQLiteException e) {

            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error registering a trip in TripDAO.", e);

        } finally {
            if (db != null) {
                db.close();
            }
        }

        return result;

    }

    public ArrayList<TripDTO> getTripsForUser(int userId) {

        ArrayList<TripDTO> trips = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        String query =
                "SELECT * FROM " + MyDatabaseHelper.TRIP_TABLE_NAME +
                        " WHERE " + MyDatabaseHelper.COLUMN_TRIP_USER_ID + " = ?";

        String[] args = {String.valueOf(userId)};

        try {

            cursor = db.rawQuery(query, args);

            if (cursor.moveToFirst()) {

                do {
                    trips.add(new TripDTO(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6)
                    ));

                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {

            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error getting trips for user in TripDAO.", e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return trips;

    }

    public ArrayList<BaseGalleryItem> getGalleryItemsForUser(int userId) {
        return databaseHelper.getAllGalleryItemsForUser(userId);
    }

}
