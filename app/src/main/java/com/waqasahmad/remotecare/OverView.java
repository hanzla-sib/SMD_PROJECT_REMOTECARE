package com.waqasahmad.remotecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OverView extends AppCompatActivity {

    CardView step, Calories_Burnt_card, test_record, calorie;
    DrawerLayout drawerLayout;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        // Initialize UI elements
        step = findViewById(R.id.steps_graph_card);
        Calories_Burnt_card = findViewById(R.id.Calories_Burnt_card);
        test_record = findViewById(R.id.testrecord_card);
        calorie = findViewById(R.id.calories_card);

        // Initialize Firebase components for logging out
        auth1 = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

        // Set click listeners for the UI elements
        step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverView.this, Steps_Graph.class));
            }
        });

        Calories_Burnt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OverView.this, Calories_Burnt.class));
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


    // Method to handle the "Overview" button click
    public void ClickOverview(View view) {
        recreate();
    }

    public void ClickMenu(View view) {
        MainActivity2.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity2.closeDrawer(drawerLayout);
    }

    public void ClickPro(View view) {
        MainActivity2.redirectActivity(this, MainActivity2.class);
    }

    public void ClickMeals(View view) {
        MainActivity2.redirectActivity(this, Meals.class);
    }

    public void ClickProfile(View view) {
        MainActivity2.redirectActivity(this, Profile.class);

    }

    public void ClickPrescriptionDetails(View view) {
        MainActivity2.redirectActivity(this, Prescription_Details.class);
    }

    public void ClickChat(View view) {
        MainActivity2.redirectActivity(this, messagemain.class);
    }

    // Method to handle the "Logout" button click
    public void ClickLogout(View view) {

        // Update online status in Firebase
        String savecurrentdate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String savetime = currentTime.format(calendar.getTime());
        HashMap<String, Object> onlinestatus = new HashMap<>();
        onlinestatus.put("time", savetime);
        onlinestatus.put("date", savecurrentdate);
        onlinestatus.put("status", "offline");
        onlinestatus.put("player_id", "");
        String curruserid = auth1.getUid();
        reference1.child(curruserid).updateChildren(onlinestatus);

        // Sign out the user and navigate to the sign-in activity
        auth1.signOut();
        finish();
        startActivity(new Intent(OverView.this, MainActivity_signin.class));
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}