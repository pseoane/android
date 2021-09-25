package dte.masteriot.mdp.sensors01;
import dte.masteriot.mdp.sensors01.utils.SensorNotAvailableException;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.MutableLiveData;

public class CustomSensorListener implements SensorEventListener {
    // region Properties
    private MutableLiveData events;
    private SensorManager sensorManager;
    // endregion

    // region Constructor
    CustomSensorListener(Application application) {
        sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
    }
    // endregion

    // region Public methods
    public MutableLiveData<SensorEvent> getEvents() {
        if (events == null) {
            events = new MutableLiveData<SensorEvent>();
        }
        return events;
    }

    public void registerSensor(Integer sensorType) throws SensorNotAvailableException {
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            throw new SensorNotAvailableException();
        }
    }

    public void unregisterSensor(Integer sensorType) throws SensorNotAvailableException {
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            sensorManager.unregisterListener(this, sensor);
        } else {
            throw new SensorNotAvailableException();
        }
    }

    // region Override methods
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        events.postValue(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Unimplemented
    }
    // endregion
    // endregion
}
