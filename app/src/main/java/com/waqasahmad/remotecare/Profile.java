package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    CircleImageView profile_circle;
    Uri image = null;
private Bitmap imagetoStore;
    ImageButton update_btn;

DatabaseHandler objectdatabasehandler;


//    EditText change_name , change_password;
//    String change_name_str , change_password_str;

    // validating user id
    FirebaseAuth mAuth;

//    FirebaseUser user ;
    String currentemail ;

    FirebaseFirestore db;
    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        drawerLayout=findViewById(R.id.drawer_layout);
        profile_circle=findViewById(R.id.profile_circle);
        objectdatabasehandler=new DatabaseHandler(this);
//        change_name = findViewById(R.id.change_name);
//        change_password = findViewById(R.id.change_password);
//        update_btn = findViewById(R.id.update_btn);


//        change_name_str = change_name.getText().toString();
//        change_password_str = change_password.getText().toString();



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
                                    Profile.this,
                                    "No data to update",
                                    Toast.LENGTH_SHORT
                            ).show();

                    }
                });


//        update_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //updating profile values name,email
//                {
//
//                    Log.d("name change " , change_name_str);
//                    Log.d("password change " , change_password_str);
//
//
//
//
//                    reference = db.collection("users").document(currentemail);
//                    Map<String,Object> map1 =new HashMap<>();
//
//                    map1.put("Name" , change_name_str);
//                    map1.put("Password" , change_password_str);
//
//                    reference.set(map1, SetOptions.merge())
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                    Toast.makeText(Profile.this,
//                                                    "Updated Succesfully ",
//                                                    Toast.LENGTH_LONG)
//                                            .show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//
//                                    Toast.makeText(Profile.this,
//                                                    "Could not update ",
//                                                    Toast.LENGTH_LONG)
//                                            .show();
//                                }
//                            });
//                }
//
//            }
//        });

        profile_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 30);

            }
        });
    }

    //return to same screen by redirecting
    public void ClickProfile(View view){
        recreate();
    }

    //Open/Close drawer function
    public void ClickMenu (View view){
        MainActivity2.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MainActivity2.closeDrawer(drawerLayout);
    }

    //Go to other screens by intent
    public void ClickPro(View view){
        MainActivity2.redirectActivity(this,MainActivity2.class);
    }
    public void ClickPrescriptionDetails (View view){
        MainActivity2.redirectActivity(this, Prescription_Details.class);
    }
    public void ClickMeals(View view){
        MainActivity2.redirectActivity(this,Meals.class);
    }
    public void ClickOverview(View view){
        MainActivity2.redirectActivity(this,OverView.class);
    }



    @Override
    protected void onPause() {
        super.onPause();
        MainActivity2.closeDrawer(drawerLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30 && resultCode == RESULT_OK) {

            image = data.getData();
            try {
                imagetoStore= MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                objectdatabasehandler.storeImage(new ModelClassoffline(currentemail,imagetoStore));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                                                      Profile.this,
                                                        "Successfully uploaded image ",
                                                             Toast.LENGTH_LONG
                                                    ).show();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(
                                                            Profile.this,
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
                            Toast.makeText(Profile.this,
                                    "Failed to upload image ",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        }
    }



}