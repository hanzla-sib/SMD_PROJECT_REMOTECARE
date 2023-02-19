package com.waqasahmad.remotecare;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pedometer extends AppCompatActivity implements SensorEventListener {

    private static final float STRIDE_LENGTH = 0.7f; // in meters
    private static final float RESTING_THRESHOLD = 2.0f; // km/h
    private static final float RUNNING_THRESHOLD = 8.0f; // km/h
    private static final long MILLISECONDS_IN_SECOND = 1000;

    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;

    private int stepCount = 0;
    private float distanceCovered = 0.0f;
    private long startTime = 0;
    private long endTime = 0;
    private long elapsedTime = 0;
    private float pace = 0.0f;

    private TextView motionTextView;
    private TextView stepCountTextView;
    private TextView distanceTextView;
    private TextView paceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        motionTextView = findViewById(R.id.motion);
        stepCountTextView = findViewById(R.id.tv_steps);
        distanceTextView = findViewById(R.id.distance);
        paceTextView = findViewById(R.id.speed);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, stepDetectorSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;
            stepCountTextView.setText(String.valueOf(stepCount));

            distanceCovered = STRIDE_LENGTH * stepCount;
            distanceTextView.setText(String.format("%.2f m", distanceCovered));

            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            } else {
                endTime = System.currentTimeMillis();
                elapsedTime = endTime - startTime;
                updatePace();
                updateMotionType();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    private void updatePace() {
        if (elapsedTime > 0 && distanceCovered > 0) {
            pace = distanceCovered / ((float)elapsedTime / MILLISECONDS_IN_SECOND); // m/s
            pace = pace * 3.6f; // km/h
            pace = Math.round(pace * 100) / 100.0f; // round to two decimal places
            paceTextView.setText(String.format("%.2f km/h", pace));
        }
    }

    private void updateMotionType() {
        String motionType = "Resting";
        if (pace > RUNNING_THRESHOLD) {
            motionType = "Running";
        } else if (pace > RESTING_THRESHOLD) {
            motionType = "Walking";
        }
        motionTextView.setText(motionType);
    }
}