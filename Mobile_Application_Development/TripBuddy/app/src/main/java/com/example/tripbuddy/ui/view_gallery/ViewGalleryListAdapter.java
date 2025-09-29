package com.example.tripbuddy.ui.view_gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tripbuddy.R;
import com.example.tripbuddy.ui.Models.BaseGalleryItem;
import com.example.tripbuddy.ui.Models.GalleryHeaderItem;
import com.example.tripbuddy.ui.Models.ViewGalleryItem;

import java.util.ArrayList;

public class ViewGalleryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PHOTO = 1;

    private ArrayList<BaseGalleryItem> galleryItems;
    private onGalleryImageClickListener galleryImageListener;

    public ViewGalleryListAdapter(ArrayList<BaseGalleryItem> galleryItems, onGalleryImageClickListener galleryImageListener) {

        this.galleryItems = galleryItems;
        this.galleryImageListener = galleryImageListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == BaseGalleryItem.TYPE_HEADER) {

            View view = inflater.inflate(R.layout.gallery_header_item, parent, false);
            return new HeaderViewHolder(view);

        } else {
            View view = inflater.inflate(R.layout.gallery_item, parent, false);
            return new ViewGalleryItemViewHolder(view, galleryImageListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BaseGalleryItem item = galleryItems.get(position);

        if (holder.getItemViewType() == BaseGalleryItem.TYPE_HEADER) {

            HeaderViewHolder header = (HeaderViewHolder) holder;
            GalleryHeaderItem headerItem = (GalleryHeaderItem) item;
            header.tripNameHeaderTv.setText(headerItem.getTripName());

        } else if (holder.getItemViewType() == BaseGalleryItem.TYPE_PHOTO) {
            ViewGalleryItemViewHolder viewGalleryItemViewHolder = (ViewGalleryItemViewHolder) holder;
            ViewGalleryItem viewGalleryItem = (ViewGalleryItem) item;

            String path = viewGalleryItem.getMediaPath();

            Glide.with(viewGalleryItemViewHolder.itemView.getContext())
                    .load(viewGalleryItem.getMediaPath())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(viewGalleryItemViewHolder.galleryMediaIv);

            viewGalleryItemViewHolder.galleryMediaIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (galleryImageListener != null) {
                        galleryImageListener.galleryImageClick(position);
                    }
                }

            });

        }

    }

    @Override
    public int getItemViewType(int position) {
        return galleryItems.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    public interface onGalleryImageClickListener {
        void galleryImageClick(int position);
    }


}
