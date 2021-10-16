package com.example.cameramaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SecondActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Intent intent;
    private FragmentContainerView fragmentContainerView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.gardenName);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView2, mapFragment)
                .commit();
        intent = getIntent();
        textView.setText(intent.getStringExtra("gardenName"));
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Double lat = intent.getDoubleExtra("latitude", 0);
        Double longit = intent.getDoubleExtra("longitude", 0);
        LatLng park = new LatLng(lat, longit);
        googleMap.addMarker(new MarkerOptions()
                .position(park)
                .title("Ubicación")
        );
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(park));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14.0F));
    }
}