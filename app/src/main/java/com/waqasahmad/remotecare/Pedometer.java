package com.waqasahmad.remotecare;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Pedometer extends AppCompatActivity implements SensorEventListener {

    private float[] acceleration = new float[3];

    private SensorManager sensorManager;
    private Sensor accSensor;
    private double bearing = 0;
    private TextView walking;
    private TextView stepView;
    private TextView resting;
    private int stepCount;
    private double magprevious;
    private boolean numberig;
    private int cehckcount;
    boolean check=false;
    private float[] filteredacceleration = new float[3];

    String ip_url = "http://192.168.28.213:5000/";
    String consumer_url="";
    String producer_url="";
    double caloriesburnt=0.0;
    JSONArray obj;
    Boolean checkrep=false;
    private final static long MICROSECONDS_IN_ONE_MINUTE = 60000000;
    private String motion="";
    String useremail="";
    private static final String update_user_steps ="http://"+Ip_server.getIpServer()+"/smd_project/update_daily_steps.php";
    private static final String initial_steps_from_DB ="http://"+Ip_server.getIpServer()+"/smd_project/initial_steps_from_DB.php";

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);
        walking = (TextView) findViewById((R.id.time_set));
        resting=(TextView) findViewById(R.id.resting);
        stepView = (TextView) findViewById((R.id.tv_steps));
        stepCount = 0;
        cehckcount = 5;
        numberig = true;
        stepView.setText("Step Count: " + stepCount);
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        useremail = mAuth.getCurrentUser().getEmail();
        OkHttpClient okHttpClient = new OkHttpClient();
        consumer_url = ip_url+"consumer";
        consumer_url = ip_url+"one";
        okhttp3.Request request1= new okhttp3.Request.Builder().url(consumer_url).build();
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
                Log.d("valuee", "network success");
//                tv.setText(response.body().string());
            }
        });

        StringRequest request=new StringRequest(Request.Method.POST, initial_steps_from_DB, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

//

                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                try {
                    obj = new JSONArray(response);
                    for(int i=0;i<obj.length()-1;i++){
                        JSONObject jsonObject = obj.getJSONObject(i);
                        String stepsss = jsonObject.getString("steps");
                        stepCount=Integer.parseInt(stepsss);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
//


            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("email",useremail);
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        //

    }
    protected float[] lowPassFilter( float[] input, float[] output ) {
        if ( output == null ) return input;
        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + 1.0f * (input[i] - output[i]);
        }
        return output;
    }
    @Override
    protected void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accSensor,
                SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
    }
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this, accSensor);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            filteredacceleration = lowPassFilter(event.values, acceleration);
            acceleration[0] = filteredacceleration[0];
            //0.5730  -2 1
            acceleration[1] = filteredacceleration[1];
            //2.5 6
            acceleration[2] = filteredacceleration[2];
            //5 11
            if(cehckcount!=22){
                resting.setVisibility(View.GONE);
                walking.setVisibility(View.VISIBLE);
                check=true;

            }
            else{

                if(check==true){
                    walking.setVisibility(View.GONE);
                    resting.setVisibility(View.VISIBLE);
                    caloriesburnt=0.04;
                    caloriesburnt=caloriesburnt*stepCount;
                    Log.d("hellooo  ","Resting");
                    StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
//                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();

                            param.put("email",useremail);
                            param.put("steps",Integer.toString(stepCount));
                            param.put("calories_burn",Double.toString(caloriesburnt));
                            param.put("Motion","Resting");
                            return param;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
                check=false;
            }
            if(numberig) {
                cehckcount--;
                numberig = (cehckcount < 0)? false : numberig;
            }
            else
                cehckcount = 22;
            if((acceleration[0]>=-1 && acceleration[0]<=1) && (acceleration[1]>1 && acceleration[1]<=9) && (acceleration[2]>=5 && acceleration[2]<=11) ) {

                if ((Math.abs(magprevious - acceleration[1]) > 0.5) && !numberig) {
                    stepCount++;
                    stepView.setText("Step Count: " + stepCount);

                    numberig = true;
                    caloriesburnt=0.04;
                    caloriesburnt=caloriesburnt*stepCount;

//                    ==================================================================================
                        OkHttpClient okHttpClient = new OkHttpClient();

                        producer_url = ip_url+"producer/"+useremail+"/"+String.valueOf(stepCount);
                        //        producer_url = ip_url+ip_url+"two";
                        okhttp3.Request request2= new okhttp3.Request.Builder().url(producer_url).build();
                        okHttpClient.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
                            }
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
                                Log.d("valuee", "network success");
                                //               tv.setText(response.body().string());
                            }
                        });
                    //===============================================================================

                    StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
//                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param=new HashMap<String,String>();

                            param.put("email",useremail);
                            param.put("steps",Integer.toString(stepCount));
                            param.put("calories_burn",Double.toString(caloriesburnt));
                            param.put("Motion","Walking");
                            return param;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
                magprevious = acceleration[1];
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}