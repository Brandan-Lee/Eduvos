package com.example.tripbuddy.ui.view_gallery;

import com.example.tripbuddy.ui.Models.BaseGalleryItem;

public class ViewGalleryItemFull extends BaseGalleryItem {

    private String filePath;

    public ViewGalleryItemFull(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public int getType() {
        return ViewGalleryListAdapter.TYPE_PHOTO;
    }

}
