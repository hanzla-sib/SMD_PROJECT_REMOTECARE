package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class messagemain extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    ArrayList<userchat> userslist;
    RecyclerView rv;
    UsersAdapter usersAdapter;
    ImageView logout;

    LinearLayout back_btn;
    LinearLayout btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagemain);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        rv=findViewById(R.id.recViewbottom);
        userslist=new ArrayList<>();
        logout=findViewById(R.id.logout);
        back_btn = findViewById(R.id.back_btn);
        btn1=findViewById(R.id.home_btn2);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);



        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

                            uchat.setName(name);
                            uchat.setUid(uid);
                            uchat.setOnlinestatus(status);
                            uchat.setLastseen(lastscene);
                            uchat.setP_id(p_id);
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