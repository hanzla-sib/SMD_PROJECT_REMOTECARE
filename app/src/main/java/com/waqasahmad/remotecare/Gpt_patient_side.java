package com.waqasahmad.remotecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Gpt_patient_side extends AppCompatActivity {

    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;
    EditText query;
    Button submit_query_btn;
    ScrollView scroll_view_answer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpt_patient_side);

        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.home_btn2);
        btn2 = findViewById(R.id.appointment_btn);
        btn3 = findViewById(R.id.record_btn);
        btn4 = findViewById(R.id.chat_btn);
        query =findViewById(R.id.gpt_query);
        submit_query_btn =findViewById(R.id.submit_query_btn);
        scroll_view_answer =findViewById(R.id.scroll_view_answer);






        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gpt_patient_side.this, MainActivity2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gpt_patient_side.this, Patient_All_appointments.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gpt_patient_side.this, Add_records.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gpt_patient_side.this, messagemain.class));
            }
        });


    }
}