package com.example.cameramaps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.example.cameramaps.adapters.MainActivityListAdapter;
import com.example.cameramaps.R;
import com.example.cameramaps.client.model.Garden;
import com.example.cameramaps.client.repository.GardensRepository;
import com.example.cameramaps.client.model.Gardens;

import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityListAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private List<Garden> gardens = new ArrayList<>();
    private MainActivityListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MainActivityListAdapter(gardens, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getGardens();
    }


    private void getGardens() {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int statusCode = msg.getData().getInt("statusCode");
                Gardens g = (Gardens) msg.obj;
                for (Garden garden : g.gardens) {
                    gardens.add(garden);
                }
                adapter.notifyDataSetChanged();
                return;
            }
        };
        new GardensRepository().executeRequest(handler);
    }

    @Override
    public void onItemClick(int position) {
        Log.d("CLICK", "clickcked positin" + position);
        Garden garden = gardens.get(position);
        Intent secondIntent = new Intent(this, SecondActivity.class);
        secondIntent.putExtra("gardenName", garden.title);
        secondIntent.putExtra("latitude", garden.location.latitude);
        secondIntent.putExtra("longitude", garden.location.longitude);
        startActivity(secondIntent);
    }
}