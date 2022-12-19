package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Doc_Profile extends AppCompatActivity {

//
    DrawerLayout doc_drawerLayout;
    ImageView Menu,logo;

    CircleImageView profile_circle;
    Uri image = null;

    // validating user id
    FirebaseAuth mAuth;

    //    FirebaseUser user ;
    String currentemail ;

    FirebaseFirestore db;
    DocumentReference reference;


    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_profile);
        doc_drawerLayout = findViewById(R.id.doc_profile);
        Menu = findViewById(R.id.menu);
        logo=findViewById(R.id.rclogo);
        profile_circle=findViewById(R.id.doc_profile_circle);

        // for logging out
        auth1=FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");



        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //getting email of the logged in user for document
        currentemail = mAuth.getCurrentUser().getEmail();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();


        reference = db.collection("users").document(currentemail);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists())
                        {
                            String Dplink = task.getResult().getString("Dp");
                            Picasso.get().load(Dplink).into(profile_circle);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                Doc_Profile.this,
                                "No data to update",
                                Toast.LENGTH_SHORT
                        ).show();

                    }
                });


        profile_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 40);

            }
        });


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


    public void ClickProfile (View view){
        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //next 3 functions are different for the doctor/patient
    public void ClickPro (View view) {

        Intent intent = new Intent(this, Doctor1.class);
        startActivity(intent);
    }
    public void ClickAppointmentsDoc (View view){

        Intent intent = new Intent(this, Doc_Appointments_pending.class);
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

        startActivity(new Intent(Doc_Profile.this, MainActivity_signin.class));

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40 && resultCode == RESULT_OK) {

            image = data.getData();
            Calendar c = Calendar.getInstance();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference ref = storage.getReference().child("dp/" +c.getTimeInMillis()+ ".jpg");
            ref.putFile(image)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.get().load(uri.toString()).into(profile_circle);

                                    reference = db.collection("users").document(currentemail);

                                    Map<String,Object> map =new HashMap<>();
                                    map.put("Dp" , uri.toString());

                                    reference.set(map , SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(
                                                            Doc_Profile.this,
                                                            "Successfully uploaded image ",
                                                            Toast.LENGTH_LONG
                                                    ).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(
                                                            Doc_Profile.this,
                                                            "Could not upload image ",
                                                            Toast.LENGTH_LONG
                                                    ).show();

                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Doc_Profile.this,
                                            "Failed to upload image ",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        }
    }

}