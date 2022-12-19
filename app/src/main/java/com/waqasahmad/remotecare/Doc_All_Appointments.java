package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Doc_All_Appointments extends AppCompatActivity {

    //
    CardView accepted_appointments,pending_appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_all_appointments);


        pending_appointments = findViewById(R.id.doc_pending_appointments);
        accepted_appointments = findViewById(R.id.doc_accepted_appointments);



        pending_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Doc_All_Appointments.this, Doc_Appointments_pending.class);
                startActivity(intent);
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