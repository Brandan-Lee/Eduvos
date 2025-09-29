package com.example.tripbuddy.ui.plan_trip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tripbuddy.R;
import com.example.tripbuddy.ui.Models.SelectedItem;

import java.util.ArrayList;

public class SelectedItemAdapter extends RecyclerView.Adapter<SelectedItemViewHolder> {

    private Context context;
    private ArrayList<SelectedItem> selectedItems;
    private OnItemClickListener deleteListener;

    public SelectedItemAdapter(Context context, ArrayList<SelectedItem> selectedItems, OnItemClickListener deleteListener) {

        this.context = context;
        this.selectedItems = selectedItems;
        this.deleteListener = deleteListener;

    }

    @NonNull
    @Override
    public SelectedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.selected_activities_list, parent, false);
        return new SelectedItemViewHolder(view, deleteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemViewHolder holder, int position) {
        holder.selectedItemTv.setText(selectedItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return selectedItems.size();
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

}
