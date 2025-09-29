package com.example.tripbuddy.ui.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;
import com.example.tripbuddy.ui.Models.TripBudgetItem;

import java.util.ArrayList;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetItemViewHolder> {

    private Context context;
    private ArrayList<TripBudgetItem> tripBudgetItems;

    public BudgetListAdapter(Context context, ArrayList<TripBudgetItem> tripBudgetItems) {
        this.context = context;
        this.tripBudgetItems = tripBudgetItems;
    }


    @NonNull
    @Override
    public BudgetItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_budget_item, parent, false);
        return new BudgetItemViewHolder(view, this);

    }

    @Override
    public void onBindViewHolder(@NonNull BudgetItemViewHolder holder, int position) {

        holder.tripNameTv.setText(tripBudgetItems.get(position).getDestination());
        holder.tripFinalBudgetTv.setText("Final Budget: R" + tripBudgetItems.get(position).getFinalBudget());

    }

    @Override
    public int getItemCount() {
        return tripBudgetItems.size();
    }
}
