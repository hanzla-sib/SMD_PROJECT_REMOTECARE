package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Doctor1 extends AppCompatActivity {

    DrawerLayout doc_drawerLayout;
    ImageView Menu,logo;
    Button msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor1);
        doc_drawerLayout = findViewById(R.id.doctor_drawer_layout);
        Menu = findViewById(R.id.menu);
        logo=findViewById(R.id.rclogo);
        msg=findViewById(R.id.messageall);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Doctor1.this,messagemain.class);
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

    //next 3 functions are different for the doctor/patient
    public void ClickProfile (View view){

        Intent intent = new Intent(this, Doc_Profile.class);
        startActivity(intent);

    }
    public void ClickPatientDetails (View view){

        Intent intent = new Intent(this, Doc_Patient_Details.class);
        startActivity(intent);
    }
    public void ClickPrescriptionDetailsDoc (View view){

        Intent intent = new Intent(this, Doc_Prescription.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }

    }
}