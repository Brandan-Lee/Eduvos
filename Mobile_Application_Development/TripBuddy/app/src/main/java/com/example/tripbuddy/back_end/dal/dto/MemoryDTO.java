package com.example.tripbuddy.back_end.dal.dto;

public class MemoryDTO {

    private int memoryId, tripId;
    private String text, song;
    private String imageUri;

    public MemoryDTO(int memoryId, int tripId, String text, String song, String imageUri) {

        this.memoryId = memoryId;
        this.tripId = tripId;
        this.text = text;
        this.song = song;
        this.imageUri = imageUri;

    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}
