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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patient_pending_appointments extends AppCompatActivity {

    //
    RecyclerView rv;
    List<Appointment_Model> ls =new ArrayList<>();

    //
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    private static final String patient_pending_appointment="http://"+Ip_server.getIpServer()+"/smd_project/patient_pending_appointment.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_pending_appointments);

        rv=findViewById(R.id.rv_pending_appointments);


        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);



        //////////////////////////////////////////////////////////////////////////////////////////

        StringRequest request=new StringRequest(Request.Method.POST, patient_pending_appointment, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("respons11111111" ,response );
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                try {
                    JSONArray obj2 = new JSONArray(response);


                    for(int i=0;i<obj2.length();i++){
                        JSONObject jsonObject = obj2.getJSONObject(i);

                        String name = jsonObject.getString("d_name");
                        String email = jsonObject.getString("d_email");

                        Appointment_Model doc_model = new Appointment_Model();
                        doc_model.setName_doc(name);
                        doc_model.setEmail_doc(email);
                        ls.add(doc_model);

                    }

                    P_pending_appointments_adapter adapter = new P_pending_appointments_adapter(ls, Patient_pending_appointments.this);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Patient_pending_appointments.this);
                    rv.setLayoutManager(lm);
                    rv.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("email",useremail);
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        //*********************************


    }
}