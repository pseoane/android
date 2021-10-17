package com.example.sevensegmentlight;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel implements SensorEventListener {
    private MutableLiveData<Integer> currentLightValue;
    private MutableLiveData<Integer> minLightValue;
    private MutableLiveData<Integer> maxLightValue;
    private MutableLiveData<Integer> avgLightValue;
    private SensorManager sensorManager;
    private int numberOfValuesRead = 0;
    private Executor ex;
    private Date initialDate;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
        ex = Executors.newSingleThreadExecutor();
    }

    public MutableLiveData<Integer> getCurrentLightValue() {
        if (currentLightValue == null) {
            currentLightValue = new MutableLiveData();
        }
        return currentLightValue;
    }

    public MutableLiveData<Integer> getMinLightValue() {
        if (minLightValue == null) {
            minLightValue = new MutableLiveData();
        }
        return minLightValue;
    }

    public MutableLiveData<Integer> getMaxLightValue() {
        if (maxLightValue == null) {
            maxLightValue = new MutableLiveData();
        }
        return maxLightValue;
    }

    public MutableLiveData<Integer> getAvgLightValue() {
        if (avgLightValue == null) {
            avgLightValue = new MutableLiveData();
        }
        return avgLightValue;
    }

    public void beginReadingLight() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ex.execute(new LightMetricsCalculator(sensorEvent.values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Unimplemented
    }

    private class LightMetricsCalculator implements Runnable {
        float[] values;

        LightMetricsCalculator(float[] values) {
            this.values = values;
        }

        @Override
        public void run() {
            Integer currentValue = Math.round(values[0]);
            currentLightValue.postValue(currentValue);
            if (minLightValue.getValue() == null || currentValue < minLightValue.getValue()) {
                minLightValue.postValue(currentValue);
            }
            if (maxLightValue.getValue() == null || currentValue > maxLightValue.getValue()) {
                maxLightValue.postValue(currentValue);
            }
            if (avgLightValue.getValue() == null) {
                avgLightValue.postValue(currentValue);
                numberOfValuesRead = 1;
            } else {
                float avg = (numberOfValuesRead * avgLightValue.getValue() + currentValue) / (numberOfValuesRead + 1);
                avgLightValue.postValue(Math.round(avg));
                numberOfValuesRead += 1;
            }
        }
    }
}
