package com.example.cameramaps;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cameramaps.client.model.Garden;

public class MainActivityListViewHolder extends RecyclerView.ViewHolder {
    private TextView itemName;
    private MainActivityListAdapter.OnClickListener clickListener;

    public MainActivityListViewHolder(View itemView, MainActivityListAdapter.OnClickListener clickListener) {
        super(itemView);
        itemName = itemView.findViewById(R.id.mainItemName);
        this.clickListener = clickListener;
        itemView.setOnClickListener(view -> this.clickListener.onItemClick(getAdapterPosition()));
    }

    void bindValues(Garden garden) {
        itemName.setText(garden.title);
    }
}
