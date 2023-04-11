package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class messagemain extends AppCompatActivity {
LinearLayout doc_nav;
LinearLayout pat_nav;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    ArrayList<userchat> userslist;
    RecyclerView rv;
    UsersAdapter usersAdapter;
    ImageView logout;
    FirebaseAuth auth1;
    LinearLayout back_btn;
    String useremail1="" ;
    LinearLayout btn1,btn2,btn3,btn4;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String strdoctor = "Doctor";
    String strpatient = "Patient";

    TextView btn3txt;

    //for logging out
    DatabaseReference reference1;

    FirebaseDatabase database1;


//    private static final String user_token_delete="http://"+Ip_server.getIpServer()+"/smd_project/user_token_delete.php";
String url1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagemain);
        mAuth= FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        auth1=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        rv=findViewById(R.id.recViewbottom);
        userslist=new ArrayList<>();
        logout=findViewById(R.id.logout);
        back_btn = findViewById(R.id.back_btn);
        useremail1 = auth1.getCurrentUser().getEmail();
        pat_nav=findViewById(R.id.patient_nav);
        doc_nav=findViewById(R.id.doc_nav);


        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);

        db.collection("users").
                document(useremail).get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        DocumentSnapshot document = task.getResult();


                        JSONObject obj;
                        obj = new JSONObject(document.getData());

                        try {
                            String usertype = obj.getString("User_Type");

                            if(usertype.equals(strpatient)){
                                doc_nav.setVisibility(View.GONE);
                                pat_nav.setVisibility(View.VISIBLE);

                                btn1=findViewById(R.id.home_btn2);
                                btn2=findViewById(R.id.appointment_btn);
                                btn3=findViewById(R.id.record_btn);
                                btn4=findViewById(R.id.chat_btn);
                                btn4.setBackgroundResource(R.drawable.nav_btn_color);

                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, MainActivity2.class));
                                    }
                                });

                                btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, Patient_All_appointments.class));
                                    }
                                });

                                btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, Add_records.class));
                                    }
                                });
                            }

                            else if (usertype.equals(strdoctor))
                            {
                                pat_nav.setVisibility(View.GONE);
                                doc_nav.setVisibility(View.VISIBLE);
                                btn1=findViewById(R.id.doc_home_btn2);
                                btn2=findViewById(R.id.doc_appointment_btn);
                                btn3=findViewById(R.id.profile_doc_button);
                                btn4=findViewById(R.id.doc_chat_btn);
                                btn4.setBackgroundResource(R.drawable.nav_btn_color);



                                btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, Doctor1.class ));
                                    }
                                });

                                btn2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, Doc_All_Appointments.class));
                                    }
                                });

                                btn3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(messagemain.this, Doc_Profile.class));
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/user_token_delete.php";


        back_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savecurrentdate;
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
                savecurrentdate=currentdate.format(calendar.getTime());
                SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
                String savetime=currentTime.format(calendar.getTime());
                HashMap<String,Object> onlinestatus=new HashMap<>();
                onlinestatus.put("time",savetime);
                onlinestatus.put("date",savecurrentdate);
                onlinestatus.put("status","offline");
                onlinestatus.put("player_id","");
                String curruserid=auth.getUid();
                reference.child(curruserid).updateChildren(onlinestatus);

                StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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
                        param.put("email",useremail1);
                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

                auth.signOut();

                startActivity(new Intent(messagemain.this, MainActivity_signin.class));

                finish();

            }
        });


        final String[] Typeuser = {""};
        reference.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    String checkuid,mauthuid,types;
                    checkuid=dataSnapshot.child("uid").getValue().toString();
                    types=dataSnapshot.child("type").getValue().toString();
                    mauthuid=auth.getUid();
                    if(checkuid.equals(mauthuid)){
                        Typeuser[0] =dataSnapshot.child("type").getValue().toString();
                    }
                }
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String checkuid,mauthuid,Type;
                    checkuid=dataSnapshot.child("uid").getValue().toString();
                    Type=dataSnapshot.child("type").getValue().toString();
                    mauthuid=auth.getUid();
//                    Log.d("typesofuser",Typeuser[0]);
                    Log.d("typesofuser",Type);
                    if(checkuid.equals(mauthuid)){

                    }
                    else{
                        if(Type.equals(Typeuser[0])){

                        }
                        else{
                            userchat uchat=new userchat();
                            String name=dataSnapshot.child("name").getValue().toString();
                            String uid=dataSnapshot.child("uid").getValue().toString();
                            String status=dataSnapshot.child("status").getValue().toString();
                            String lastscene=dataSnapshot.child("time").getValue().toString();
                            String p_id=dataSnapshot.child("player_id").getValue().toString();
                            String email=dataSnapshot.child("email").getValue().toString();

                            uchat.setName(name);
                            uchat.setUid(uid);
                            uchat.setOnlinestatus(status);
                            uchat.setLastseen(lastscene);
                            uchat.setP_id(p_id);
                            uchat.setEmailadd(email);
                            userslist.add(uchat);
                        }

                    }
                }



                usersAdapter=new UsersAdapter(userslist,messagemain.this);
                RecyclerView.LayoutManager lm=new LinearLayoutManager(messagemain.this);
                rv.setLayoutManager(lm);
                rv.setAdapter(usersAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

}