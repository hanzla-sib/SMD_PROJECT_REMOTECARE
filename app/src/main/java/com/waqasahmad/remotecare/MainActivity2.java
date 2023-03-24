package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {
String useremail1="";
    DrawerLayout drawerLayout;
    String Query1, namefood_str, calorie_str ;
    EditText input_query ;
    ImageButton Enter_button;

    LinearLayout btn1,btn2,btn3,btn4;

    FirebaseFirestore db;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    RecyclerView rv;
    List<MyModel> ls=new ArrayList<>();

    JSONObject obj;
//    private static final String consumed_calories="http://"+Ip_server.getIpServer()+"/smd_project/consumed_calories.php";
//    private static final String user_token_delete="http://"+Ip_server.getIpServer()+"/smd_project/user_token_delete.php";
String url1="",url2="";
    CardView appointment;
    CardView steps;
    CardView step,Calories_Burnt_card,test_record,calorie,HR;


//    Button but,signout;

    CardView display_records;
    FirebaseAuth mAuth;

    //for logging out
//    DatabaseReference reference1;
//    FirebaseAuth auth1;
//    FirebaseDatabase database1;
//    FirebaseFirestore db;

    TextView patient_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer_layout);
        appointment = findViewById(R.id.appointmentcard);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/consumed_calories.php";
        url2 ="http://"+s1+"/smd_project/user_token_delete.php";

        steps = findViewById(R.id.steps_card);
        mAuth=FirebaseAuth.getInstance();

        step = findViewById(R.id.steps_graph_card);
        Calories_Burnt_card = findViewById(R.id.Calories_Burnt_card);
        HR=findViewById(R.id.HR_card);
        test_record = findViewById(R.id.testrecord_card);
        calorie = findViewById(R.id.calories_card);
        input_query = findViewById(R.id.input_query);
        Query1 = input_query.getText().toString();
        Enter_button = findViewById(R.id.Enter_button);

        btn1=findViewById(R.id.home_btn);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);



        // for logging out
        auth1=FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");


        db = FirebaseFirestore.getInstance();

        patient_name= findViewById(R.id.patient_name);

        display_records = findViewById(R.id.display_records);


        
     
        String currentemail = mAuth.getCurrentUser().getEmail();
        useremail1=currentemail;
        db.collection("users").document(useremail1).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                        DocumentSnapshot document = task.getResult();

                        JSONObject obj;
                        obj = new JSONObject(document.getData());

                        try
                        {
                            String dname = obj.getString("Name");
                            patient_name.setText("Hi, " + dname);

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Patient_All_appointments.class)); // for patient view
            }
        });

        display_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Add_records.class));
            }
        });

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity2.this, Step_count.class));
                startActivity(new Intent(MainActivity2.this, Pedometer.class));
            }

        });

        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Steps_Graph.class));
            }
        });

        Calories_Burnt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Calories_Burnt.class));
            }
        });

        test_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Test_Record.class));
            }
        });

        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Calories.class));
            }
        });


        HR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, HeartBeat_graph.class));
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(MainActivity2.this, Patient_All_appointments.class));
         }
     });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, Add_records.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, messagemain.class));
            }
        });



        Enter_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Query1 = input_query.getText().toString();
                input_query.setText("");
                // Concatenating header with the API and getting calories in JSON file format
                {
                    StringRequest myReq = new StringRequest(Request.Method.GET,
                            "https://api.calorieninjas.com/v1/nutrition?query=" + Query1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response)
                                {
                                    try {
                                        obj =new JSONObject(response);

                                        calorie_str = String.valueOf(obj.getJSONArray("items").getJSONObject(0).getString("calories"));

                                        ////////////////////////////////////////////

                                        StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response)
                                            {
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
                                            protected Map<String, String> getParams() throws AuthFailureError
                                            {
                                                Map<String,String> param=new HashMap<String,String>();
                                                param.put("p_email",currentemail);
                                                param.put("calories",calorie_str);
                                                return param;
                                            }
                                        };
                                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                        queue.add(request);

                                        //////////////////////////////////////////////

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }




                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(MainActivity2.this,error.toString(),Toast.LENGTH_LONG).show();
                                    Log.d("dataa",error.toString());

                                }
                            })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> mHeaders = new ArrayMap<String, String>();
                            mHeaders.put("Content-Type", "application/json");
                            mHeaders.put("X-Api-Key", "syfXOOkubhTjCgJFOr6KGQ==mYTrMBkvTiw6piBu");
                            return mHeaders;
                        }
                    };

                    RequestQueue requestQueue  = Volley.newRequestQueue(MainActivity2.this);
                    requestQueue.add(myReq);

                }


            }
        });
            }


    public void ClickMenu(View view) {

        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {

        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickPro (View view) {
        recreate();
    }
    public void ClickProfile (View view){

        redirectActivity(this,Profile.class );
    }
    public void ClickPrescriptionDetails (View view){
        redirectActivity(this, Prescription_Details.class);
    }
    public void ClickMeals(View view){
        MainActivity2.redirectActivity(this,Meals.class);
    }
    public void ClickOverview(View view){
        MainActivity2.redirectActivity(this,OverView.class);
    }
    public void ClickChat(View view){
        MainActivity2.redirectActivity(this,messagemain.class);
    }
    public void ClickVideoCapture(View view){
        MainActivity2.redirectActivity(this,Capture_Video.class);
    }

    public void ClickLogout(View view)
    {

        String savecurrentdate;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        String savetime=currentTime.format(calendar.getTime());
        HashMap<String,Object> onlinestatus=new HashMap<>();
        onlinestatus.put("time",savetime);
        onlinestatus.put("date",savecurrentdate);
        onlinestatus.put("status","offline");
        onlinestatus.put("player_id","");
        String curruserid=auth1.getUid();
        reference1.child(curruserid).updateChildren(onlinestatus);
        StringRequest request=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

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
                param.put("email",useremail1);

                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


        auth1.signOut();



        finish();


        startActivity(new Intent(MainActivity2.this, MainActivity_signin.class));
    }



    public static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);

    }

}
