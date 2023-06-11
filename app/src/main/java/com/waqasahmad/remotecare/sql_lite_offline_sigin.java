package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class sql_lite_offline_sigin extends AppCompatActivity {

    ImageButton signin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_lite_offline_sigin);


        signin2=findViewById(R.id.signinbutton2);


        signin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(sql_lite_offline_sigin.this,ShowimagesActivity.class);
                startActivity(intent);

            }
        });






    }
}