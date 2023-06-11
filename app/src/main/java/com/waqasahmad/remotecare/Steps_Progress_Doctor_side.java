package com.waqasahmad.remotecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Steps_Progress_Doctor_side extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    JSONArray obj2;

    // For logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    BarChart weekly_barchart, monthly_barchart;
    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;
    TextView recom_steps, comp_steps, check_Ai;
    String currentemail = "";

    ArrayList<Steps_Modal> Steps_MODAL_weekly = new ArrayList<>();
    ArrayList<Steps_Modal> Steps_MODAL_monthly = new ArrayList<>();
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> Labelsname;
    ArrayList<BarEntry> barEntryArrayListmonthly;
    ArrayList<String> Labelsnamemonthly;
    String url1 = "", url2 = "", url3 = "", url4 = "";
    ArrayList<String> paths = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_progress_doctor_side);

        // Initialize bar chart views
        weekly_barchart = findViewById(R.id.graph2);
        monthly_barchart = findViewById(R.id.graph3);
        weekly_barchart.setBackgroundColor(Color.WHITE);
        monthly_barchart.setBackgroundColor(Color.WHITE);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String useremail = mAuth.getCurrentUser().getEmail();
        currentemail = useremail;

        // Initialize UI elements
        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.doc_home_btn2);
        btn2 = findViewById(R.id.doc_appointment_btn);
        btn3 = findViewById(R.id.profile_doc_button);
        btn4 = findViewById(R.id.doc_chat_btn);
        recom_steps = findViewById(R.id.recom_steps);
        comp_steps = findViewById(R.id.comp_steps);
        check_Ai = findViewById(R.id.check_ai);

        // Get the server IP from SharedPreferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/Steps_graph.php";
        url2 = "http://" + s1 + "/smd_project/monthlyStepsgraph.php";
        url3 = "http://" + s1 + "/smd_project/fetch_patient_reg_doctors.php";
        url4 = "http://" + s1 + "/smd_project/get_steps_results_from_formula.php";

        // Set click listener for the back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set click listener for button 1 (Doctor Home)
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Progress_Doctor_side.this, Doctor1.class));
            }
        });

        // Set click listener for button 2 (Doctor Appointments)
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Progress_Doctor_side.this, Doc_All_Appointments.class));
            }
        });

        // Set click listener for button 3 (Doctor Profile)
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Progress_Doctor_side.this, Doc_Profile.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Steps_Progress_Doctor_side.this, messagemain.class));
            }
        });


        StringRequest request = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("No entry")) {

                }
                else {

                    try {
                        obj2 = new JSONArray(response);

                        // Retrieve patient emails from JSON response
                        for (int i = 0; i < obj2.length(); i++) {
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String mail = jsonObject.getString("p_email");
                            paths.add(mail);
                        }

                        // Set up spinner with patient emails
                        spinner = (Spinner) findViewById(R.id.spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Steps_Progress_Doctor_side.this,
                                R.layout.spinner_color, paths);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(Steps_Progress_Doctor_side.this);
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
                param.put("email", useremail);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // Request to fetch weekly steps data
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("No entry")) {

                    // Clear data and reset chart if no entry is found
                    Steps_MODAL_weekly.clear();
                    barEntryArrayList.clear();
                    Labelsname.clear();
                    weekly_barchart.setData(null);
                    weekly_barchart.invalidate();

                }
                else {

                    // Parse the JSON response and populate the chart
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
                        for (int i = starting; i < obj2.length(); i++) {
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("date");
                            String halfdate = "";
                            for (int j = 5; j < date.length(); j++) {
                                halfdate += date.charAt(j);
                            }
                            String Calorie = jsonObject.getString("steps");
                            if (!Calorie.equals("null")) {
                                Steps_MODAL_weekly.add(new Steps_Modal(halfdate, Integer.parseInt(Calorie)));
                            }
                        }

                        for (int i = 0; i < Steps_MODAL_weekly.size(); i++) {
                            String date = Steps_MODAL_weekly.get(i).getDate();
                            int stepss = Steps_MODAL_weekly.get(i).getSteps();
                            barEntryArrayList.add(new BarEntry(i, stepss));
                            Labelsname.add(date);
                        }

                        BarDataSet barDataSetweekly = new BarDataSet(barEntryArrayList, "Weekly Steps");
                        barDataSetweekly.setColors(ColorTemplate.PASTEL_COLORS);
                        Description description_weekly = new Description();
                        description_weekly.setText("-");
                        weekly_barchart.setDescription(description_weekly);
                        BarData barData_weekly = new BarData((barDataSetweekly));
                        weekly_barchart.setData(barData_weekly);


                        XAxis xAxis_weekly = weekly_barchart.getXAxis();
                        xAxis_weekly.setValueFormatter(new IndexAxisValueFormatter(Labelsname));
                        xAxis_weekly.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis_weekly.setDrawGridLines(false);
                        xAxis_weekly.setDrawAxisLine(false);
                        xAxis_weekly.setGranularity(1f);
                        xAxis_weekly.setLabelCount(Labelsname.size());
                        xAxis_weekly.setLabelRotationAngle(0);


                        weekly_barchart.animateY(2000);
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("p_email", (String) adapterView.getItemAtPosition(i));
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


        //===================MONTHLY

        // Request to fetch monthly steps data
        StringRequest request1 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.trim().equals("No entry")) {

                    // Clear data and reset chart if no entry is found
                    Steps_MODAL_monthly.clear();
                    barEntryArrayListmonthly.clear();
                    Labelsnamemonthly.clear();
                    monthly_barchart.setData(null);
                    monthly_barchart.invalidate();
                }
                else {

                    // Parse the JSON response and populate the chart
                    Steps_MODAL_monthly.clear();
                    barEntryArrayListmonthly = new ArrayList<>();
                    Labelsnamemonthly = new ArrayList<>();
                    barEntryArrayListmonthly.clear();
                    Labelsnamemonthly.clear();

                    try {
                        obj2 = new JSONArray(response);

                        for (int i = 0; i < obj2.length(); i++) {
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("month");
                            String Calorie = jsonObject.getString("totalStepssum");
                            if (!Calorie.equals("null")) {
                                Steps_MODAL_monthly.add(new Steps_Modal(date, Integer.parseInt(Calorie)));
                            }
                        }
                        for (int i = 0; i < Steps_MODAL_monthly.size(); i++) {
                            String date = Steps_MODAL_monthly.get(i).getDate();
                            int Cal = Steps_MODAL_monthly.get(i).getSteps();
                            barEntryArrayListmonthly.add(new BarEntry(i, Cal));
                            Labelsnamemonthly.add(date);
                        }

                        //====================


//                        monthly
                        BarDataSet barDataSet_monthly = new BarDataSet(barEntryArrayListmonthly, "Monthly Steps");
                        barDataSet_monthly.setColors(ColorTemplate.PASTEL_COLORS);
                        Description description = new Description();
                        description.setText("-");
                        monthly_barchart.setDescription(description);
                        BarData barData_monthly = new BarData((barDataSet_monthly));
                        monthly_barchart.setData(barData_monthly);


                        XAxis xAxis = monthly_barchart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(Labelsnamemonthly));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(Labelsnamemonthly.size());
                        xAxis.setLabelRotationAngle(0);


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
                Map<String, String> param = new HashMap<String, String>();
                param.put("p_email", (String) adapterView.getItemAtPosition(i));
                return param;
            }
        };
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        queue1.add(request1);


        // Request to fetch AI prediction and completed steps
        StringRequest request4 = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("No entry")) {

                    // Hide recommendation, completed steps, and AI prediction if no entry is found
                    recom_steps.setVisibility(View.GONE);
                    comp_steps.setVisibility(View.GONE);
                    check_Ai.setVisibility(View.GONE);
                }
                else if (response.trim().equals("no recom steps"))
                {

                    // Display "No recommended steps" message
                    recom_steps.setText("Recommendation: " + "No recommended steps yet");
                    recom_steps.setVisibility(View.VISIBLE);
                    comp_steps.setVisibility(View.GONE);
                    check_Ai.setVisibility(View.GONE);

                } else {

                    try {
                        obj2 = new JSONArray(response);
                        if (obj2.length() >= 4) {

                            // Calculate the average of the last three steps values
                            JSONObject jsonObject = obj2.getJSONObject(obj2.length() - 2);
                            String value1 = jsonObject.getString("steps");
                            JSONObject jsonObject1 = obj2.getJSONObject(obj2.length() - 3);
                            String value2 = jsonObject1.getString("steps");
                            JSONObject jsonObject2 = obj2.getJSONObject(obj2.length() - 4);

                            // Get the latest completed steps and recommendation values
                            String value3 = jsonObject2.getString("steps");
                            JSONObject jsonObject4 = obj2.getJSONObject(obj2.length() - 1);

                            String Completed_steps = jsonObject4.getString("steps");
                            String latest_date=jsonObject4.getString("date_log");
                            String recom_stepsjson = jsonObject.getString("recom_steps");

                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = format.format(currentDate);


                            int sum = (Integer.parseInt(value1) + Integer.parseInt(value2) + Integer.parseInt(value3)) / 3;

                            // Display recommendation, completed steps, and AI prediction based on conditions
                            recom_steps.setText("Recommended Steps:  " + recom_stepsjson);
                            recom_steps.setVisibility(View.VISIBLE);

                            if(dateString.equals(latest_date)){
                                comp_steps.setText("Completed Steps:  " + Completed_steps);
                                comp_steps.setVisibility(View.VISIBLE);
                                if (Integer.parseInt(recom_stepsjson) <= Integer.parseInt(Completed_steps)) {

                                    check_Ai.setText("Prediction: " + "Completed The goal");
                                    check_Ai.setVisibility(View.VISIBLE);
                                } else {
                                    if (sum >= (Integer.parseInt(recom_stepsjson) - 50)) {
//                                        Log.d("likely to complete", "steps");
                                        check_Ai.setVisibility(View.VISIBLE);
                                        check_Ai.setText("Prediction: " + "Likely to complete");
                                    } else {
//                                        Log.d("unlikely to complete", "steps");
                                        check_Ai.setVisibility(View.VISIBLE);
                                        check_Ai.setText("Prediction: " + "Unlikely to complete");
                                    }
                                }
                            }
                            else{
                                comp_steps.setText("Completed Steps:  " + "0");
                                comp_steps.setVisibility(View.VISIBLE);
                                    if (sum >= (Integer.parseInt(recom_stepsjson) - 50)) {
//                                        Log.d("likely to complete", "steps");
                                        check_Ai.setVisibility(View.VISIBLE);
                                        check_Ai.setText("Prediction: " + "Likely to complete");
                                    } else {
//                                        Log.d("unlikely to complete", "steps");
                                        check_Ai.setVisibility(View.VISIBLE);
                                        check_Ai.setText("Prediction: " + "Unlikely to complete");
                                    }
                            }
                        } else {
                            check_Ai.setVisibility(View.VISIBLE);
                            check_Ai.setText("Prediction: " + "insufficeint previous record");
                        }
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("p_email", (String) adapterView.getItemAtPosition(i));
                param.put("d_email", currentemail);

                return param;
            }
        };
        RequestQueue queue3 = Volley.newRequestQueue(getApplicationContext());
        queue3.add(request4);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}