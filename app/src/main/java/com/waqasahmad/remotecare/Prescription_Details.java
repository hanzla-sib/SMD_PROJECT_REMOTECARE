package com.waqasahmad.remotecare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Prescription_Details extends AppCompatActivity {

    DrawerLayout drawerLayout;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_details);

        drawerLayout = findViewById(R.id.drawer_layout);

        // for logging out
        auth1 = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

    }

    public void ClickPrescriptionDetails(View view) {
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

    public void ClickOverview(View view) {
        MainActivity2.redirectActivity(this, OverView.class);
    }

    public void ClickProfile(View view) {
        MainActivity2.redirectActivity(this, Profile.class);

    }

    public void ClickChat(View view) {
        MainActivity2.redirectActivity(this, messagemain.class);
    }

    public void ClickLogout(View view) {

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
        auth1.signOut();

        finish();

        startActivity(new Intent(Prescription_Details.this, MainActivity_signin.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}