package com.waqasahmad.remotecare;

import static com.waqasahmad.remotecare.R.id.add_img;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class Test_Record extends AppCompatActivity {

    Bitmap bitmap;
    ImageView dp;

    LinearLayout back_btn;
    LinearLayout btn1,btn2,btn3,btn4;

    TextView add_img;
    TextView add_details;
    EditText details;
    Uri image = null;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseDatabase database ;
    DatabaseReference reference ;

    String email1;
    String details1;

//    private static final String insert_img_url="http://"+Ip_server.getIpServer()+"/smd_project/insert_test_record.php";
//    private static final String retreive_img="http://"+Ip_server.getIpServer()+"/smd_project/retireveimage.php";

    String url1="",url2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_test_record);

        add_img = findViewById(R.id.add_img);
        dp = findViewById(R.id.dp);

        details = findViewById(R.id.details);
        back_btn = findViewById(R.id.back_btn);
        btn1=findViewById(R.id.home_btn2);
        btn2=findViewById(R.id.appointment_btn);
        btn3=findViewById(R.id.record_btn);
        btn4=findViewById(R.id.chat_btn);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 ="http://"+s1+"/smd_project/insert_test_record.php";
        url2 ="http://"+s1+"/smd_project/retireveimage.php";


        //Initializing Firebase MAuth instance
        mAuth=FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email1 = user.getEmail();


        ActivityResultLauncher<Intent>activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                    Uri uri=data.getData();
                    image = data.getData();
                    Picasso.get().load(uri).into(dp);
                    Log.d("pro22",uri.toString());

                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test_Record.this, MainActivity2.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test_Record.this, Patient_All_appointments.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test_Record.this, Add_records.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test_Record.this, messagemain.class));
            }
        });






        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                details1 = details.getText().toString();

                Log.d("detailllll",details.getText().toString());

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream=new ByteArrayOutputStream();
                if(bitmap != null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byte[] bytes=byteArrayOutputStream.toByteArray();
                    final String base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                    String strDate =mdformat.format(calendar.getTime());



                    ////////////////////////////////////////////////////////////////////////////////

                    Calendar c = Calendar.getInstance();
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference ref = storage.getReference().child("test_record/" +c.getTimeInMillis()+ ".jpg");
                    ref.putFile(image)

                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>()
                                    {
                                        @Override
                                        public void onSuccess(Uri uri) {

                                            Log.d("p33333",uri.toString());


                                            StringRequest request=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String response)
                                                {
                                                    Log.d("res1111", response.toString());
                                                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                                                    Toast.makeText(getApplicationContext(),"uploaded sucesfully",Toast.LENGTH_LONG).show();
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
                                                protected Map<String, String> getParams() throws AuthFailureError
                                                {
                                                    Map<String,String> param=new HashMap<String,String>();
                                                    Log.d("detailllll222",uri.toString());
                                                    param.put("link", uri.toString());
                                                    param.put("image",base64Image);
                                                    param.put("details",details1);
                                                    param.put("email",email1);

                                                    return param;
                                                }
                                            };
                                            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                            queue.add(request);

                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Test_Record.this,
                                                    "Failed to upload image ",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                }
                            });

                    ////////////////////////////////////////////////////////////////////////////////


                }
                else
                {
                    Toast.makeText(getApplicationContext(),"error uploading image",Toast.LENGTH_SHORT).show();
                }

                dp.setImageResource(R.drawable.add);
                details.setText("");

            }
        });
    }
}