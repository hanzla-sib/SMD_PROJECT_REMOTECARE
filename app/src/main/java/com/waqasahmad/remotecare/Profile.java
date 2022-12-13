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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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


    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;
    Bitmap bitmap;

    DatabaseHandler objectdatabasehandler;

    private static final String saveimageuser="http://"+Ip_server.getIpServer()+"/smd_project/imageupload.php";
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


        // for logging out
        auth1=FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

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
    public void ClickChat(View view){
        MainActivity2.redirectActivity(this,messagemain.class);
    }
    public void ClickLogout(View view){

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

        startActivity(new Intent(Profile.this, MainActivity_signin.class));
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
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                imagetoStore= MediaStore.Images.Media.getBitmap(getContentResolver(),image);
                objectdatabasehandler.storeImage(new ModelClassoffline(currentemail,imagetoStore));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream=new ByteArrayOutputStream();
            if(bitmap != null){
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();
                final String base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                String strDate =mdformat.format(calendar.getTime());




                StringRequest request=new StringRequest(Request.Method.POST, saveimageuser, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

//
                        Toast.makeText(getApplicationContext(),"uploaded sucesfully",Toast.LENGTH_LONG).show();


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

                        param.put("image",base64Image);

                        param.put("email",currentemail);

                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);
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



}}