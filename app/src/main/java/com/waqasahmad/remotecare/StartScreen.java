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

        // Delayed execution using Handler
        new Handler().postDelayed(() -> {

            // Check if the user is already logged in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                mAuth = FirebaseAuth.getInstance();
                db = FirebaseFirestore.getInstance();
                String useremail = mAuth.getCurrentUser().getEmail();
                Log.d("userrrr", useremail);

                // Retrieve user document from Firestore
                db.collection("users").
                        document(useremail).get().
                        addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                // Get the document snapshot
                                DocumentSnapshot document = task.getResult();

                                // Convert document data to JSON object
                                JSONObject obj;
                                obj = new JSONObject(document.getData());

                                try {
                                    // Get the user type from the JSON object
                                    String usertype = obj.getString("User_Type");

                                    // Check the user type and start the appropriate activity
                                    if (usertype.equals(strpatient)) {

                                        startActivity(new Intent(StartScreen.this, MainActivity2.class)); // for patient view

                                    } else if (usertype.equals(strdoctor)) {

                                        Intent intent = new Intent(StartScreen.this, Doctor1.class);
                                        startActivity(intent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            } else {

                // User not logged in, start the sign-in activity
                startActivity(new Intent(StartScreen.this, MainActivity_signin.class));
            }

        }, 1000);
    }
}
