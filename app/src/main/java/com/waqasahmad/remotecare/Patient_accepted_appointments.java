package com.waqasahmad.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient_accepted_appointments extends AppCompatActivity {

    RecyclerView rv;
    List<Appointment_Model> ls =new ArrayList<>();
    LinearLayout back_btn;
    LinearLayout btn1,btn2,btn3,btn4;


    //
    FirebaseFirestore db;
    FirebaseAuth mAuth;


//    private static final String patient_accepted_appointment="http://"+Ip_server.getIpServer()+"/smd_project/patient_accepted_appointment.php";
String url1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_accepted_appointments);

        rv=findViewById(R.id.rv_accepted_appointments);
        back_btn = findViewById(R.id.back_btn);
        btn1=findViewById(R.id.home_btn2);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/patient_accepted_appointment.php";

        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);




        //////////////////////////////////////////////////////////////////////////////////////////

        StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("respons11111111" ,response );
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                try {
                    JSONArray obj2 = new JSONArray(response);


                    for(int i=0;i<obj2.length();i++){
                        JSONObject jsonObject = obj2.getJSONObject(i);

                        String name = jsonObject.getString("d_name");
                        String email = jsonObject.getString("d_email");
                        String image = jsonObject.getString("imageurl");

                        String date = jsonObject.getString("Date1");
                        String time = jsonObject.getString("Time1");



                        Appointment_Model doc_model = new Appointment_Model();
                        doc_model.setName_doc(name);
                        doc_model.setEmail_doc(email);
                        doc_model.setImage_doc(image);
                        doc_model.setDate_doc(date);
                        doc_model.setTime_doc(time);

                        ls.add(doc_model);

                    }

                    P_accepted_appointments_adapter adapter = new P_accepted_appointments_adapter(ls, Patient_accepted_appointments.this);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Patient_accepted_appointments.this);
                    rv.setLayoutManager(lm);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        //*********************************


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_accepted_appointments.this, MainActivity2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_accepted_appointments.this, Patient_All_appointments.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_accepted_appointments.this, Add_records.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_accepted_appointments.this, messagemain.class));
            }
        });

    }
}