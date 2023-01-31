package com.waqasahmad.remotecare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_records extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<Record_Model> arrayList;
    Record_Adapter adapter;
    String email1;
    FirebaseAuth mAuth;
    FirebaseDatabase database ;
    DatabaseReference reference ;
    FirebaseFirestore db;

    LinearLayout back_btn;
    LinearLayout btn1,btn2,btn3,btn4;


    private static final String retreive_img="http://"+Ip_server.getIpServer()+"/smd_project/retireveimage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records);

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();
        arrayList= new ArrayList<>();
        rv = findViewById(R.id.rv);
        back_btn = findViewById(R.id.back_btn);
        btn1=findViewById(R.id.home_btn2);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email1 = user.getEmail();



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_records.this, MainActivity2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_records.this, Patient_All_appointments.class));
            }
        });
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Add_records.this, Add_records.class));
//            }
//        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_records.this, messagemain.class));
            }
        });



        Record_Model record = new Record_Model();
        StringRequest request=new StringRequest(Request.Method.POST, retreive_img, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                Log.d("msgggggg", response.toString());
                try {
                    int check=0;

                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {

                        Record_Model record = new Record_Model();
                        JSONObject object=array.getJSONObject(i);
                        String imageurl = object.getString("imageurl");
                        String details = object.getString("details");
                        Log.d("iamgeurllll",imageurl);

                        record.setDetails(details);
                        record.setImage_url(imageurl);

                        arrayList.add(record);

//                        Uri myUri = Uri.parse(imageurl);
//                         dp.setImageURI(myUri);
//                        Picasso.get().load("http://"+IP_server.getIpServer()+"/Assignment3/"+imageurl).into(dp);
                    }
                    Log.d("adapterrrrr", Integer.toString(arrayList.size()));

                    adapter = new Record_Adapter(Add_records.this,arrayList);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Add_records.this);
                    rv.setLayoutManager(lm);
                    rv.setAdapter(adapter);
                }
                catch (Exception e)
                {

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
                param.put("email",email1);
                Log.d("emailll",email1);

                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
}