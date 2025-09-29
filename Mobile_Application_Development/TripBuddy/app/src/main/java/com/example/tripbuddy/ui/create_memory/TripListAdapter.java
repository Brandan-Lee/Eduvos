package com.example.tripbuddy.ui.create_memory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;
import com.example.tripbuddy.ui.Models.TripItem;

import java.util.ArrayList;

public class TripListAdapter extends RecyclerView.Adapter<TripItemViewHolder>{

    private Context context;
    private ArrayList<TripItem> tripItems;
    private OnCreateMemoryClickListener createMemoryListener;

    public TripListAdapter(Context context, ArrayList<TripItem> tripItems, OnCreateMemoryClickListener createMemoryListener) {

        this.context = context;
        this.tripItems = tripItems;
        this.createMemoryListener = createMemoryListener;

    }

    @NonNull
    @Override
    public TripItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_item, parent, false);
        return new TripItemViewHolder(view, createMemoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TripItemViewHolder holder, int position) {
        holder.tripNameTv.setText(tripItems.get(position).getTripName());
    }

    @Override
    public int getItemCount() {
        return tripItems.size();
    }

    public interface OnCreateMemoryClickListener {
        void createMemoryClick(int position);
    }

}
