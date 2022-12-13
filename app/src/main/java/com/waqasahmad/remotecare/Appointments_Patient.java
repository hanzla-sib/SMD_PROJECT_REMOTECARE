package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appointments_Patient extends AppCompatActivity {

    RecyclerView rv;
    List<Appointment_Model> ls =new ArrayList<>();
    ImageView req_app;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String strdoctor = "Doctor";

    private static final String readuser="http://"+Ip_server.getIpServer()+"/smd_project/userlist.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments_patient);

        rv=findViewById(R.id.rv_appointments);
        req_app = findViewById(R.id.request_appointment);

        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);



        //////////////////////////////////////////////////////////////////////////////////////////

        StringRequest request=new StringRequest(Request.Method.POST, readuser, new Response.Listener<String>()
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

                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");

                        Appointment_Model doc_model = new Appointment_Model();
                        doc_model.setName_doc(name);
                        doc_model.setEmail_doc(email);
                        ls.add(doc_model);

                    }

                    Appointment_Adapter adapter = new Appointment_Adapter(ls, Appointments_Patient.this);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Appointments_Patient.this);
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















        //*********************************
















        ///////////////////////////////////////////////////////////////////////////////////////////////

















//        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful())
//                {
//                    List<String> list = new ArrayList<>();
//                    for (QueryDocumentSnapshot document : task.getResult())
//                    {
//                        list.add(document.getId());
////                        Log.d("count777", "1");
//                    }
////                    Log.d("TAG66666666", list.toString());
//
//                    for(int i=0; i<list.size(); i++)
//                    {
//                        String currentX = list.get(i);
////                        Log.d("count000000", currentX);
//
//                        db.collection("users").
//                                document(currentX).get().
//                                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                                        DocumentSnapshot document = task.getResult();
//
//                                        JSONObject obj;
//                                        obj = new JSONObject(document.getData());
//
//                                        try {
//                                            String usertype = obj.getString("User_Type");
//                                            if(usertype.equals(strdoctor))
//                                            {
//
//                                                Appointment_Model doc_model = new Appointment_Model();
//                                                String name = obj.getString("Name");
//                                                String email = obj.getString("Email");
//                                                doc_model.setName_doc(name);
//                                                doc_model.setEmail_doc(email);
//                                                ls.add(doc_model);
//
//                                            }
//                                            Appointment_Adapter adapter = new Appointment_Adapter(ls, Appointments_Patient.this);
//                                            RecyclerView.LayoutManager lm = new LinearLayoutManager(Appointments_Patient.this);
//                                            rv.setLayoutManager(lm);
//                                            rv.setAdapter(adapter);
//
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                });
//                    }
//                }
//                else
//                {
//                    Log.d("TAG88888888", "Error getting documents: ", task.getException());
//                }
//
//            }
//            });

    }
}