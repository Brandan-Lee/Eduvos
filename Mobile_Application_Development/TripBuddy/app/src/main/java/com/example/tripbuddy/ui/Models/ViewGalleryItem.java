package com.example.tripbuddy.ui.Models;

public class ViewGalleryItem extends BaseGalleryItem {

    private String mediaPath;
    private String songPath;

    public ViewGalleryItem(String mediaPath, String songPath) {

        this.mediaPath = mediaPath;
        this.songPath = songPath;

    }

    public String getSongPath() {
        return songPath;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    @Override
    public int getType() {
        return TYPE_PHOTO;
    }

}
