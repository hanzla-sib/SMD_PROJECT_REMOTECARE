package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class Doc_Profile extends AppCompatActivity {

    //
    DrawerLayout doc_drawerLayout;
    ImageView Menu, logo;

    CircleImageView profile_circle;
    Uri image = null;

    // validating user id
    FirebaseAuth mAuth;

    //    FirebaseUser user ;
    String currentemail;

    FirebaseFirestore db;
    DocumentReference reference;

    //
    EditText current_password, new_password;

    //
    TextView Name, Email, Gender, U_Type;
    ImageView update_btn;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    //
    String current_password_str, new_password_str;

    //
    private FirebaseUser user;

    LinearLayout btn1,btn2,btn4;


    Bitmap bitmap;
    DatabaseHandler objectdatabasehandler;
    private Bitmap imagetoStore;


//    private static final String saveimagedoc = "http://" + Ip_server.getIpServer() + "/smd_project/imageupload.php";
//    private static final String update_password_doc = "http://" + Ip_server.getIpServer() + "/smd_project/update_password.php";

    String url1="",url2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_profile);
        doc_drawerLayout = findViewById(R.id.doc_profile);
        Menu = findViewById(R.id.menu);
        logo = findViewById(R.id.rclogo);
        profile_circle = findViewById(R.id.doc_profile_circle);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/imageupload.php";
        url2 ="http://"+s1+"/smd_project/update_password.php";

        // for logging out
        auth1 = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");


        //
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Gender = findViewById(R.id.Gender);
        U_Type = findViewById(R.id.U_Type);

        //
        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        update_btn = findViewById(R.id.update_btn);


        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //getting email of the logged in user for document
        currentemail = mAuth.getCurrentUser().getEmail();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();


        btn1=findViewById(R.id.doc_home_btn2);
        btn2=findViewById(R.id.doc_appointment_btn);
        btn4=findViewById(R.id.chat_btn);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_Profile.this, Doctor1.class));
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_Profile.this, Doc_All_Appointments.class));
            }
        });



        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Doc_Profile.this, messagemain.class));
            }
        });

        reference = db.collection("users").document(currentemail);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()) {
                            Name.setText("Name             " + task.getResult().getString("Name"));
                            Email.setText("Email              " + task.getResult().getString("Email"));
                            Gender.setText("Gender           " + task.getResult().getString("Gender"));
                            U_Type.setText("User Type      " + task.getResult().getString("User_Type"));

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


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current_password_str = current_password.getText().toString();
                new_password_str = new_password.getText().toString();

                if (!(current_password_str.equals("") && new_password_str.equals(""))) {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, current_password_str);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {


                                user.updatePassword(new_password_str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), " Password not Updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                                            StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            error.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            }) {
                                                @Nullable
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> param = new HashMap<String, String>();
                                                    param.put("password", new_password_str);
                                                    param.put("email", email);
                                                    return param;
                                                }
                                            };
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            queue.add(request);
                                        }

                                        /////////////////////
                                        Map<String, Object> new_password_obj = new HashMap<>();
                                        new_password_obj.put("Password", new_password_str);

                                        // Add a new document
                                        // reference.set(map , SetOptions.merge())
                                        db.collection("users").document(email).set(new_password_obj, SetOptions.merge())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(
                                                                Doc_Profile.this,
                                                                "updated in firebase",
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                    }
                                                });

                                        ////////////////////////////////////////

                                    }
                                });
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        " Authentication Failed for password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {

                    Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();

                }
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


    public void ClickProfile(View view) {
        if (doc_drawerLayout.isDrawerOpen(GravityCompat.START)) {

            doc_drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    //next 3 functions are different for the doctor/patient
    public void ClickPro(View view) {

        Intent intent = new Intent(this, Doctor1.class);
        startActivity(intent);
    }

    public void ClickAppointmentsDoc(View view) {

        Intent intent = new Intent(this, Doc_Appointments_pending.class);
        startActivity(intent);
    }

    public void ClickPrescriptionDetailsDoc(View view) {

        Intent intent = new Intent(this, Doc_Prescription.class);
        startActivity(intent);
    }

    public void ClickChatDoc(View view) {

        Intent intent = new Intent(this, messagemain.class);
        startActivity(intent);
    }


    public void ClickLogoutDoc(View view) {

        String savecurrentdate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String savetime = currentTime.format(calendar.getTime());
        HashMap<String, Object> onlinestatus = new HashMap<>();
        onlinestatus.put("time", savetime);
        onlinestatus.put("date", savecurrentdate);
        onlinestatus.put("status", "offline");
        onlinestatus.put("player_id", "");
        String curruserid = auth1.getUid();
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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                imagetoStore = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
               
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                String strDate = mdformat.format(calendar.getTime());


                ///////////////////////////////////////////////////////

                StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "uploaded sucesfully", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("image", base64Image);
                        param.put("email", currentemail);
                        return param;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);


                ////////////////////////////////////////////////


                Calendar c = Calendar.getInstance();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference ref = storage.getReference().child("dp/" + c.getTimeInMillis() + ".jpg");
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

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("Dp", uri.toString());

                                        reference.set(map, SetOptions.merge())
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
}