package com.example.tripbuddy.ui.plan_trip;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripbuddy.R;


public class SelectedItemViewHolder extends RecyclerView.ViewHolder {

    public TextView selectedItemTv;
    ImageButton deleteBtn;

    public SelectedItemViewHolder(@NonNull View itemView, SelectedItemAdapter.OnItemClickListener deleteListener) {
        super(itemView);

        findViews();

        deleteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (deleteListener != null) {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        deleteListener.onDeleteClick(position);
                    }

                }

            }

        });

    }

    private void findViews() {

        selectedItemTv = itemView.findViewById(R.id.selected_item_rv_tv);
        deleteBtn = itemView.findViewById(R.id.delete_btn_rv);

    }

}
