package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OverView extends AppCompatActivity {

    CardView step,heart_beat,test_record,calorie;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_view);
        drawerLayout=findViewById(R.id.drawer_layout);

        step = findViewById(R.id.steps_card);
        heart_beat = findViewById(R.id.heartbeat_card);
        test_record = findViewById(R.id.testrecord_card);
        calorie = findViewById(R.id.calories_card);


//        step.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(OverView.this, Steps.class));
//            }
//        });

        heart_beat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverView.this, Heart_Beat.class));
            }
        });


        test_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverView.this, Test_Record.class));
            }
        });

        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverView.this, Calories.class));
            }
        });




    }


    public void ClickOverview (View view){
        recreate();
    }

    public void ClickMenu (View view){
        MainActivity2.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MainActivity2.closeDrawer(drawerLayout);
    }
    public void ClickPro(View view){
        MainActivity2.redirectActivity(this,MainActivity2.class);
    }
    public void ClickMeals(View view){
        MainActivity2.redirectActivity(this,Meals.class);
    }
    public void ClickProfile(View view){
        MainActivity2.redirectActivity(this, Profile.class);

    }
    public void ClickPrescriptionDetails (View view){
        MainActivity2.redirectActivity(this, Prescription_Details.class);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}