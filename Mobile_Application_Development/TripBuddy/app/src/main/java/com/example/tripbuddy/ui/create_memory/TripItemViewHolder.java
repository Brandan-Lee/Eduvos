package com.example.tripbuddy.ui.create_memory;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;

public class TripItemViewHolder extends RecyclerView.ViewHolder {

    TextView tripNameTv;
    private Button createMemoryBtn;

    public TripItemViewHolder(@NonNull View itemView, TripListAdapter.OnCreateMemoryClickListener createMemoryListener) {
        super(itemView);

        findViews();

        createMemoryBtn.setOnClickListener(v -> {
            int position = getAdapterPosition();
            if (createMemoryListener != null && position != RecyclerView.NO_POSITION) {
                createMemoryListener.createMemoryClick(position);
            }
        });


    }

    private void findViews() {

        tripNameTv = itemView.findViewById(R.id.trip_name_tv);
        createMemoryBtn = itemView.findViewById(R.id.create_memory_btn);

    }

}
