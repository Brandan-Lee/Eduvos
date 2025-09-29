package com.example.tripbuddy.back_end.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tripbuddy.ui.Models.BaseGalleryItem;
import com.example.tripbuddy.ui.Models.GalleryHeaderItem;
import com.example.tripbuddy.ui.Models.ViewGalleryItem;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "tripbuddy.db";
    private static final int DATABASE_VERSION = 1;

    //User Table
    public static final String USER_TABLE_NAME = "user";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_PASSWORD = "password";

    //Trip Table
    public static final String TRIP_TABLE_NAME = "trip";
    public static final String COLUMN_TRIP_ID = "trip_id";
    public static final String COLUMN_TRIP_USER_ID = "user_id";
    public static final String COLUMN_TRIP_DESTINATION = "destination";
    public static final String COLUMN_TRIP_START_DATE = "start_date";
    public static final String COLUMN_TRIP_END_DATE = "end_date";
    public static final String COLUMN_TRIP_NOTES = "notes";
    public static final String COLUMN_TRIP_IMAGE = "image";

    //Budget Table
    public static final String BUDGET_TABLE_NAME = "budget";
    public static final String COLUMN_BUDGET_ID = "budget_id";
    public static final String COLUMN_BUDGET_TRIP_ID = "trip_id";
    public static final String COLUMN_EXPENCE_TYPE = "expence_type";
    public static final String COLUMN_AMOUNT = "amount";

    //Trip Activity Table
    public static final String TRIP_ACTIVITY_TABLE_NAME = "trip_activity";
    public static final String COLUMN_TRIP_ACTIVITY_ID = "trip_activity_id";
    public static final String COLUMN_TRIP_ACTIVITY_TRIP_ID = "trip_id";
    public static final String COLUMN_TRIP_ACTIVITY_NAME = "name";

    //Memory Table
    public static final String MEMORY_TABLE_NAME = "memory";
    public static final String COLUMN_MEMORY_ID = "memory_id";
    public static final String COLUMN_MEMORY_TRIP_ID = "trip_id";
    public static final String COLUMN_MEMORY_TEXT = "text";
    public static final String COLUMN_MEMORY_SONG = "song";

    //media Table
    public static final String MEDIA_TABLE_NAME = "media";
    public static final String COLUMN_MEDIA_ID = "media_id";
    public static final String COLUMN_MEMORY_MEDIA_ID = "memory_id";
    public static final String COLUMN_MEDIA_TRIP_ID = "trip_id";
    public static final String COLUMN_MEDIA_URI = "uri";
    public static final String COLUMN_MEDIA_TYPE = "type";

    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createUserTable(db);
        createTripTable(db);
        createBudgetTable(db);
        createTripActivityTable(db);
        createMemoryTable(db);
        createMediaTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        dropUserTableIfExists(db);
        dropTripTableIfExists(db);
        dropBudgetTableIfExists(db);
        dropTripActivityTableIfExists(db);
        dropMemoryTableIfExists(db);
        dropMediaTableIfExists(db);

        onCreate(db);
    }

    private void createUserTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + USER_TABLE_NAME +
                        " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                        COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                        COLUMN_AGE + " INTEGER NOT NULL, " +
                        COLUMN_PASSWORD + " TEXT NOT NULL);";

        db.execSQL(query);

    }

    private void dropUserTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
    }

    private void createTripTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TRIP_TABLE_NAME +
                        " (" + COLUMN_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TRIP_USER_ID + " INTEGER NOT NULL, " +
                        COLUMN_TRIP_DESTINATION + " TEXT NOT NULL, " +
                        COLUMN_TRIP_START_DATE + " TEXT NOT NULL, " +
                        COLUMN_TRIP_END_DATE + " TEXT NOT NULL, " +
                        COLUMN_TRIP_NOTES + " TEXT, " +
                        COLUMN_TRIP_IMAGE + " TEXT, " +
                        "FOREIGN KEY (" + COLUMN_TRIP_USER_ID + ") REFERENCES " + USER_TABLE_NAME + "(" + COLUMN_USER_ID + "));";

        db.execSQL(query);

    }

    private void dropTripTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
    }

    private void createBudgetTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + BUDGET_TABLE_NAME +
                        " (" + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_BUDGET_TRIP_ID + " INTEGER NOT NULL, " +
                        COLUMN_EXPENCE_TYPE + " TEXT NOT NULL, " +
                        COLUMN_AMOUNT + " REAL NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_BUDGET_TRIP_ID + ") REFERENCES " + TRIP_TABLE_NAME + "(" + COLUMN_TRIP_ID + "));";

        db.execSQL(query);

    }

    private void dropBudgetTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + BUDGET_TABLE_NAME);
    }

    private void createTripActivityTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TRIP_ACTIVITY_TABLE_NAME +
                        " (" + COLUMN_TRIP_ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TRIP_ACTIVITY_TRIP_ID + " INTEGER NOT NULL, " +
                        COLUMN_TRIP_ACTIVITY_NAME + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_TRIP_ACTIVITY_TRIP_ID + ") REFERENCES " + TRIP_TABLE_NAME + "(" + COLUMN_TRIP_ID + "));";

        db.execSQL(query);

    }

    private void dropTripActivityTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_ACTIVITY_TABLE_NAME);
    }

    private void createMemoryTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + MEMORY_TABLE_NAME +
                        " (" + COLUMN_MEMORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MEMORY_TRIP_ID + " INTEGER NOT NULL, " +
                        COLUMN_MEMORY_TEXT + " TEXT NOT NULL, " +
                        COLUMN_MEMORY_SONG + " TEXT, " +
                        "FOREIGN KEY (" + COLUMN_MEMORY_TRIP_ID + ") REFERENCES " + TRIP_TABLE_NAME + "(" + COLUMN_TRIP_ID + "));";

        db.execSQL(query);

    }

    private void dropMemoryTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMORY_TABLE_NAME);
    }

    private void createMediaTable(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + MEDIA_TABLE_NAME +
                        " (" + COLUMN_MEDIA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MEMORY_MEDIA_ID + " INTEGER NOT NULL, " +
                        COLUMN_MEDIA_TRIP_ID + " INTEGER NOT NULL, " +
                        COLUMN_MEDIA_URI + " TEXT NOT NULL, " +
                        COLUMN_MEDIA_TYPE + " TEXT NOT NULL, " +
                        "FOREIGN KEY (" + COLUMN_MEMORY_MEDIA_ID + ") REFERENCES " + MEMORY_TABLE_NAME + "(" + COLUMN_MEMORY_ID + ")," +
                        "FOREIGN KEY (" + COLUMN_MEDIA_TRIP_ID + ") REFERENCES " + TRIP_TABLE_NAME + "(" + COLUMN_TRIP_ID + "));";

        db.execSQL(query);

    }

    private void dropMediaTableIfExists(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + MEDIA_TABLE_NAME);
    }

    public int getTripCountForUser(int userId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {

            String query =
                    "SELECT COUNT(*) FROM " + TRIP_TABLE_NAME +
                            " WHERE " + COLUMN_TRIP_USER_ID + " = ?";

            String[] selectionArgs = { String.valueOf(userId) };

            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return count;

    }

    public ArrayList<BaseGalleryItem> getAllGalleryItemsForUser(int userId) {

        ArrayList<BaseGalleryItem> galleryItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String currentTripDestination = "";

        try {

            String query =
                    "SELECT T." + MyDatabaseHelper.COLUMN_TRIP_DESTINATION +
                            ", M." + MyDatabaseHelper.COLUMN_MEDIA_URI +
                            ", R." + MyDatabaseHelper.COLUMN_MEMORY_SONG +
                            " FROM " + MyDatabaseHelper.TRIP_TABLE_NAME + " AS T" +
                            " JOIN " + MyDatabaseHelper.MEMORY_TABLE_NAME + " AS R ON T." + MyDatabaseHelper.COLUMN_TRIP_ID + " = R." + MyDatabaseHelper.COLUMN_MEMORY_TRIP_ID +
                            " LEFT JOIN " + MyDatabaseHelper.MEDIA_TABLE_NAME + " AS M ON R." + MyDatabaseHelper.COLUMN_MEMORY_ID + " = M." + MyDatabaseHelper.COLUMN_MEMORY_MEDIA_ID +
                            " WHERE T." + MyDatabaseHelper.COLUMN_TRIP_USER_ID + " = ? " +
                            "ORDER BY T." + MyDatabaseHelper.COLUMN_TRIP_ID + " ASC";

            String[] selectionArgs = { String.valueOf(userId) };
            cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {

                do {

                    String tripDestination = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_TRIP_DESTINATION));
                    String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEDIA_URI));
                    String song = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_MEMORY_SONG));

                    if (!tripDestination.equals(currentTripDestination)) {
                        currentTripDestination = tripDestination;
                        galleryItems.add(new GalleryHeaderItem(tripDestination));
                    }

                    galleryItems.add(new ViewGalleryItem(imageUri, song));

                } while (cursor.moveToNext());

            }

        } catch (SQLiteException e) {

            Log.d("MyDatabaseHelper", "Error getting gallery items: " + e.getMessage());
            throw new SQLiteException("Error getting gallery items in MyDatabaseHelper.", e);

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return galleryItems;

    }

}

