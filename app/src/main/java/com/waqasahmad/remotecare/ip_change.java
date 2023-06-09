package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ip_change extends AppCompatActivity {


    EditText ip_edit_text;
    Button submit_ip;

    String Ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_change);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        ip_edit_text=findViewById(R.id.ip_edit_text);
        submit_ip=findViewById(R.id.submit_ip);

        submit_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip_edit_text.getText().toString().equals(""))
                {
                }
                else
                {
                    // setting IP address
                   Ip = ip_edit_text.getText().toString();
                    Log.d("Ip == " , Ip);
                    myEdit.putString("Ip", Ip);
                    myEdit.apply();
                    startActivity(new Intent(ip_change.this, MainActivity_signup.class));
                }
            }
        });
    }
}