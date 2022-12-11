package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    CardView appointment;
    CardView steps;
    Button but,signout;
    CardView display_records;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        drawerLayout = findViewById(R.id.drawer_layout);
        appointment = findViewById(R.id.appointmentcard);

        steps = findViewById(R.id.steps_card);
        but=findViewById(R.id.msgs);
        signout=findViewById(R.id.signout);
        mAuth=FirebaseAuth.getInstance();

        display_records = findViewById(R.id.display_records);

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Appointments_Patient.class)); // for patient view
            }
        });

        display_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Add_records.class));
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, messagemain.class));
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(MainActivity2.this, MainActivity_signin.class));

//                finish();
            }
        });

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, Step_count.class));
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
