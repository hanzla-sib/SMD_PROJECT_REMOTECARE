package com.waqasahmad.remotecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Doc_All_Appointments extends AppCompatActivity {

    //
    CardView accepted_appointments, pending_appointments;

    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_all_appointments);

        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.doc_home_btn2);
        btn2 = findViewById(R.id.doc_appointment_btn);
        btn3 = findViewById(R.id.profile_doc_button);
        btn4 = findViewById(R.id.doc_chat_btn);
        btn2.setBackgroundResource(R.drawable.nav_btn_color);


        pending_appointments = findViewById(R.id.doc_pending_appointments);
        accepted_appointments = findViewById(R.id.doc_accepted_appointments);


        pending_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Doc_All_Appointments.this, Doc_Appointments_pending.class);
                startActivity(intent);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_All_Appointments.this, Doctor1.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_All_Appointments.this, Doc_Profile.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_All_Appointments.this, messagemain.class));
            }
        });


        accepted_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Doc_All_Appointments.this, Doc_appointments_accepted.class);
                startActivity(intent);
            }
        });
    }
}