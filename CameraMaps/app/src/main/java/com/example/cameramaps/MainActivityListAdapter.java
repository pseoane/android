package com.example.cameramaps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cameramaps.client.model.Garden;

import java.util.List;

public class MainActivityListAdapter extends RecyclerView.Adapter<MainActivityListViewHolder> {
    private List<Garden> gardens;

    public MainActivityListAdapter(List<Garden> gardens) {
        this.gardens = gardens;
    }

    @NonNull
    @Override
    public MainActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MainActivityListViewHolder(v);
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
