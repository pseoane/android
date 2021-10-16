package com.example.cameramaps.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cameramaps.fragments.MapsFragment;
import com.example.cameramaps.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class SecondActivity extends AppCompatActivity {
    private Intent intent;
    private MapsFragment mapFragment;
    private TextView textView;
    private RadioGroup radioGroup;
    private SharedPreferences preferences;
    private int mapType;
    private String preferencesFile = "preferencesFile";
    private final String mapTypeKey = "mapType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        intent = getIntent();

        preferences = getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        mapType = preferences.getInt(mapTypeKey, GoogleMap.MAP_TYPE_NORMAL);

        setupTextView();
        setupRadioGroup();
        setupMapFragment();
    }

    @Override
    protected void onStop() {
        super.onStop();
        preferences.edit().putInt(mapTypeKey, mapType).apply();
    }

    private void setupMapFragment() {
        mapFragment = new MapsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainerView2, mapFragment)
                .commit();
        Double lat = intent.getDoubleExtra("latitude", 0);
        Double longit = intent.getDoubleExtra("longitude", 0);
        mapFragment.setMarker(new LatLng(lat, longit));
        mapFragment.setType(mapType);
    }

    private void setupTextView() {
        textView = findViewById(R.id.gardenName);
        textView.setText(intent.getStringExtra("gardenName"));
    }

    private void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.terrain:
                if (checked)
                    mapType = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            case R.id.normal:
                if (checked)
                    mapType = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case R.id.hybrid:
                if (checked)
                    mapType = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case R.id.satellite:
                if (checked)
                    mapType = GoogleMap.MAP_TYPE_SATELLITE;
                break;
        }
        mapFragment.setType(mapType);
    }

    private void setupRadioGroup() {
        radioGroup = findViewById(R.id.radioGroup);
        switch (mapType) {
            case GoogleMap.MAP_TYPE_NORMAL:
                radioGroup.check(R.id.normal);
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                radioGroup.check(R.id.hybrid);
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                radioGroup.check(R.id.terrain);
                break;
            case GoogleMap.MAP_TYPE_SATELLITE:
                radioGroup.check(R.id.satellite);
                break;
        }
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setOnClickListener(view -> {onRadioButtonClicked(view);});
        }
    }
}