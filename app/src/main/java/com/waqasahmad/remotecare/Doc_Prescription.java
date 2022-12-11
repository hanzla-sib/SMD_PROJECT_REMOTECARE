package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Doc_Prescription extends AppCompatActivity {


    DrawerLayout doc_drawerLayout;
    ImageView Menu,logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_prescription);

        doc_drawerLayout = findViewById(R.id.doc_prescription);
        Menu = findViewById(R.id.menu);
        logo=findViewById(R.id.rclogo);


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

    public void ClickPrescriptionDetailsDoc (View view){

        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //next 3 functions are different for the doctor/patient
    public void ClickPro (View view) {

        Intent intent = new Intent(this, Doctor1.class);
        startActivity(intent);
    }
    public void ClickProfile (View view){

        Intent intent = new Intent(this, Doc_Profile.class);
        startActivity(intent);
    }

    public void ClickPatientDetails (View view){

        Intent intent = new Intent(this, Doc_Patient_Details.class);
        startActivity(intent);

    }







}