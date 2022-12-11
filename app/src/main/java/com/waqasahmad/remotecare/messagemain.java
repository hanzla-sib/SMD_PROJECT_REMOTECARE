package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<userchat> userslist;
    RecyclerView rv;
    UsersAdapter usersAdapter;
    ImageView logout;
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


            }
        });




        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String checkuid,mauthuid;
                    checkuid=dataSnapshot.child("uid").getValue().toString();
                    mauthuid=auth.getUid();
                    if(checkuid.equals(mauthuid)){

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