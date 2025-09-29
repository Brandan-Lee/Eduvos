package com.example.tripbuddy.ui.Models;

public abstract class BaseGalleryItem {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PHOTO = 1;
    public abstract int getType();

}
