package com.example.cameramaps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.example.cameramaps.adapters.MainActivityListAdapter;
import com.example.cameramaps.R;
import com.example.cameramaps.client.model.Garden;
import com.example.cameramaps.client.repository.GardensRepository;
import com.example.cameramaps.client.model.Gardens;
import com.google.android.gms.maps.model.LatLng;

import android.os.Handler;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements
        MainActivityListAdapter.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

    private RecyclerView recyclerView;
    private List<Garden> gardens = new ArrayList<>();
    private MainActivityListAdapter adapter;
    private LocationManager locationManager;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MainActivityListAdapter(gardens, this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        Garden garden = gardens.get(position);
        Intent secondIntent = new Intent(this, SecondActivity.class);
        secondIntent.putExtra("gardenName", garden.title);
        secondIntent.putExtra("latitude", garden.location.latitude);
        secondIntent.putExtra("longitude", garden.location.longitude);
        startActivity(secondIntent);
    }

    private void getLocationOrRequestPermission() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates()
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, this);
        }
    }

    private void addLocationToTable() {
        if (currentLocation != null) {

            for (Garden garden: gardens) {
                float[] results = new float[1];
                Double distance = Location.distanceBetween(currentLocation.latitude, currentLocation.longitude, garden.location.latitude, garden.location.longitude, results);
            }
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}