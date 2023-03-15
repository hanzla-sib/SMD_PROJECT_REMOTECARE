package com.waqasahmad.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HeartRate_progress_doc_side extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    JSONArray obj2;
    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;
    BarChart weekly_barchart,monthly_barchart;

    LinearLayout back_btn;
    LinearLayout btn1,btn2,btn3,btn4;

    String currentemail="";

    ArrayList<HeartBeat_modal> CAL_MODAL_weekly=new ArrayList<>();
    ArrayList<HeartBeat_modal> CAL_MODAL_monthly=new ArrayList<>();
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> Labelsname;
    ArrayList<BarEntry> barEntryArrayListmonthly;
    ArrayList<String> Labelsnamemonthly;
    //    private static final String calorie_graph="http://"+Ip_server.getIpServer()+"/smd_project/calorie_graph.php";
//    private static final String calorie_graph_month="http://"+Ip_server.getIpServer()+"/smd_project/monthlycaloriesgraph.php";
//
//    private static final String fetch_patient_withdocs="http://"+Ip_server.getIpServer()+"/smd_project/fetch_patient_reg_doctors.php";
    String url1="",url2="",url3="";
    ArrayList<String> paths = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_progress_doc_side);
        weekly_barchart = findViewById(R.id.graph2);
        monthly_barchart=findViewById(R.id.graph3);
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();
        String useremail = mAuth.getCurrentUser().getEmail();

        back_btn = findViewById(R.id.back_btn);
        btn1=findViewById(R.id.home_btn2);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/HR_Graph.php";
        url2="http://"+s1+"/smd_project/HR_Graph_monthly.php";
        url3="http://"+s1+"/smd_project/fetch_patient_reg_doctors.php";
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HeartRate_progress_doc_side.this, Doctor1.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HeartRate_progress_doc_side.this, Doc_All_Appointments.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HeartRate_progress_doc_side.this, Doc_Profile.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HeartRate_progress_doc_side.this, messagemain.class));
            }
        });

        //////////////////////////////////////////////////////////////
        StringRequest request=new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("No entry")){

                }
                else{
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                    try {
                        obj2 = new JSONArray(response);

                        for(int i=0;i<obj2.length();i++) {
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String mail = jsonObject.getString("p_email");
                            paths.add(mail);
                        }

                        spinner = (Spinner)findViewById(R.id.spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HeartRate_progress_doc_side.this,
                                android.R.layout.simple_spinner_item,paths);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(HeartRate_progress_doc_side.this);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("email",useremail);
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                Log.d("response111111111111111" , response);
                if(response.toString().equals("No entry"))
                {

                    Log.d("response333333333" , "Noooooooooooooooooooooooooooooooooooooo");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                    CAL_MODAL_weekly.clear();
                    barEntryArrayList=new ArrayList<>();
                    Labelsname=new ArrayList<>();
                    barEntryArrayList.clear();
                    Labelsname.clear();





                    try {
                        obj2 = new JSONArray(response);
                        int totalsize=obj2.length();
                        int starting=0;
                        if(totalsize>=7){
                            starting=obj2.length()-7;
                        }
                        for(int i=starting;i<obj2.length();i++){
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("date");
                            String halfdate="";
                            for(int j=5;j<date.length();j++){
                                halfdate+=date.charAt(j);
                            }
                            String Calorie = jsonObject.getString("HR");
                            CAL_MODAL_weekly.add(new HeartBeat_modal(halfdate,Float.parseFloat(Calorie)));
//                            CAL_MODAL.add(new CaloriesModal(date,Integer.parseInt(Calorie)));
                        }

                        for(int i=0;i<CAL_MODAL_weekly.size();i++){
                            String date=CAL_MODAL_weekly.get(i).getDate();
                            float Cal=CAL_MODAL_weekly.get(i).getHR();
                            barEntryArrayList.add(new BarEntry(i,Cal));
                            Labelsname.add(date);
                        }
                        BarDataSet barDataSetweekly=new BarDataSet(barEntryArrayList,"Weekly HeartBeat");
                        barDataSetweekly.setColors(ColorTemplate.COLORFUL_COLORS);
                        Description description_weekly= new Description();
                        description_weekly.setText("-");
                        weekly_barchart.setDescription(description_weekly);
                        BarData barData_weekly=new BarData((barDataSetweekly));
                        weekly_barchart.setData(barData_weekly);
                        XAxis xAxis_weekly=weekly_barchart.getXAxis();
                        xAxis_weekly.setValueFormatter(new IndexAxisValueFormatter(Labelsname));
                        xAxis_weekly.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis_weekly.setDrawGridLines(false);
                        xAxis_weekly.setDrawAxisLine(false);
                        xAxis_weekly.setGranularity(1f);
                        xAxis_weekly.setLabelCount(Labelsname.size());
                        xAxis_weekly.setLabelRotationAngle(0);
                        weekly_barchart.animateY(2000);
                        weekly_barchart.invalidate();



//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param=new HashMap<String,String>();
                param.put("p_email",(String) adapterView.getItemAtPosition(i));
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);



        //===================MONTHLY
        StringRequest request1=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                Log.d("response111111111111111" , response);
                if(response.toString().equals("No entry"))
                {

                    Log.d("response333333333" , "Noooooooooooooooooooooooooooooooooooooo");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();


                    CAL_MODAL_monthly.clear();
                    barEntryArrayListmonthly=new ArrayList<>();
                    Labelsnamemonthly=new ArrayList<>();
                    barEntryArrayListmonthly.clear();
                    Labelsnamemonthly.clear();




                    try {
                        obj2 = new JSONArray(response);

                        for(int i=0;i<obj2.length();i++){
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("month");
                            String Calorie = jsonObject.getString("totalStepssum");
                            CAL_MODAL_monthly.add(new HeartBeat_modal(date,Float.parseFloat(Calorie)));
                        }
                        for(int i=0;i<CAL_MODAL_monthly.size();i++){
                            String date=CAL_MODAL_monthly.get(i).getDate();
                            float Cal=CAL_MODAL_monthly.get(i).getHR();
                            barEntryArrayListmonthly.add(new BarEntry(i,Cal));
                            Labelsnamemonthly.add(date);
                        }

                        //====================


//                        monthly
                        BarDataSet barDataSet_monthly=new BarDataSet(barEntryArrayListmonthly,"Monthly Heart Beat");
                        barDataSet_monthly.setColors(ColorTemplate.COLORFUL_COLORS);
                        Description description= new Description();
                        description.setText("-");
                        monthly_barchart.setDescription(description);
                        BarData barData_monthly=new BarData((barDataSet_monthly));
                        monthly_barchart.setData(barData_monthly);
                        XAxis xAxis=monthly_barchart.getXAxis();
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
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> param=new HashMap<String,String>();
                param.put("p_email",(String) adapterView.getItemAtPosition(i));
                return param;
            }
        };
        RequestQueue queue1= Volley.newRequestQueue(getApplicationContext());
        queue1.add(request1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}