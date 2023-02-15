package com.waqasahmad.remotecare;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class MainActivity_signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name,email,password;
    ImageView signup;
    String genderstr,user_type;
    TextView signin;

    String name2,email2,password2;

    ImageView male,female, other;
    ImageView patient,doctor;
    String user_type_id;
    CheckBox bt_show;

    FirebaseDatabase database ;
    DatabaseReference reference ;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
String user_t="";
    private Spinner spinner2;
    ArrayList<String> paths = new ArrayList();




    private static final String insert_user_url ="http://"+Ip_server.getIpServer()+"/smd_project/insert.php";
    private static final String insert_dailySteps_url ="http://"+Ip_server.getIpServer()+"/smd_project/insert_daily_steps.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);



        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user!=null){
//            startActivity(new Intent(MainActivity_signup.this, MainActivity2.class));
//        }



        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signup=findViewById(R.id.signup);
        signin=findViewById(R.id.signin);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);

        patient = findViewById(R.id.patient);
        doctor = findViewById(R.id.doctor);
        bt_show = findViewById(R.id.bt_show);


        ///////////////////////////

        paths.add("Allergists");
        paths.add("Cardiologists");
        paths.add("Gastroenterologists");
        paths.add("Dermatologists");
        paths.add("Neurologists");
        paths.add("Physiatrists");
        paths.add("Plastic Surgeons");
        paths.add("Radiologists");

        Log.d("11111111111111111111" , paths.toString());

        spinner2 = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity_signup.this,
                android.R.layout.simple_spinner_dropdown_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(MainActivity_signup.this);



        bt_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //Gender selection
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genderstr="Male";
                Toast.makeText(
                        MainActivity_signup.this,
                        "Male",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genderstr="Female";

                Toast.makeText(
                        MainActivity_signup.this,
                        "female",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                genderstr="Other";

                Toast.makeText(
                        MainActivity_signup.this,
                        "other",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        //User Type selection
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type="Patient";user_type_id = "1";

                Toast.makeText(
                        MainActivity_signup.this,
                        "Patient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_type="Doctor";
                user_type_id = "2";

                spinner2.setVisibility(View.VISIBLE);

                Toast.makeText(
                        MainActivity_signup.this,
                        "Doctor",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //converting to string for sending into Hash map
                name2 = name.getText().toString();
                email2 = email.getText().toString();
                password2 = password.getText().toString();

                mAuth.createUserWithEmailAndPassword(
                                email.getText().toString(),
                                password.getText().toString()
                        )
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(
                                        MainActivity_signup.this,
                                        "Creating the user",
                                        Toast.LENGTH_LONG
                                ).show();
                                String savecurrentdate;
                                Calendar calendar=Calendar.getInstance();
                                SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
                                savecurrentdate=currentdate.format(calendar.getTime());
                                SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
                                String savetime=currentTime.format(calendar.getTime());
                                createuserinrealtime pro=new createuserinrealtime(name.getText().toString(),email.getText().toString(),password.getText().toString(),genderstr,FirebaseAuth.getInstance().getCurrentUser().getUid(),user_type,savecurrentdate,savetime,"offline","");
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(pro);

                                reference.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                //calling function to create a new user
                                CreateUser();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(
                                        MainActivity_signup.this,
                                        "Failed to Create User",
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity_signup.this, MainActivity_signin.class));
            }
        });
    }

    private void CreateUser() {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name2);
        user.put("Email", email2);
        user.put("Password", password2);
        user.put("Gender", genderstr);
        user.put("User_Type", user_type);

        // Add a new document
        db.collection("users").document(email2).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(
                                MainActivity_signup.this,
                                "Successfully Created Account",
                                Toast.LENGTH_SHORT
                        ).show();

                        StringRequest request=new StringRequest(Request.Method.POST, insert_user_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                email.setText("");
                                password.setText("");
                                name.setText("");
                                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Nullable
                            protected Map getParams() throws AuthFailureError {
                                Map<String,String> param=new HashMap<>();
                                param.put("name",name2);
                                param.put("email",email2);
                                param.put("password",password2);
                                param.put("type",user_type_id);
                                param.put("gender",genderstr);
                                param.put("user_t",user_t);

                                Log.d("user_t" , user_t.toString());


                                return param;
                            }
                        };
                        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);




                        startActivity(new Intent(MainActivity_signup.this, MainActivity_signin.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                MainActivity_signup.this,
                                "Failed to Create Account",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        user_t= (String) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}