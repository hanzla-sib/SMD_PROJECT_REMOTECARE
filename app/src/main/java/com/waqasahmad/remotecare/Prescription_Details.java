package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class Prescription_Details extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_details);

        drawerLayout=findViewById(R.id.drawer_layout);

    }

    public void ClickPrescriptionDetails (View view){
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
    public void ClickOverview(View view){
        MainActivity2.redirectActivity(this,OverView.class);
    }
    public void ClickProfile(View view){
        MainActivity2.redirectActivity(this, Profile.class);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}