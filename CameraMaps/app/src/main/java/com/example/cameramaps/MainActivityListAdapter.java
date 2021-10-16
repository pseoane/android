package com.example.cameramaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameramaps.client.model.Garden;

import java.util.List;

public class MainActivityListAdapter extends RecyclerView.Adapter<MainActivityListViewHolder> {
    public interface OnClickListener {
        void onItemClick(int position);
    }

    private List<Garden> gardens;
    private static OnClickListener clickListener;

    public MainActivityListAdapter(List<Garden> gardens, OnClickListener clickListener) {
        this.gardens = gardens;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MainActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MainActivityListViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityListViewHolder holder, int position) {
        final Garden garden = gardens.get(position);
        holder.bindValues(garden);
    }

    @Override
    public int getItemCount() {
        return gardens.size();
    }
}
