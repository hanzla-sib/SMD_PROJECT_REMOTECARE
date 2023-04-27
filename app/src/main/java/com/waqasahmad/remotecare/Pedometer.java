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

    private static final float STRIDE_LENGTH = 0.7f; // in meters
    private static final float RESTING_THRESHOLD = 2.0f; // km/h
    private static final float RUNNING_THRESHOLD = 8.0f; // km/h
    private static final long MILLISECONDS_IN_SECOND = 1000;
    private static final int REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 1;
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

    String consumer_url = "";
    String producer_url = "";
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    LinearLayout back_btn;

    //    private static final String update_user_steps ="http://"+Ip_server.getIpServer()+"/smd_project/update_daily_steps.php";
//    private static final String initial_steps_from_DB ="http://"+Ip_server.getIpServer()+"/smd_project/initial_steps_from_DB.php";
    String url1 = "", url2 = "";
    String ip_url = "";
    JSONArray obj;
    //
    double caloriesburnt = 0.0;
    String useremail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


        stepCountTextView = findViewById(R.id.tv_steps);
        distanceTextView = findViewById(R.id.distance);
        paceTextView = findViewById(R.id.speed);
        back_btn = findViewById(R.id.back_btn);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/update_daily_steps.php";
        url2 = "http://" + s1 + "/smd_project/initial_steps_from_DB.php";

        /////////////////////////////////////////////////////////////////////////////////////////

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        useremail = mAuth.getCurrentUser().getEmail();

        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
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
//
//


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
        //


//        OkHttpClient okHttpClient = new OkHttpClient();
//        ip_url = "http://192.168.100.53:5000/";
//        consumer_url = ip_url+"consumer";
////        consumer_url = ip_url+"one";
//        okhttp3.Request request1= new okhttp3.Request.Builder().url(consumer_url).build();
//        okHttpClient.newCall(request1).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
//            }
//            @Override
//            public void onResponse(@NonNull Call call, okhttp3.Response response) throws IOException {
//                Log.d("valuee", "network success");
////                tv.setText(response.body().string());
//            }
//        });


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACTIVITY_RECOGNITION},
                    REQUEST_ACTIVITY_RECOGNITION_PERMISSION);
        } else {

        }


        ////////////////////////////////////////////////////////////////////////////////////////

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


        ////////////////////////////////////////////////////////////////////////


//        OkHttpClient okHttpClient = new OkHttpClient();
//        producer_url = ip_url+"producer/"+useremail+"/"+String.valueOf(stepCount);
//        //        producer_url = ip_url+ip_url+"two";
//        okhttp3.Request request2= new okhttp3.Request.Builder().url(producer_url).build();
//        okHttpClient.newCall(request2).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//            @Override
//            public void onResponse(@NonNull Call call, okhttp3.Response response) throws IOException {
//                Log.d("valuee", "network success");
//                //               tv.setText(response.body().string());
//            }
//        });

        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                distanceCovered = STRIDE_LENGTH * stepCount;
                distanceTextView.setText(String.format("Distance\n" + "%.2f m", distanceCovered));
                pace = distanceCovered / ((float) elapsedTime / MILLISECONDS_IN_SECOND); // m/s
                pace = pace * 3.6f; // km/h
                pace = Math.round(pace * 100) / 100.0f; // round to two decimal places
                paceTextView.setText(String.format("%.2f km/h", pace));
                Log.d("checking", response.toString());
//              Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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

//        ==================================================================================

        //===================================================================


        ///////////////////////////////////////////////////////////////

    }
}