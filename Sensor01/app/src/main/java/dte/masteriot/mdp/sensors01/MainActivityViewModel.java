package dte.masteriot.mdp.sensors01;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import dte.masteriot.mdp.sensors01.utils.SensorNotAvailableException;

public class MainActivityViewModel extends AndroidViewModel {
    // region Properties
    private MutableLiveData accelerometerValues;
    private MutableLiveData lightSensorValues;
    private SharedPreferences preferences;
    private CustomSensorListener listener;
    private Observer<SensorEvent> sensorEventsObserver;
    private String preferencesFile = "preferencesFile";
    // endregion

    // region Constructor
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        listener = new CustomSensorListener(application);
        sensorEventsObserver = sensorEvent -> { sensorChanged(sensorEvent); };
        listener.getEvents().observeForever(sensorEventsObserver);
        preferences = application.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        initWithPreferences(preferences);
    }
    // endregion

    // region Public methods
    public boolean isSensorOn(int sensorType) {
        return preferences.getBoolean(preferencesFileName(sensorType), false);
    }

    public MutableLiveData<float[]> getAccelerometerValues() {
        if (accelerometerValues == null) {
            accelerometerValues = new MutableLiveData<float[]>();
        }
        return accelerometerValues;
    }

    public MutableLiveData<float[]> getLightValues() {
        if (lightSensorValues == null) {
            lightSensorValues = new MutableLiveData<float[]>();
        }
        return lightSensorValues;
    }

    public void turnSensorOn(int sensorType) {
        try {
            listener.registerSensor(sensorType);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(preferencesFileName(sensorType), true);
            editor.apply();
        } catch (SensorNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void turnSensorOff(int sensorType) {
        try {
            listener.unregisterSensor(sensorType);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(preferencesFileName(sensorType), false);
            editor.apply();
        } catch (SensorNotAvailableException e) {
            e.printStackTrace();
        }
    }
    //endregion

    // region Override methods
    @Override
    protected void onCleared() {
        listener.getEvents().removeObserver(sensorEventsObserver);
        super.onCleared();
    }
    // endregion

    // region PRIVATE
    private void sensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerValues.postValue(event.values);
                break;
            case Sensor.TYPE_LIGHT:
                lightSensorValues.postValue(event.values);
                break;
            default:
                return;
        }
    }

    private void initWithPreferences(SharedPreferences preferences) {
        String lightPreferences = preferencesFileName(Sensor.TYPE_LIGHT);
        String accPreferences = preferencesFileName(Sensor.TYPE_ACCELEROMETER);
        boolean lightSensorShouldBeOn = preferences.getBoolean(lightPreferences, false);
        boolean accelerometerShouldBeOn = preferences.getBoolean(accPreferences, false);

        if (lightSensorShouldBeOn) {
            turnSensorOn(Sensor.TYPE_LIGHT);
        }
        if (accelerometerShouldBeOn) {
            turnSensorOn(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private String preferencesFileName(int sensorType) {
        return "sensor_preferences_" + sensorType;
    }
    // endregion
}
