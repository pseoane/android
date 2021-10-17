package com.example.sevensegmentlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView minValue;
    TextView maxValue;
    TextView avgValue;
    TextView currentValue;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        minValue = findViewById(R.id.minValue);
        maxValue = findViewById(R.id.maxValue);
        currentValue = findViewById(R.id.currentValue);
        avgValue = findViewById(R.id.avgValue);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setupObservers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.beginReadingLight();
    }

    private void setupObservers() {
        if (!viewModel.getMinLightValue().hasObservers()) {
            viewModel.getMinLightValue().observe(this, value -> {
                minValue.setText(String.valueOf(value));
            });
        }
        if (!viewModel.getMaxLightValue().hasObservers()) {
            viewModel.getMaxLightValue().observe(this, value -> {
                maxValue.setText(String.valueOf(value));
            });
        }
        if (!viewModel.getAvgLightValue().hasObservers()) {
            viewModel.getAvgLightValue().observe(this, value -> {
                avgValue.setText(String.valueOf(value));
            });
        }
        if (!viewModel.getCurrentLightValue().hasObservers()) {
            viewModel.getCurrentLightValue().observe(this, value -> {
                currentValue.setText(String.valueOf(value));
            });
        }
    }
}