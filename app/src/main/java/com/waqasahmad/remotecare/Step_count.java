
//package com.waqasahmad.remotecare;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.pm.PackageManager;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.WindowManager;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//
//public class    Step_count extends AppCompatActivity{
//    private SensorManager sensorManager;
//    private double MagnitudePrevious=0;
//    private Integer stepCount=0;
//    private TextView tv_steps;
//    private TextView time_set;
//    private TextView daily_steps;
//    double caloriesburnt=0.0;
//    JSONArray obj;
//    Boolean checkrep=false;
//    private final static long MICROSECONDS_IN_ONE_MINUTE = 60000000;
//    private String motion="";
//    String useremail="";
//
//    String ip_url = "http://192.168.100.53:5000/";
//    String consumer_url="";
//    String producer_url="";
//    private static final String update_user_steps ="http://"+"Ip_server.getIpServer()"+"/smd_project/update_daily_steps.php";
//    private static final String initial_steps_from_DB ="http://"+"Ip_server.getIpServer()"+"/smd_project/initial_steps_from_DB.php";
//
//    FirebaseFirestore db;
//    FirebaseAuth mAuth;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_step_count);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        tv_steps=findViewById(R.id.tv_steps);
//        time_set = findViewById(R.id.time_set);
//
//        db = FirebaseFirestore.getInstance();
//        mAuth= FirebaseAuth.getInstance();
//        useremail = mAuth.getCurrentUser().getEmail();
//        //Initializing Firebase MAuth instance
//
//
//        //cathcing steps from DB
//
//        //===========================================================================================
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        consumer_url = ip_url+"consumer";
////        consumer_url = ip_url+"one";
//        okhttp3.Request request1= new okhttp3.Request.Builder().url(consumer_url).build();
//        okHttpClient.newCall(request1).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
//                Log.d("valuee", "network success");
////                tv.setText(response.body().string());
//            }
//        });
//
//
//
//
//
//
//
//        //============================================================================================
//
//
//
//
//
//        StringRequest request=new StringRequest(Request.Method.POST, initial_steps_from_DB, new Response.Listener<String>()
//        {
//            @Override
//            public void onResponse(String response)
//            {
//
////
//
//                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
//                try {
//                    obj = new JSONArray(response);
//                    for(int i=0;i<obj.length()-1;i++){
//                        JSONObject jsonObject = obj.getJSONObject(i);
//                        String stepsss = jsonObject.getString("steps");
//                        stepCount=Integer.parseInt(stepsss);
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////
////
//
//
//            }
//        }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//            }
//        })
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> param=new HashMap<String,String>();
//                param.put("email",useremail);
//                return param;
//            }
//        };
//        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
//        //
//
//
//        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        SensorEventListener stepDetector=new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent sensorEvent) {
//                if(sensorEvent!=null){
//                    float x_accel=sensorEvent.values[0];
//                    float y_accel=sensorEvent.values[1];
//                    float z_accel=sensorEvent.values[2];
//
//                    double Magnitude=Math.sqrt(x_accel*x_accel+y_accel*y_accel+z_accel*z_accel);
//                    double MagnitudeDelta=Magnitude-MagnitudePrevious;
//                    MagnitudePrevious=Magnitude;
//                    caloriesburnt=0.04;
//                    if((MagnitudeDelta>=2) && MagnitudeDelta<7){
//                        stepCount++;
//                        caloriesburnt=caloriesburnt*stepCount;
//                        tv_steps.setText(stepCount.toString());
//                        time_set.setText("Walking");
//                        motion="Walking";
//                        checkrep=false;
//                        Log.d("magnitudeee ",Double.toString(MagnitudeDelta));
//
//
//                        //==================================================================================
////                        OkHttpClient okHttpClient = new OkHttpClient();
////
////                        producer_url = ip_url+"producer/"+useremail+"/"+String.valueOf(stepCount);
////                        //        producer_url = ip_url+ip_url+"two";
////                        okhttp3.Request request2= new okhttp3.Request.Builder().url(producer_url).build();
////                        okHttpClient.newCall(request2).enqueue(new Callback() {
////                            @Override
////                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
////                                Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
////                            }
////                            @Override
////                            public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
////                                Log.d("valuee", "network success");
////                                //               tv.setText(response.body().string());
////                            }
////                        });
//                        //===============================================================================
//
//
//                        StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response)
//                            {
////                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//                        }, new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error)
//                            {
//                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        {
//                            @Nullable
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> param=new HashMap<String,String>();
//
//                                param.put("email",useremail);
//                                param.put("steps",stepCount.toString());
//                                param.put("calories_burn",Double.toString(caloriesburnt));
//                                param.put("Motion",motion);
//                                return param;
//                            }
//                        };
//                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//                        queue.add(request);
//
//
//
//                    }
//                    else if(MagnitudeDelta>=7){
//                        stepCount++;
//                        tv_steps.setText(stepCount.toString());
//                        time_set.setText("Running");
//                        motion="Running";
//                        checkrep=false;
//                        Log.d("magnitudeee ",Double.toString(MagnitudeDelta));
//                        caloriesburnt=caloriesburnt*stepCount;
//                        StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response)
//                            {
//
//
//                            }
//                        }, new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error)
//                            {
//                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        {
//                            @Nullable
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> param=new HashMap<String,String>();
//                                param.put("email",useremail);
//                                param.put("steps",stepCount.toString());
//                                param.put("Motion",motion);
//                                param.put("calories_burn",Double.toString(caloriesburnt));
//                                return param;
//                            }
//                        };
//                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//                        queue.add(request);
//
//
//
//                    }
//                    else{
//                        time_set.setText("Resting");
//                        motion="Resting";
//                        caloriesburnt=caloriesburnt*stepCount;
//
//                        if(checkrep==false){
//                            checkrep=true;
//                            Log.d("magnitudeeestop ",Double.toString(MagnitudeDelta));
//                        StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response)
//                            {
//
//
//
//                            }
//                        }, new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error)
//                            {
//                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        })
//                        {
//                            @Nullable
//                            @Override
//                            protected Map<String, String> getParams() throws AuthFailureError {
//                                Map<String,String> param=new HashMap<String,String>();
//                                param.put("email",useremail);
//                                param.put("steps",stepCount.toString());
//                                param.put("Motion",motion);
//                                param.put("calories_burn",Double.toString(caloriesburnt));
//                                return param;
//                            }
//                        };
//                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
//                        queue.add(request);
//
//                        }
//
//                    }
//
////                //==================================================================================
////                    OkHttpClient okHttpClient = new OkHttpClient();
////                    producer_url = ip_url+"producer/Umaid/"+String.valueOf(stepCount);
////                //        producer_url = ip_url+ip_url+"two";
////                    okhttp3.Request request= new okhttp3.Request.Builder().url(producer_url).build();
////                    okHttpClient.newCall(request).enqueue(new Callback() {
////                        @Override
////                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
////                            Log.d("valuee", "network faisaaaaaaaaaaaaaaaaa");
////                        }
////                        @Override
////                        public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
////                            Log.d("valuee", "network success");
////                            //               tv.setText(response.body().string());
////                        }
////                    });
////                    //===============================================================================
//
//
//
//                }
//                else{
//
//                }
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int i) {
//
//            }
//        };
//        sensorManager.registerListener(stepDetector,sensor,SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
//    }
//}