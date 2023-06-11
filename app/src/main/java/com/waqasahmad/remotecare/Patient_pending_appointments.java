package com.waqasahmad.remotecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient_pending_appointments extends AppCompatActivity {
    RecyclerView rv;
    List<Appointment_Model> ls = new ArrayList<>();
    LinearLayout back_btn;
    LinearLayout btn1, btn2, btn3, btn4;
    P_pending_appointments_adapter adapter;

    SearchView search;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String url1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_pending_appointments);

        // Initialize views
        rv = findViewById(R.id.rv_pending_appointments);
        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.home_btn2);
        btn2 = findViewById(R.id.appointment_btn);
        btn3 = findViewById(R.id.record_btn);
        btn4 = findViewById(R.id.chat_btn);
        btn2.setBackgroundResource(R.drawable.nav_btn_color);

        // Get the shared preferences for IP address
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/patient_pending_appointment.php";
        search = findViewById(R.id.search_view);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        String useremail = mAuth.getCurrentUser().getEmail();

        search.clearFocus();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fileList(newText);
                return true;
            }
        });

        // Send a request to fetch pending appointments from the server
        StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj2 = new JSONArray(response);


                    for (int i = 0; i < obj2.length(); i++) {
                        JSONObject jsonObject = obj2.getJSONObject(i);

                        String name = jsonObject.getString("d_name");
                        String email = jsonObject.getString("d_email");
                        String image = jsonObject.getString("imageurl");
                        String doc_type = jsonObject.getString("doc_type");

                        Appointment_Model doc_model = new Appointment_Model();
                        doc_model.setName_doc(name);
                        doc_model.setEmail_doc(email);
                        doc_model.setImage_doc(image);
                        doc_model.setDoc_type(doc_type);

                        ls.add(doc_model);

                    }

                    adapter = new P_pending_appointments_adapter(ls, Patient_pending_appointments.this);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Patient_pending_appointments.this);
                    rv.setLayoutManager(lm);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
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
                param.put("email", useremail);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        // Set click listeners for buttons
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_pending_appointments.this, MainActivity2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_pending_appointments.this, Patient_All_appointments.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_pending_appointments.this, Add_records.class));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Patient_pending_appointments.this, messagemain.class));
            }
        });

    }

    // Filter the appointment list based on search text
    private void fileList(String newText) {
        List<Appointment_Model> filterlist = new ArrayList<>();
        for (Appointment_Model item : ls) {
            if (item.getDoc_type().toLowerCase().contains(newText.toLowerCase())) {
                filterlist.add(item);
            }
        }
        if (filterlist.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {

            adapter.setfilterlist(filterlist);
        }
    }
}