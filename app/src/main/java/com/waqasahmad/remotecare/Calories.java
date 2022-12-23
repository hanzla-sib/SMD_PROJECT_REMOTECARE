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

    GraphView graph2;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String currentemail="";
    JSONArray obj2;


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
        graph2 = findViewById(R.id.graph2);
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


                    try {
                        obj2 = new JSONArray(response);
//                        DataPoint[] dp = new DataPoint[obj2.length()];
//                        for(int i=1;i<obj2.length();i++){
//                            JSONObject jsonObject = obj2.getJSONObject(i);
//                            String date = jsonObject.getString("date");
//                            String Calorie = jsonObject.getString("calorie");
//
//                            dp[i] = new DataPoint(10, 10);
//                        }
                        DataPoint[] dp = new DataPoint[10];
                        for(int i=1;i<=10;i++){
                            dp[i] = new DataPoint(1, 2);
                        }

                        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dp);
                        graph2.addSeries(series);
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