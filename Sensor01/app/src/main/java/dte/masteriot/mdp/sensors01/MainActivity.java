package dte.masteriot.mdp.sensors01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // region Properties
    private Switch bLight;
    private Switch bAcc;
    private TextView tvSensorValue;
    private TextView accSensorValue;
    private MainActivityViewModel viewModel;
    //endregion

    // region Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(R.layout.activity_main);
        setupAccelerometerView();
        setupLightView();
        setupObservers(viewModel);
    }
    // endregion

    // region Private methods
    // region Setup methods
    private void setupObservers(MainActivityViewModel viewModel) {
        if (!viewModel.getAccelerometerValues().hasObservers()) {
            viewModel.getAccelerometerValues().observe(this, values -> {
                onAccelerometerSensorChanged(values);
            });
        }
        if (!viewModel.getLightValues().hasObservers()) {
            viewModel.getLightValues().observe(this, values -> {
                onLightSensorChanged(values);
            });
        }
    }

    private void setupLightView() {
        bLight = findViewById(R.id.lightSwitch); // button to start/stop sensor's readings
        tvSensorValue = findViewById(R.id.lightMeasurement); // sensor's values
        setupLightViewAppearance(viewModel.isSensorOn(Sensor.TYPE_LIGHT));
        // Listener for the button:
        bLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.isSensorOn(Sensor.TYPE_LIGHT)) {
                    viewModel.turnSensorOff(Sensor.TYPE_LIGHT);
                    setupLightViewAppearance(false);
                } else {
                    viewModel.turnSensorOn(Sensor.TYPE_LIGHT);
                    setupLightViewAppearance(true);
                }
            }
        });
    }

    private void setupLightViewAppearance(boolean isSensorOn) {
        if (isSensorOn) {
            bLight.setChecked(true);
            tvSensorValue.setText("Waiting for first light sensor value");
        } else {
            bLight.setChecked(false);
            tvSensorValue.setText("Light sensor is OFF");
        }
    }

    private void setupAccelerometerView() {
        bAcc = findViewById(R.id.accSwitch);
        accSensorValue = findViewById(R.id.accx);
        setupAccelerometerViewAppearance(viewModel.isSensorOn(Sensor.TYPE_ACCELEROMETER));
        bAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.isSensorOn(Sensor.TYPE_ACCELEROMETER)) {
                    viewModel.turnSensorOff(Sensor.TYPE_ACCELEROMETER);
                    setupAccelerometerViewAppearance(false);
                } else {
                    viewModel.turnSensorOn(Sensor.TYPE_ACCELEROMETER);
                    setupAccelerometerViewAppearance(true);
                }
            }
        });
    }

    private void setupAccelerometerViewAppearance(boolean isSensorOn) {
        if (isSensorOn) {
            bAcc.setChecked(true);
            accSensorValue.setText("Waiting for first accelerometer sensor value");
        } else {
            bAcc.setChecked(false);
            accSensorValue.setText("Accelerometer sensor is OFF");
        }
    }
    // endregion

    // region onChanged methods
    private void onLightSensorChanged(float[] values) {
        if (viewModel.isSensorOn(Sensor.TYPE_LIGHT)) {
            tvSensorValue.setText(Float.toString(values[0]));
        }
    }

    private void onAccelerometerSensorChanged(float[] values) {
        if (viewModel.isSensorOn(Sensor.TYPE_ACCELEROMETER)) {
            accSensorValue.setText(
                    "X: " + values[0]
                            + "\nY: " + values[1]
                            + "\nZ: " + values[2]
            );
        }
    }
    // endregion
    // endregion
}
