package com.example.tripbuddy.ui.Models;

public class TripBudgetItem {

    String destination;
    double finalBudget;

    public TripBudgetItem(String destination, double finalBudget) {
        this.destination = destination;
        this.finalBudget = finalBudget;
    }


    public String getDestination() {
        return destination;
    }

    public double getFinalBudget() {
        return finalBudget;
    }

}
