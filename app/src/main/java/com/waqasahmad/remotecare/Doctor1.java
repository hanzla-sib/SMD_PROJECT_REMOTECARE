package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Doctor1 extends AppCompatActivity {

    DrawerLayout doc_drawerLayout;
    ImageView Menu,logo;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    TextView doctor_name;
    String name;

    LinearLayout btn2,btn3,btn4;

    CardView appointment,chat,Steps_pat,Calories_Burnt,CaloriesConsumed,HR_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor1);
        doc_drawerLayout = findViewById(R.id.doctor_drawer_layout);
        Menu = findViewById(R.id.menu);
        logo=findViewById(R.id.rclogo);
        doctor_name=findViewById(R.id.doctor_name);
        Steps_pat=findViewById(R.id.Steps_progress);
        Calories_Burnt=findViewById(R.id.CaloriesBurnt);
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        CaloriesConsumed=findViewById(R.id.CaloriesConsumed);
        HR_graph=findViewById(R.id.HR_graph);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);

        // for logging out
        auth1=FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");


        appointment=findViewById(R.id.appointments_doc);
        chat=findViewById(R.id.chat_doc);

        String useremail1 = auth1.getCurrentUser().getEmail();

        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);




        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, Doc_All_Appointments.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, Doc_Profile.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, messagemain.class));
            }
        });

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
                            doctor_name.setText("Hi, Dr." + dname);

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                    }
                });

        CaloriesConsumed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, Consumed_calories_Progress_Doctor_side.class));
            }
        });

        HR_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, HeartRate_progress_doc_side.class));
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Doctor1.this, Doc_All_Appointments.class));

            }
        });

        Steps_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doctor1.this, Steps_Progress_Doctor_side.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Doctor1.this, messagemain.class));


            }
        });

        Calories_Burnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Doctor1.this, Burnt_calories_Progress_Doctor_side.class);
                startActivity(intent);
            }
        });

        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doc_drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    doc_drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

    }

    public void ClickPro (View view) {
        recreate();
    }

    //next 4 functions are different for the doctor/patient
    public void ClickProfile (View view){

        Intent intent = new Intent(this, Doc_Profile.class);
        startActivity(intent);

    }
    public void ClickAppointmentsDoc (View view){

        Intent intent = new Intent(this, Doc_All_Appointments.class);
        startActivity(intent);
    }
    public void ClickPrescriptionDetailsDoc (View view){

        Intent intent = new Intent(this, Doc_Prescription.class);
        startActivity(intent);
    }
    public void ClickChatDoc (View view){

        Intent intent = new Intent(this, messagemain.class);
        startActivity(intent);
    }
    public void ClickLogoutDoc (View view){

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
        auth1.signOut();

        finish();

        startActivity(new Intent(Doctor1.this, MainActivity_signin.class));
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
}