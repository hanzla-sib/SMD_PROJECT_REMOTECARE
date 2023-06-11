package com.waqasahmad.remotecare;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Pedometer extends AppCompatActivity implements SensorEventListener {

    // Constants
    private static final float STRIDE_LENGTH = 0.7f; // in meters
    private static final float RESTING_THRESHOLD = 2.0f; // km/h
    private static final float RUNNING_THRESHOLD = 8.0f; // km/h
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;

    // Sensor and sensor manager variables
    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;

    // Pedometer data variables
    private int stepCount = 0;
    private float distanceCovered = 0.0f;
    private long startTime = 0;
    private long endTime = 0;
    private long elapsedTime = 0;
    private float pace = 0.0f;

    // TextViews and UI elements
    private TextView motionTextView;
    private TextView stepCountTextView;
    private TextView distanceTextView;
    private TextView paceTextView;

    // URLs and Firebase variables
    String consumer_url = "";
    String producer_url = "";
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    LinearLayout back_btn;
    String url1 = "", url2 = "";
    String ip_url = "";
    JSONArray obj;

    // Calorie tracking variables
    double caloriesburnt = 0.0;
    String useremail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        // Initialize sensor manager and step detector sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // Initialize UI elements
        stepCountTextView = findViewById(R.id.tv_steps);
        distanceTextView = findViewById(R.id.distance);
        paceTextView = findViewById(R.id.speed);
        back_btn = findViewById(R.id.back_btn);

        // Set click listener for the back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get the IP address from shared preferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/update_daily_steps.php";
        url2 = "http://" + s1 + "/smd_project/initial_steps_from_DB.php";

        // Retrieve initial step count from the database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        useremail = mAuth.getCurrentUser().getEmail();

        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    obj = new JSONArray(response);
                    for (int i = 0; i < obj.length() - 1; i++) {
                        JSONObject jsonObject = obj.getJSONObject(i);
                        String stepsss = jsonObject.getString("steps");
                        stepCount = Integer.parseInt(stepsss);
                    }

                    stepCountTextView.setText(String.valueOf(stepCount));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("email", useremail);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        // Check if activity recognition permission is granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
        } else {

            // Permission is granted, continue with the app
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Register the step detector sensor listener
        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregister the step detector sensor listener
        sensorManager.unregisterListener(this, stepDetectorSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {

            // Step count increased
            stepCount++;
            caloriesburnt = 0.04 * stepCount;
            stepCountTextView.setText(String.valueOf(stepCount));
            updateMotionType();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }


    private void updateMotionType() {

        // Make HTTP request to update the step count and calculate distance, pace, and calories burnt

        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                distanceCovered = STRIDE_LENGTH * stepCount;
                distanceTextView.setText(String.format("Distance\n" + "%.2f m", distanceCovered));
                pace = distanceCovered / ((float) elapsedTime / MILLISECONDS_IN_SECOND); // m/s
                pace = pace * 3.6f; // km/h
                pace = Math.round(pace * 100) / 100.0f; // round to two decimal places
                paceTextView.setText(String.format("%.2f km/h", pace));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("email", useremail);
                param.put("steps", Integer.toString(stepCount));
                param.put("calories_burn", Double.toString(caloriesburnt));
                param.put("Motion", "Resting");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}