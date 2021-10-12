package com.example.cameramaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.example.cameramaps.R;
import com.example.cameramaps.client.model.Garden;
import com.example.cameramaps.client.repository.GardensRepository;
import com.example.cameramaps.client.model.Gardens;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Garden> gardens = new ArrayList<>();
    private MainActivityListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MainActivityListAdapter(gardens);
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
}