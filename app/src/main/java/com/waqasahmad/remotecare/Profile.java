package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.android.material.snackbar.Snackbar;
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

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    CircleImageView profile_circle;
    Uri image = null;
    private Bitmap imagetoStore;
    ImageView update_btn;

    //
    TextView Name,Email,Gender,U_Type;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;
    //
    private FirebaseUser user;

    Bitmap bitmap;
    DatabaseHandler objectdatabasehandler;

    //
    EditText current_password,new_password;

//    private static final String saveimageuser="http://"+Ip_server.getIpServer()+"/smd_project/imageupload.php";
//    private static final String update_password="http://"+Ip_server.getIpServer()+"/smd_project/update_password.php";
//
//    private static final String user_token_delete="http://"+Ip_server.getIpServer()+"/smd_project/user_token_delete.php";

    String url1="",url2="",url3="";
    // validating user id
    FirebaseAuth mAuth;

//    FirebaseUser user ;
    String currentemail ;

    FirebaseFirestore db;
    DocumentReference reference;

    //
    String current_password_str,new_password_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        drawerLayout=findViewById(R.id.drawer_layout);
        profile_circle=findViewById(R.id.profile_circle);
        objectdatabasehandler=new DatabaseHandler(this);

        //
        Name=findViewById(R.id.Name);
        Email=findViewById(R.id.Email);
        Gender=findViewById(R.id.Gender);
        U_Type=findViewById(R.id.U_Type);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/imageupload.php";
        url2 ="http://"+s1+"/smd_project/update_password.php";
        url3 ="http://"+s1+"/smd_project/user_token_delete.php";

        //
        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        update_btn = findViewById(R.id.update_btn);



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
                                    Name.setText("Name             " + task.getResult().getString("Name"));
                                    Email.setText("Email              " +task.getResult().getString("Email"));
                                    Gender.setText("Gender           " +task.getResult().getString("Gender"));
                                    U_Type.setText("User Type      " +task.getResult().getString("User_Type"));


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



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current_password_str = current_password.getText().toString();
                new_password_str = new_password.getText().toString();

                if( !(current_password_str.equals("") && new_password_str.equals("")) )
                {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email,current_password_str);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {


                                user.updatePassword(new_password_str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext()," Password not Updated" , Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"Password updated" , Toast.LENGTH_SHORT).show();
                                            StringRequest request=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(
                                                            getApplicationContext(),
                                                            error.toString(),Toast.LENGTH_LONG).show();
                                                }
                                            }){
                                                @Nullable
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String,String> param=new HashMap<String,String>();

                                                    param.put("password",new_password_str);

                                                    param.put("email",email);

                                                    return param;
                                                }
                                            };
                                            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
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
                                                                Profile.this,
                                                                "updated in firebase",
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                    }
                                                });

                                                        ////////////////////////////////////////

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(
                                        getApplicationContext(),
                                        " Authentication Failed for password" ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Password is empty" , Toast.LENGTH_SHORT).show();

                }
            }
        });


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

    public void ClickLogout(View view)
    {

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

        StringRequest request=new StringRequest(Request.Method.POST, url3, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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
                param.put("email",currentemail);
                return param;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

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



                ///////////////////////////////////////////////////////

                StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"uploaded sucesfully",Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String,String> param=new HashMap<String,String>();
                        param.put("image",base64Image);
                        param.put("email",currentemail);
                        return param;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                queue.add(request);


                ////////////////////////////////////////////////

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

                                    Log.d("profileeeee",uri.toString());

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