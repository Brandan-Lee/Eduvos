package com.example.tripbuddy.ui.view_gallery;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;

public class ViewGalleryItemViewHolder extends RecyclerView.ViewHolder {

    ImageView galleryMediaIv;

    public ViewGalleryItemViewHolder(@NonNull View itemView, ViewGalleryListAdapter.onGalleryImageClickListener galleryImageListener) {
        super(itemView);
        findViews();

        galleryMediaIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();

                if (galleryImageListener != null && position != RecyclerView.NO_POSITION) {
                    galleryImageListener.galleryImageClick(position);
                }

            }
        });

    }

    private void findViews() {

        galleryMediaIv = itemView.findViewById(R.id.gallery_media_iv);

    }

}
