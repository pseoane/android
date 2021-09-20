package dte.masteriot.mdp.sensors01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button bLight;
    Button bAcc;
    TextView tvSensorValue;
    TextView accSensorValue;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor accSensor;
    boolean lightSensorIsActive;
    boolean accelerometerSensorActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerometerSensorActive = false;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setupLightSensor(sensorManager);
        setupAccSensor(sensorManager);
    }

    // Methods related to the SensorEventListener interface:

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Show the sensor's value in the UI:
       // tvSensorValue.setText(Float.toString(sensorEvent.values[0]));
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accSensorValue.setText(
                                "X: " + sensorEvent.values[0]
                                + "\nY: " + sensorEvent.values[1]
                                + "\nZ: " + sensorEvent.values[2]
                        );
                break;
            case Sensor.TYPE_LIGHT:
                tvSensorValue.setText(Float.toString(sensorEvent.values[0]));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // In this app we do nothing if sensor's accuracy changes
    }

    private void setupLightSensor(SensorManager manager) {
        lightSensorIsActive = false;
        bLight = findViewById(R.id.bLight); // button to start/stop sensor's readings
        tvSensorValue = findViewById(R.id.lightMeasurement); // sensor's values
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Listener for the button:
        bLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lightSensorIsActive) {
                    // unregister listener and make the appropriate changes in the UI:
                    sensorManager.unregisterListener(MainActivity.this, lightSensor);
                    bLight.setText(R.string.light_sensor_off);
                    bLight.setBackground(getResources().getDrawable(R.drawable.round_button_off));
                    tvSensorValue.setText("Light sensor is OFF");
                    lightSensorIsActive = false;
                } else {
                    // register listener and make the appropriate changes in the UI:
                    sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    bLight.setText(R.string.light_sensor_on);
                    bLight.setBackground(getResources().getDrawable(R.drawable.round_button_on));
                    tvSensorValue.setText("Waiting for first light sensor value");
                    lightSensorIsActive = true;
                }
            }
        });
    }

    private void setupAccSensor(SensorManager manager) {
        accelerometerSensorActive = false;
        bAcc = findViewById(R.id.bAcc);
        accSensorValue = findViewById(R.id.accx);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        bAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accelerometerSensorActive) {
                    sensorManager.unregisterListener(MainActivity.this, accSensor);
                    bAcc.setBackground(getResources().getDrawable(R.drawable.round_button_off));
                    bAcc.setText(R.string.acc_sensor_off);
                    accSensorValue.setText("Accelerometer sensor is OFF");
                    accelerometerSensorActive = false;
                } else {
                    sensorManager.registerListener(MainActivity.this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
                    bAcc.setBackground(getResources().getDrawable(R.drawable.round_button_on));
                    bAcc.setText("Waiting for first accelerometer sensor value");
                    bAcc.setText(R.string.acc_sensor_on);
                    accelerometerSensorActive = true;
                }
            }
        });
    }

}
