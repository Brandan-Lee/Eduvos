package com.example.tripbuddy.back_end.dal.dto;

public class BudgetDTO {

    private int budgetId, tripId;
    private String expenseType;
    private double amount;

    public BudgetDTO(int budgetId, int tripId, String expenseType, double amount) {

        this.budgetId = budgetId;
        this.tripId = tripId;
        this.expenseType = expenseType;
        this.amount = amount;

    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
