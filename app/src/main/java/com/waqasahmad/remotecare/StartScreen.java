package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartScreen extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String strdoctor = "Doctor";
    String strpatient = "Patient";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        new Handler().postDelayed(() -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null)
            {

                mAuth= FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                String useremail = mAuth.getCurrentUser().getEmail();
                Log.d("userrrr",useremail);
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
//                                        Log.d("USER_TYPE",usertype);
                                        startActivity(new Intent(StartScreen.this, MainActivity2.class)); // for patient view

                                    }
                                    else if (usertype.equals(strdoctor)){
                                        Intent intent=new Intent(StartScreen.this,Doctor1.class);
                                        startActivity(intent);
//
                                        Log.d("USER_TYPE",usertype);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


            }
            else
            {
                startActivity(new Intent(StartScreen.this, MainActivity_signin.class));
            }

        }, 1000);
    }
}
