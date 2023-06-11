package com.waqasahmad.remotecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Steps_Graph extends AppCompatActivity {

    BarChart weekly_barchart, monthly_barchart;
    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail = "";
    JSONArray obj2, obj3;
    JSONObject obj4;

    RecyclerView rv;
    List<Steps_Model_Graph_Page> ls = new ArrayList<>();

    TextView doc_name, recommended_steps, remaining_steps;
    Integer daily_int, recommended_int, remaining_int;

    ArrayList<Steps_Modal> Steps_MODAL_weekly = new ArrayList<>();
    ArrayList<Steps_Modal> Steps_MODAL_monthly = new ArrayList<>();
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> Labelsname;
    ArrayList<BarEntry> barEntryArrayListmonthly;
    ArrayList<String> Labelsnamemonthly;

    String url1 = "", url2 = "", url3 = "", url4 = "";
    String d_email_steps_recommeded = "";
    String steps_steps_recommeded = "";

    List<String> d_name_array = new ArrayList<String>();
    List<String> recommended_steps_array = new ArrayList<String>();
    String daily_steps_global = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_graph);

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();
        weekly_barchart = findViewById(R.id.graph2);
        monthly_barchart = findViewById(R.id.graph3);
        weekly_barchart.setBackgroundColor(Color.WHITE);
        monthly_barchart.setBackgroundColor(Color.WHITE);


        currentemail = mAuth.getCurrentUser().getEmail();
        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.home_btn2);
        btn2 = findViewById(R.id.appointment_btn);
        btn3 = findViewById(R.id.record_btn);
        btn4 = findViewById(R.id.chat_btn);

        doc_name = findViewById(R.id.d_name_steps_screen);
        recommended_steps = findViewById(R.id.recommended_steps_steps_screen);
        remaining_steps = findViewById(R.id.remaining_steps_steps_screen);

        rv = findViewById(R.id.steps_rv);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/Steps_graph.php";
        url2 = "http://" + s1 + "/smd_project/monthlyStepsgraph.php";
        url3 = "http://" + s1 + "/smd_project/get_doc_recommended_steps.php";
        url4 = "http://" + s1 + "/smd_project/daily_steps.php";

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Graph.this, MainActivity2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Graph.this, Patient_All_appointments.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Graph.this, Add_records.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Graph.this, messagemain.class));
            }
        });


        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.toString().equals("No entry")) {

                }
                else
                {
                    // Clear previous data
                    Steps_MODAL_weekly.clear();
                    barEntryArrayList = new ArrayList<>();
                    Labelsname = new ArrayList<>();
                    barEntryArrayList.clear();
                    Labelsname.clear();

                    try {
                        obj2 = new JSONArray(response);
                        int totalsize = obj2.length();
                        int starting = 0;
                        if (totalsize >= 7) {
                            starting = obj2.length() - 7;
                        }

                        // Retrieve data from JSON response
                        for (int i = starting; i < obj2.length(); i++)
                        {
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("date");
                            String halfdate = "";

                                for (int j = 5; j < date.length(); j++)
                                {
                                    halfdate += date.charAt(j);
                                }

                            String Calorie = jsonObject.getString("steps");
                            if (!Calorie.equals("null")) {
                                Steps_MODAL_weekly.add(new Steps_Modal(halfdate, Integer.parseInt(Calorie)));
                            }
                        }

                        // Prepare data for bar chart
                        for (int i = 0; i < Steps_MODAL_weekly.size(); i++) {
                            String date = Steps_MODAL_weekly.get(i).getDate();
                            int stepss = Steps_MODAL_weekly.get(i).getSteps();
                            barEntryArrayList.add(new BarEntry(i, stepss));
                            Labelsname.add(date);
                        }

                        // Create bar chart and set data
                        BarDataSet barDataSetweekly = new BarDataSet(barEntryArrayList, "Weekly Steps");
                        barDataSetweekly.setColors(ColorTemplate.PASTEL_COLORS);
                        Description description_weekly = new Description();
                        description_weekly.setText("-");
                        weekly_barchart.setDescription(description_weekly);
                        BarData barData_weekly = new BarData((barDataSetweekly));
                        weekly_barchart.setData(barData_weekly);

                        // Configure X-axis
                        XAxis xAxis_weekly = weekly_barchart.getXAxis();
                        xAxis_weekly.setValueFormatter(new IndexAxisValueFormatter(Labelsname));
                        xAxis_weekly.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis_weekly.setDrawGridLines(false);
                        xAxis_weekly.setDrawAxisLine(false);
                        xAxis_weekly.setGranularity(1f);
                        xAxis_weekly.setLabelCount(Labelsname.size());
                        xAxis_weekly.setLabelRotationAngle(0);

                        // Animate and invalidate chart
                        weekly_barchart.animateY(1500);
                        weekly_barchart.invalidate();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Set request parameters
                Map<String, String> param = new HashMap<String, String>();
                param.put("p_email", currentemail);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


        //===================MONTHLY
        {
            StringRequest request1 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response.toString().equals("No entry")) {

                    } else {

                        // Clear previous data
                        Steps_MODAL_monthly.clear();
                        barEntryArrayListmonthly = new ArrayList<>();
                        Labelsnamemonthly = new ArrayList<>();
                        barEntryArrayListmonthly.clear();
                        Labelsnamemonthly.clear();

                        try {
                            obj2 = new JSONArray(response);

                            // Retrieve data from JSON response
                            for (int i = 0; i < obj2.length(); i++) {
                                JSONObject jsonObject = obj2.getJSONObject(i);
                                String date = jsonObject.getString("month");
                                String Calorie = jsonObject.getString("totalStepssum");
                                if (!Calorie.equals("null")) {
                                    Steps_MODAL_monthly.add(new Steps_Modal(date, Integer.parseInt(Calorie)));
                                }
                            }

                            // Prepare data for bar chart
                            for (int i = 0; i < Steps_MODAL_monthly.size(); i++) {
                                String date = Steps_MODAL_monthly.get(i).getDate();
                                int Cal = Steps_MODAL_monthly.get(i).getSteps();
                                barEntryArrayListmonthly.add(new BarEntry(i, Cal));
                                Labelsnamemonthly.add(date);
                            }

                            // Create bar chart and set data
                            BarDataSet barDataSet_monthly = new BarDataSet(barEntryArrayListmonthly, "Monthly Steps");
                            barDataSet_monthly.setColors(ColorTemplate.PASTEL_COLORS);
                            Description description = new Description();
                            description.setText("-");
                            monthly_barchart.setDescription(description);
                            BarData barData_monthly = new BarData((barDataSet_monthly));
                            monthly_barchart.setData(barData_monthly);

                            // Configure X-axis
                            XAxis xAxis = monthly_barchart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(Labelsnamemonthly));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setDrawGridLines(false);
                            xAxis.setDrawAxisLine(false);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(Labelsnamemonthly.size());
                            xAxis.setLabelRotationAngle(0);

                            // Animate and invalidate chart
                            monthly_barchart.animateY(2000);
                            monthly_barchart.invalidate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    // Set request parameters
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("p_email", currentemail);
                    return param;
                }
            };
            RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
            queue1.add(request1);
        }


        //===================STEPS RECOMMENDED

        StringRequest request2 = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {

                if (response2.toString().equals("user not found")) {

                } else {
                    try {
                        obj3 = new JSONArray(response2);

                        // Retrieve recommended steps data from JSON response
                        for (int i = 0; i < obj3.length(); i++)
                        {
                            JSONObject jsonObject = obj3.getJSONObject(i);
                            d_email_steps_recommeded = jsonObject.getString("d_email");
                            steps_steps_recommeded = jsonObject.getString("steps");

                            d_name_array.add(d_email_steps_recommeded);
                            recommended_steps_array.add(steps_steps_recommeded);

                            //===================GETTING DAILY STEPS FOR REMAINING STEPS

                        }//for loop ends

                        // Request daily steps for remaining steps calculation
                        StringRequest request3 = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response3) {

                                if (response3.toString().equals("No entry")) {

                                } else {
                                    String daily_steps_str = response3.trim();
                                    daily_steps_global = daily_steps_str;

                                    //---------------------
                                    int i;
                                    for (i = 0; i < d_name_array.size(); i++) {

                                        daily_int = Integer.parseInt(daily_steps_str);
                                        recommended_int = Integer.parseInt(recommended_steps_array.get(i));
                                        remaining_int = recommended_int - daily_int;
                                        Steps_Model_Graph_Page steps_model = new Steps_Model_Graph_Page();
                                        steps_model.setD_email("Dr." + d_name_array.get(i));
                                        steps_model.setRecommended_steps("Recommended Steps: " + recommended_int.toString());
                                        steps_model.setRemaining_steps(remaining_int.toString());
                                        ls.add(steps_model);

                                        Steps_Adapter_Graph_Page adapter = new Steps_Adapter_Graph_Page(ls, Steps_Graph.this);
                                        RecyclerView.LayoutManager lm = new LinearLayoutManager(Steps_Graph.this);
                                        rv.setLayoutManager(lm);
                                        rv.setAdapter(adapter);
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                // Set request parameters
                                Map<String, String> param = new HashMap<String, String>();
                                param.put("p_email", currentemail);
                                return param;
                            }
                        };
                        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
                        queue3.add(request3);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Set request parameters
                Map<String, String> param = new HashMap<String, String>();
                param.put("p_email", currentemail);
                return param;
            }
        };
        RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
        queue2.add(request2);

    }
}