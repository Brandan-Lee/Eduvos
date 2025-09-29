package com.example.tripbuddy.ui.Models;

public class GalleryHeaderItem extends BaseGalleryItem {

    private String tripName;

    public GalleryHeaderItem(String tripName) {
        this.tripName = tripName;
    }

    public String getTripName() {
        return tripName;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }

}
