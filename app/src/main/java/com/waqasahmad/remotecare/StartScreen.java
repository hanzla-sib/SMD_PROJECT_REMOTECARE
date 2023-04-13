package com.waqasahmad.remotecare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

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
            if (user != null) {

                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                String useremail = mAuth.getCurrentUser().getEmail();
                Log.d("userrrr", useremail);

                db.collection("users").
                        document(useremail).get().
                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                DocumentSnapshot document = task.getResult();
                                Log.d("docccc", document.toString());

                                JSONObject obj;
                                obj = new JSONObject(document.getData());

                                try {
                                    String usertype = obj.getString("User_Type");


                                    if (usertype.equals(strpatient)) {
//                                        Log.d("USER_TYPE",usertype);
                                        startActivity(new Intent(StartScreen.this, MainActivity2.class)); // for patient view

                                    } else if (usertype.equals(strdoctor)) {
                                        Intent intent = new Intent(StartScreen.this, Doctor1.class);
                                        startActivity(intent);
//
                                        Log.d("USER_TYPE", usertype);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


            } else {
                startActivity(new Intent(StartScreen.this, MainActivity_signin.class));
            }

        }, 1000);
    }
}
