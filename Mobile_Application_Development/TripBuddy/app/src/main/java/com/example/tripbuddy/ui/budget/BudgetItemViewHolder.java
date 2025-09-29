package com.example.tripbuddy.ui.budget;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;

public class BudgetItemViewHolder extends RecyclerView.ViewHolder {

    TextView tripNameTv, tripFinalBudgetTv;

    public BudgetItemViewHolder (View itemView, BudgetListAdapter adapter) {

        super(itemView);
        findViews();

    }

    private void findViews() {

        tripNameTv = itemView.findViewById(R.id.trip_destination_tv);
        tripFinalBudgetTv = itemView.findViewById(R.id.trip_final_budget_tv_item);

    }

}
