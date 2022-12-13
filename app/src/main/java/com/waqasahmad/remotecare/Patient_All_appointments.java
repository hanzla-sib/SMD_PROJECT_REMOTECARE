package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Patient_All_appointments extends AppCompatActivity {

    CardView patient_all_appointments,patient_accepted_appointments,patient_pending_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_all_appointments);


        patient_all_appointments = findViewById(R.id.patient_all_appointments);
        patient_accepted_appointments=findViewById(R.id.patient_accepted_appointments);
        patient_pending_appointments=findViewById(R.id.patient_pending_appointments);

        patient_all_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Patient_All_appointments.this, Appointments_Patient.class);
                startActivity(intent);

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
    }
}