package com.example.cameramaps;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cameramaps.client.model.Garden;

public class MainActivityListViewHolder extends RecyclerView.ViewHolder {
    TextView itemName;

    public MainActivityListViewHolder(View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.mainItemName);
    }

    void bindValues(Garden garden) {
        itemName.setText(garden.title);
    }
}
