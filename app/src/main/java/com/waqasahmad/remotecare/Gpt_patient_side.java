package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Gpt_patient_side extends AppCompatActivity {
    public static final MediaType JSON=MediaType.get("application/json; charset=utf-8");
    OkHttpClient client=new OkHttpClient();

    private static final String[] STOP = new String[] { "." };
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


        submit_query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prompt = query.getText().toString();
                Log.d("promot", prompt);
                try {
                    callApi(prompt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




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

    void callApi(String question) throws JSONException {
        Log.d("prompt",question);
        JSONObject jsonBody=new JSONObject();

        jsonBody.put("model","text-davinci-003");
        jsonBody.put("prompt",question);
        jsonBody.put("max_tokens",4000);
        jsonBody.put("temperature",0);

        RequestBody body= RequestBody.create(jsonBody.toString(),JSON);
        Request request= new Request.Builder().url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            Log.d("promot","failure");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {


                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("hello object",jsonObject.toString());
                    if (jsonObject.has("choices")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        Log.d("hello ,", result.trim());
                    } else {
                        Log.d("response", "No 'choices' key in JSON object");
                    }
                } catch (JSONException e) {
                    Log.d("response", "Error parsing JSON object: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });
    }

}