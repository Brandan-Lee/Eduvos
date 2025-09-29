package com.example.tripbuddy.ui.Models;

public class TripBudget {

    private double subTotal, discountAmount, finalBudget;

    public TripBudget(double subTotal, double discountAmount, double finalBudget) {

        this.subTotal = subTotal;
        this.discountAmount = discountAmount;
        this.finalBudget = finalBudget;

    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalBudget() {
        return finalBudget;
    }



}
