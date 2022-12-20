package com.waqasahmad.remotecare;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.Toast;

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

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Doc_appointments_accepted extends AppCompatActivity {

    DrawerLayout doc_drawerLayout;
    ImageView Menu,logo;

    //RV
    RecyclerView rv;
    List<Doc_Appointment_Model> ls2 =new ArrayList<>();

    //Fire store
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    //
    List<String> list = new ArrayList<>();

    //
    private static final String doctor_appointment_accepted="http://"+Ip_server.getIpServer()+"/smd_project/doctor_appointment_accepted.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_appointments_accepted);


        doc_drawerLayout = findViewById(R.id.doc_appointments_accepted);
        Menu = findViewById(R.id.menu);
        logo=findViewById(R.id.rclogo);

        //RV
        rv=findViewById(R.id.doc_rv_appointments_accepted);

        //
        db = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        // for logging out
        auth1=FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

        String useremail = mAuth.getCurrentUser().getEmail();
        Log.d("useremail" , useremail);


        //////////////////////////////////////////////////////////////////////////////////////////

        StringRequest request=new StringRequest(Request.Method.POST, doctor_appointment_accepted, new Response.Listener<String>()
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

                        Doc_Appointment_Model doc_model = new Doc_Appointment_Model();
                        doc_model.setName_patient(name);
                        doc_model.setEmail_patient(email);
                        ls2.add(doc_model);

                    }

                    Doc_Appointments_Accepted_Adapter adapter = new Doc_Appointments_Accepted_Adapter(ls2 , Doc_appointments_accepted.this);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(Doc_appointments_accepted.this);
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


        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doc_drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

                    doc_drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

    }


    public void ClickAppointmentsDoc (View view){

        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //next 3 functions are different for the doctor/patient
    public void ClickPro (View view) {

        Intent intent = new Intent(this, Doctor1.class);
        startActivity(intent);
    }
    public void ClickProfile (View view){

        Intent intent = new Intent(this, Doc_Profile.class);
        startActivity(intent);
    }
    public void ClickPrescriptionDetailsDoc (View view){

        Intent intent = new Intent(this, Doc_Prescription.class);
        startActivity(intent);
    }

    public void ClickChatDoc (View view){

        Intent intent = new Intent(this, messagemain.class);
        startActivity(intent);
    }
    public void ClickLogoutDoc (View view){

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
        String curruserid=auth1.getUid();
        reference1.child(curruserid).updateChildren(onlinestatus);
        auth1.signOut();

        finish();


        startActivity(new Intent(Doc_appointments_accepted.this, MainActivity_signin.class));




    }

}


