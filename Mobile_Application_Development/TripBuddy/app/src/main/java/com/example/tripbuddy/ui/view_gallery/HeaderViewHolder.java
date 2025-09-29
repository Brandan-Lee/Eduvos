package com.example.tripbuddy.ui.view_gallery;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    TextView tripNameHeaderTv;

    public HeaderViewHolder(@NonNull View itemView) {

        super(itemView);
        tripNameHeaderTv = itemView.findViewById(R.id.trip_name_header_tv);

    }

}
