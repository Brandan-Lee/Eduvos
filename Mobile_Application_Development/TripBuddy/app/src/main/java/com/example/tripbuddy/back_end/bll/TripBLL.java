package com.example.tripbuddy.back_end.bll;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.tripbuddy.back_end.dal.dao.BudgetDAO;
import com.example.tripbuddy.back_end.dal.dao.TripActivityDAO;
import com.example.tripbuddy.back_end.dal.dao.TripDAO;
import com.example.tripbuddy.back_end.dal.dto.BudgetDTO;
import com.example.tripbuddy.back_end.dal.dto.TripActivityDTO;
import com.example.tripbuddy.back_end.dal.dto.TripDTO;
import com.example.tripbuddy.back_end.database.MyDatabaseHelper;
import com.example.tripbuddy.ui.Models.BaseGalleryItem;
import com.example.tripbuddy.ui.Models.GalleryHeaderItem;
import com.example.tripbuddy.ui.Models.SelectedItem;
import com.example.tripbuddy.ui.Models.TripBudget;
import com.example.tripbuddy.ui.Models.ViewGalleryItem;
import com.example.tripbuddy.utils.ValidationUtils;

import java.util.ArrayList;

public class TripBLL {

    private TripDAO tripDAO;
    private BudgetDAO budgetDAO;
    private TripActivityDAO tripActivityDAO;
    private MyDatabaseHelper databaseHelper;
    private ValidationUtils validation;

    public TripBLL(MyDatabaseHelper databaseHelper, TripDAO tripDAO, BudgetDAO budgetDAO, TripActivityDAO tripActivityDAO) {

        this.databaseHelper = databaseHelper;
        this.tripDAO = tripDAO;
        this.budgetDAO = budgetDAO;
        this.tripActivityDAO = tripActivityDAO;

    }

    final double sightSeeingCost = 200.00;
    final double hikingCost = 55.00;
    final double diningCost = 500.00;
    final double museumToursCost = 300.00;
    final double beachDayCost = 100.00;

    public TripBudget calculateBudget(ArrayList<Integer> costsList, String mealsFees, String customExpenses, int userId) {

        double subTotal = 0.00;
        double discountAmount = 0.00;
        double finalBudget = 0.00;

        for (int position : costsList) {

            switch (position) {
                case 1:
                    subTotal += sightSeeingCost;
                    break;
                case 2:
                    subTotal += hikingCost;
                    break;
                case 3:
                    subTotal += diningCost;
                    break;
                case 4:
                    subTotal += museumToursCost;
                    break;
                case 5:
                    subTotal += beachDayCost;
                    break;
            }
        }

        try {

            if (mealsFees != null && !mealsFees.isEmpty()) {
                subTotal += Double.parseDouble(mealsFees);
            }

            if (customExpenses != null && !customExpenses.isEmpty()) {
                subTotal += Double.parseDouble(customExpenses);
            }

        } catch (NumberFormatException e) {

            e.printStackTrace();

        }

        int tripCount = databaseHelper.getTripCountForUser(userId);

        if (tripCount >= 3) {

            double discountPercentage = 0.10;
            discountAmount = subTotal * discountPercentage;
            finalBudget = subTotal - (subTotal * discountPercentage);

        } else {

            discountAmount = 0.00;
            finalBudget = subTotal;

        }

        return new TripBudget(subTotal, discountAmount, finalBudget);

    }

    public ArrayList<TripDTO> getTripsForUser(int userId) {

        return tripDAO.getTripsForUser(userId);

    }

    public boolean saveTrip(int userId, String destination, String startDate, String endDate, String notes, String customExpenses, String mealsFees, ArrayList<SelectedItem> selectedItems, ArrayList<Integer> costsList) {

        if (destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            return false;
        }

        if (selectedItems == null || selectedItems.isEmpty()) {
            return false;
        }

        TripBudget tripBudget = calculateBudget(costsList, mealsFees, customExpenses, userId);

        TripDTO tripDTO = new TripDTO(
                0,
                userId,
                destination,
                startDate,
                endDate,
                notes,
                null
        );

        long tripId = tripDAO.saveTrip(tripDTO);

        if (tripId == -1) {
            return false;
        }

        saveBudgetAndActivities(tripId, tripBudget.getFinalBudget(), selectedItems);

        return true;

    }

    private void saveBudgetAndActivities(long tripId, double totalTripCost, ArrayList<SelectedItem> selectedItems) {

        BudgetDTO budgetDTO = new BudgetDTO(
                0,
                (int) tripId,
                "Total Trip Cost",
                totalTripCost
        );

        long budgetResult = budgetDAO.saveBudget(budgetDTO);

        if (budgetResult == -1) {
            Log.d("SQL Exception", "Error Saving budget");
        }

        for (int i = 0; i < selectedItems.size(); i++) {

            TripActivityDTO tripActivityDTO = new TripActivityDTO(
                    0,
                    (int) tripId,
                    selectedItems.get(i).getName()
            );

            long activityResult = tripActivityDAO.saveTripActivity(tripActivityDTO);

            if (activityResult == -1) {
                Log.d("SQL Exception", "Error Saving trip activity");
            }

        }

    }

    private double getCostFromPosition(int position) {

        switch (position) {
            case 1:
                return sightSeeingCost;
            case 2:
                return hikingCost;
            case 3:
                return diningCost;
            case 4:
                return museumToursCost;
            case 5:
                return beachDayCost;
            default:
                return 0.00;
        }

    }



    public double getBudgetForTrip(int tripId) {

        BudgetDTO budget = budgetDAO.getBudgetByTripId(tripId);

        if (budget != null) {

            return budget.getAmount();

        } else {
            return 0.0;
        }

    }

    public ArrayList<BaseGalleryItem> getAllGalleryItemsForUser(int userId) {

        return tripDAO.getGalleryItemsForUser(userId);

    }

}
