package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class AboutUs extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout=findViewById(R.id.drawer_layout);

    }
    // Method to handle click event on "About Us" button
    public void ClickAboutUs (View view){
        recreate();// Recreate the activity to refresh the UI

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

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}