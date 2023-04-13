package com.waqasahmad.remotecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meals extends AppCompatActivity {

    DrawerLayout drawerLayout;
    String Query1, namefood_str, calorie_str;
    EditText input_query;
    ImageButton Enter_button;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    RecyclerView rv;
    List<MyModel> ls = new ArrayList<>();

    JSONObject obj;


    //    private static final String consumed_calories="http://"+Ip_server.getIpServer()+"/smd_project/consumed_calories.php";
    String url1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals);

        drawerLayout = findViewById(R.id.drawer_layout);
        input_query = findViewById(R.id.input_query);
        Query1 = input_query.getText().toString();
        Enter_button = findViewById(R.id.Enter_button);

        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/consumed_calories.php";

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

        // for logging out
        auth1 = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

        String currentemail = mAuth.getCurrentUser().getEmail();

        Enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query1 = input_query.getText().toString();
                // Concatenating header with the API and getting calories in JSON file format
                {
                    StringRequest myReq = new StringRequest(Request.Method.GET,
                            "https://api.calorieninjas.com/v1/nutrition?query=" + Query1,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        obj = new JSONObject(response);

                                        calorie_str = String.valueOf(obj.getJSONArray("items").getJSONObject(0).getString("calories"));

                                        ////////////////////////////////////////////

                                        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
//                                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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
                                                param.put("p_email", currentemail);
                                                param.put("calories", calorie_str);
                                                return param;
                                            }
                                        };
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        queue.add(request);

                                        //////////////////////////////////////////////

                                        rv = findViewById(R.id.rv);

                                        try {

                                            ls.add(new MyModel(String.valueOf(obj.getJSONArray("items").getJSONObject(0).getString("name")),
                                                    String.valueOf(obj.getJSONArray("items").getJSONObject(0).getString("calories"))));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        MyAdapter adapter = new MyAdapter(ls, Meals.this);
                                        RecyclerView.LayoutManager lm = new LinearLayoutManager(Meals.this);
                                        rv.setLayoutManager(lm);
                                        rv.setAdapter(adapter);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Meals.this, error.toString(), Toast.LENGTH_LONG).show();
                                    Log.d("dataa", error.toString());

                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> mHeaders = new ArrayMap<String, String>();
                            mHeaders.put("Content-Type", "application/json");
                            mHeaders.put("X-Api-Key", "syfXOOkubhTjCgJFOr6KGQ==mYTrMBkvTiw6piBu");
                            return mHeaders;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Meals.this);
                    requestQueue.add(myReq);

                }


            }
        });
    }


    public void ClickMeals(View view) {
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

    public void ClickProfile(View view) {
        MainActivity2.redirectActivity(this, Profile.class);
    }

    public void ClickOverview(View view) {
        MainActivity2.redirectActivity(this, OverView.class);
    }

    public void ClickPrescriptionDetails(View view) {
        MainActivity2.redirectActivity(this, Prescription_Details.class);
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

        startActivity(new Intent(Meals.this, MainActivity_signin.class));
    }


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }
}