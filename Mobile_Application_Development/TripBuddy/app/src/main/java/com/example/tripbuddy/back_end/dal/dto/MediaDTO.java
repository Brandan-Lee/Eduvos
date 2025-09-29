package com.example.tripbuddy.back_end.dal.dto;

public class MediaDTO {

    private int mediaId, memoryId, tripId;
    private String uri, type;

    public MediaDTO (int mediaId, int memoryId, int tripId, String uri, String type) {

        this.mediaId = mediaId;
        this.memoryId = memoryId;
        this.tripId = tripId;
        this.uri = uri;
        this.type = type;

    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
