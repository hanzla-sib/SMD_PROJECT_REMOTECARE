package com.waqasahmad.remotecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Patient_All_appointments extends AppCompatActivity {

    CardView patient_all_appointments, patient_accepted_appointments, patient_pending_appointments;
    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_all_appointments);

        // Initialize views
        patient_all_appointments = findViewById(R.id.patient_all_appointments);
        patient_accepted_appointments = findViewById(R.id.patient_accepted_appointments);
        patient_pending_appointments = findViewById(R.id.patient_pending_appointments);

        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.home_btn2);
        btn2 = findViewById(R.id.appointment_btn);
        btn2.setBackgroundResource(R.drawable.nav_btn_color);
        btn3 = findViewById(R.id.record_btn);
        btn4 = findViewById(R.id.chat_btn);

        // Set click listeners for different appointment categories
        patient_all_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_All_appointments.this, Appointments_Patient.class);
                startActivity(intent);
            }
        });

        // Set click listeners for buttons
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        patient_accepted_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Patient_All_appointments.this, Patient_accepted_appointments.class);
                startActivity(intent);
            }
        });

        patient_pending_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Patient_All_appointments.this, Patient_pending_appointments.class);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_All_appointments.this, MainActivity2.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_All_appointments.this, Add_records.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_All_appointments.this, messagemain.class));
            }
        });
    }
}