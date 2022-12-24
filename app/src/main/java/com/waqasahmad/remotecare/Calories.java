package com.waqasahmad.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calories extends AppCompatActivity {

    BarChart weekly_barchart,monthly_barchart;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    JSONArray obj2;
    ArrayList<CaloriesModal> CAL_MODAL=new ArrayList<>();
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> Labelsname;

    private static final String calorie_graph="http://"+Ip_server.getIpServer()+"/smd_project/calorie_graph.php";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories);

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();
        weekly_barchart = findViewById(R.id.graph2);
        monthly_barchart=findViewById(R.id.graph3);
        //
        currentemail = mAuth.getCurrentUser().getEmail();

        StringRequest request=new StringRequest(Request.Method.POST, calorie_graph, new Response.Listener<String>()
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

                    CAL_MODAL.clear();
                    barEntryArrayList=new ArrayList<>();
                    Labelsname=new ArrayList<>();
                    barEntryArrayList.clear();
                    Labelsname.clear();




                    try {
                        obj2 = new JSONArray(response);

                        for(int i=1;i<obj2.length();i++){
                            JSONObject jsonObject = obj2.getJSONObject(i);
                            String date = jsonObject.getString("date");
                            String halfdate="";
                            for(int j=5;j<date.length();j++){
                               halfdate+=date.charAt(j);
                            }
                            String Calorie = jsonObject.getString("calorie");
                            CAL_MODAL.add(new CaloriesModal(halfdate,Integer.parseInt(Calorie)));
//                            CAL_MODAL.add(new CaloriesModal(date,Integer.parseInt(Calorie)));
                        }

                        for(int i=0;i<CAL_MODAL.size();i++){
                            String month=CAL_MODAL.get(i).getDate();
                            int Cal=CAL_MODAL.get(i).getCalories();
                            barEntryArrayList.add(new BarEntry(i,Cal));
                            Labelsname.add(month);
                        }
                        BarDataSet barDataSetweekly=new BarDataSet(barEntryArrayList,"Weekly CALORIES");
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

                        //====================
//                        monthly
                        BarDataSet barDataSet_monthly=new BarDataSet(barEntryArrayList,"Monthly CALORIES");
                        barDataSet_monthly.setColors(ColorTemplate.COLORFUL_COLORS);
                        Description description= new Description();
                        description.setText("-");
                        monthly_barchart.setDescription(description);
                        BarData barData_monthly=new BarData((barDataSet_monthly));
                        monthly_barchart.setData(barData_monthly);
                        XAxis xAxis=monthly_barchart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(Labelsname));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setDrawAxisLine(false);
                        xAxis.setGranularity(1f);
                        xAxis.setLabelCount(Labelsname.size());
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
                param.put("p_email",currentemail);
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);



    }
//
}