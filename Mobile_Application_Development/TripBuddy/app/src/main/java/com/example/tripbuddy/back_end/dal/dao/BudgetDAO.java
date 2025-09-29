package com.example.tripbuddy.back_end.dal.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dto.BudgetDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;

public class BudgetDAO {

    private MyDatabaseHelper databaseHelper;

    public BudgetDAO(MyDatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public long saveBudget(BudgetDTO budgetDTO) throws SQLiteException {

        SQLiteDatabase db = null;
        ContentValues values = new ContentValues();
        long result = -1;

        try {
            db = databaseHelper.getWritableDatabase();

            values.put(MyDatabaseHelper.COLUMN_BUDGET_TRIP_ID, budgetDTO.getTripId());
            values.put(MyDatabaseHelper.COLUMN_EXPENCE_TYPE, budgetDTO.getExpenseType());
            values.put(MyDatabaseHelper.COLUMN_AMOUNT, budgetDTO.getAmount());

            result = db.insert(MyDatabaseHelper.BUDGET_TABLE_NAME, null, values);

            if (result == -1) {
                throw new SQLiteException("Budget saving failed");
            }

        } catch (SQLiteException e) {

            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error saving a budget in BudgetDAO.", e);

        }

        return result;

    }

    public BudgetDTO getBudgetByTripId(int tripId) {

        SQLiteDatabase db = null;
        Cursor cursor = null;
        BudgetDTO budget = null;

        try {

            db = databaseHelper.getReadableDatabase();
            String query =
                    "SELECT * FROM " + MyDatabaseHelper.BUDGET_TABLE_NAME +
                            " WHERE " + MyDatabaseHelper.COLUMN_BUDGET_TRIP_ID + " = ?";
            String[] selectionArgs = {String.valueOf(tripId)};
            cursor = db.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {

                int budgetId = cursor.getInt(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_BUDGET_ID));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_AMOUNT));
                budget = new BudgetDTO(budgetId, tripId, null, amount);

            }


        } catch (SQLiteException e) {

            Log.d("SQL Exception", e.getMessage());
            throw new SQLiteException("Error getting a budget in BudgetDAO.", e);
        } finally {

            if (cursor != null) {

                cursor.close();

            }

        }

        return budget;

    }
}
