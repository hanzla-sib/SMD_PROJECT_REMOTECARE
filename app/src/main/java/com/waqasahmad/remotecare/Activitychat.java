package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activitychat extends AppCompatActivity {
    FirebaseAuth mauth;
    FirebaseDatabase database;
    DatabaseReference mref ;
    LinearLayout back_btn;
    FirebaseAuth mAuth;
    CircleImageView img;
    //    private static final String notify="http://"+Ip_server.getIpServer()+"/smd_project/notify.php";
    String url="",url1="";
    TextView username,userMainChatActivityProfileName;
    ImageButton send1;
    EditText editText1;
String useremail1="";
    String rname , ruid , suid,P_id;
    String send_rcv,rcv_send;
    String rec_email="";
    RecyclerView recyclerView;

    ArrayList<Messages> messagesArrayList;

    MessagesAdapter messagesAdapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitychat);
        back_btn = findViewById(R.id.back_btn);
        searchView = findViewById(R.id.searchbar);
        searchView.clearFocus();
        ruid = getIntent().getStringExtra("uid");
        P_id=getIntent().getStringExtra("p_id");
        mAuth=FirebaseAuth.getInstance();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url ="http://"+s1+"/smd_project/notify.php";
        url1 ="http://"+s1+"/smd_project/get_image_for_chat_insidescreen.php";


        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userNodeRef = usersRef.child(ruid);

        DatabaseReference emailNodeRef = userNodeRef.child("email");
        emailNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);

                StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("picturee",response.toString());
                        String pic=response.toString();

                        Picasso.get().load("http://"+s1+"/smd_project/"+pic).into(img);

                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
//                                Toast.makeText(c_doc2,error.toString(),Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();

                        param.put("d_email",email);


                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        messagesArrayList = new ArrayList<>();

        send1 = findViewById(R.id.send1);
        editText1 = findViewById(R.id.edit1);
        userMainChatActivityProfileName = findViewById(R.id.userMainChatActivityProfileName);
        recyclerView = findViewById(R.id.insidechatrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        messagesAdapter = new MessagesAdapter(Activitychat.this, messagesArrayList);

        recyclerView.setAdapter(messagesAdapter);
        rname = getIntent().getStringExtra("name");
        userMainChatActivityProfileName.setText(rname);

        mauth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



//        rec_email=getIntent().getStringExtra("rec_email");
        String currentemail = mAuth.getCurrentUser().getEmail();
        useremail1=currentemail;
        suid = mauth.getUid();

        send_rcv = suid + ruid;
        rcv_send = ruid + suid;

        mref = database.getReference("Users").child(mauth.getUid());
        DatabaseReference mref2 = database.getReference("Chats").child(send_rcv).child("messages");
        img=findViewById(R.id.imguser_msgtop);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText1.getText().toString();

                if (message.isEmpty()) {
                    Toast.makeText(Activitychat.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    editText1.setText("");
                    //notification
                    //============================================
                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("responseeeeee ",response);
//                            if(response.equals("success")){



//

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();

                        param.put("tokeni",P_id);
                        param.put("msg",message);
                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
//================================================================

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                    String strDate = mdformat.format(calendar.getTime());

                    Messages messages = new Messages(message, mauth.getUid(), strDate);

                    database = FirebaseDatabase.getInstance();
                    database.getReference().child("Chats").child(send_rcv).child("messages").push().setValue(messages)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    database.getReference().child("Chats").child(rcv_send).child("messages").push()
                                            .setValue(messages)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });


                                }
                            });

                }

            }
        });

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String msg1 = dataSnapshot.child("msg").getValue().toString();
                    String suid = dataSnapshot.child("suid").getValue().toString();
                    String timestamp = dataSnapshot.child("timestamp").getValue().toString();


                    Messages messages = new Messages();
                    messages.setMsg(msg1);
                    messages.setSuid(suid);
                    messages.setTimestamp(timestamp);

                    messagesArrayList.add(messages);


//                    Log.d("message999" , messages.getMsg());
//                    Log.d("suid9999" , messages.getSuid());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void fileList(String newText) {
        ArrayList<Messages> filterlist=new ArrayList<>();
        for(Messages item: messagesArrayList){
            if(item.getMsg().toLowerCase().contains(newText.toLowerCase())){
                filterlist.add(item);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(this, "no data found", Toast.LENGTH_SHORT).show();
        }
        else{
            messagesAdapter.setfilterlist(filterlist);
        }
    }
    }