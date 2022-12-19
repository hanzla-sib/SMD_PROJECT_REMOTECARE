package com.waqasahmad.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Step_count extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mStepCounter;
    private boolean isCounterSensorPresent;
    private TextView tv_steps;
    private TextView time_set;
    private TextView daily_steps;
    int stepCount=0;
    int numSteps = 0;
    int today_Steps = 0;
    String ss;
    String useremail="";
    boolean flag = false;

    private static final String update_user_steps ="http://"+Ip_server.getIpServer()+"/smd_project/update_daily_steps.php";

    FirebaseFirestore db;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tv_steps=findViewById(R.id.tv_steps);
        time_set = findViewById(R.id.time_set);
        daily_steps = findViewById(R.id.time_after);

        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        useremail = mAuth.getCurrentUser().getEmail();
        //Initializing Firebase MAuth instance


        updateTimeOnEachSecond();
//        if (ContextCompat.checkSelfPermission(Step_count.this,
//                Manifest.permission.ACTIVITY_RECOGNITION)
//                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
////
//        }
//        else{
//            Toast.makeText(this, "permission  granted", Toast.LENGTH_SHORT).show();
//        }
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mStepCounter=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent=true;

        }
        else{
            tv_steps.setText("Counter not found");
            isCounterSensorPresent=false;
        }

    }

    private void stopSelf() {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor==mStepCounter){

            stepCount=(int)sensorEvent.values[0];

            tv_steps.setText(String.valueOf(stepCount));


//================================================

            StringRequest request=new StringRequest(Request.Method.POST, update_user_steps, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    Log.d("respons11111111" ,response );
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

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
                    param.put("steps",String.valueOf(stepCount - numSteps));
                    return param;
                }
            };
            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

//==========================================



        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
//            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            sensorManager.registerListener(this,mStepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void updateTimeOnEachSecond() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Date currentTime = Calendar.getInstance().getTime();
                int hours = currentTime.getHours();


                SimpleDateFormat sdf4_h = new SimpleDateFormat("h");
                SimpleDateFormat sdf4_mm = new SimpleDateFormat("mm");
                SimpleDateFormat sdf4_a = new SimpleDateFormat("a");
                SimpleDateFormat sdf4_ss = new SimpleDateFormat("ss");
                String h = sdf4_h.format(new Date());
                String mm = sdf4_mm.format(new Date());
                String a = sdf4_a.format(new Date());
                String ss = sdf4_ss.format(new Date());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        time_set.setText(h+":"+mm+":"+ss+" "+a);
                        if((h.equals("9")) && (mm.equals("52")) && (a.equals("pm"))){
                            if (flag==false)
                            {
                                flag = true;
                                today_Steps = stepCount - numSteps;
                                numSteps = stepCount;
                                daily_steps.setText(String.valueOf(today_Steps));
                            }
                        }
                        else{
                            flag = false;
                        }
                    }
                });

            }
        }, 0, 1000);

    }
}