package com.example.tripbuddy.back_end.dal.dto;

public class TripActivityDTO {

    public int tripActivityId, tripId;
    public String name;

    public TripActivityDTO(int tripActivityId, int tripId, String name) {

        this.tripActivityId = tripActivityId;
        this.tripId = tripId;
        this.name = name;


    }

    public int getTripActivityId() {
        return tripActivityId;
    }

    public void setTripActivityId(int tripActivityId) {
        this.tripActivityId = tripActivityId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
