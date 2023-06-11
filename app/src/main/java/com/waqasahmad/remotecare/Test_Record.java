package com.waqasahmad.remotecare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    LinearLayout btn1, btn2, btn3, btn4;

    TextView add_img;
    TextView add_details;
    EditText details;
    Uri image = null;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseDatabase database;
    DatabaseReference reference;

    String email1;
    String details1;

    String url1 = "", url2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_test_record);

        add_img = findViewById(R.id.add_img);
        dp = findViewById(R.id.dp);

        details = findViewById(R.id.details);
        back_btn = findViewById(R.id.back_btn);
        btn1 = findViewById(R.id.home_btn2);
        btn2 = findViewById(R.id.appointment_btn);
        btn3 = findViewById(R.id.record_btn);
        btn4 = findViewById(R.id.chat_btn);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/insert_test_record.php";
        url2 = "http://" + s1 + "/smd_project/retireveimage.php";


        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email1 = user.getEmail();


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                // Check if the result is successful
                if (result.getResultCode() == Activity.RESULT_OK) {

                    // Get the data from the result
                    Intent data = result.getData();

                    // Get the URI of the selected image
                    Uri uri = data.getData();

                    // Store the URI in the "image" variable
                    image = data.getData();

                    // Use Picasso library to load the image into the ImageView "dp"
                    Picasso.get().load(uri).into(dp);

                    try {

                        // Get the bitmap from the URI using the ContentResolver
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

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

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);

            }
        });

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an AlertDialog to confirm the upload
                AlertDialog.Builder builder = new AlertDialog.Builder(Test_Record.this);
                builder.setMessage("Do you want to upload this record?")
                        .setTitle("Upload Test Record");

                // Add the positive button (OK)
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        // Get the details entered by the user
                        details1 = details.getText().toString();

                        // Create a ByteArrayOutputStream to compress the bitmap into bytes
                        ByteArrayOutputStream byteArrayOutputStream;
                        byteArrayOutputStream = new ByteArrayOutputStream();

                        // Check if a bitmap is available
                        if (bitmap != null) {

                            // Compress the bitmap into JPEG format with 100 quality and store it in the ByteArrayOutputStream
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                            byte[] bytes = byteArrayOutputStream.toByteArray();

                            // Convert the compressed image bytes to base64 string
                            final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

                            // Get the current time as a string
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                            String strDate = mdformat.format(calendar.getTime());

                            ////////////////////////////////////////////////////////////////////////////////

                            // Get the current time as a Calendar instance
                            Calendar c = Calendar.getInstance();

                            // Create a FirebaseStorage instance
                            FirebaseStorage storage = FirebaseStorage.getInstance();

                            // Create a StorageReference with a unique filename for the uploaded image
                            StorageReference ref = storage.getReference().child("test_record/" + c.getTimeInMillis() + ".jpg");

                            // Upload the image file to Firebase Storage
                            ref.putFile(image)

                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            // Image upload successful

                                            // Get the download URL of the uploaded image
                                            Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    // The download URL of the image is available
                                                    // Create a StringRequest to make a POST request to a URL

                                                    StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            // Handle the response from the server upon successful upload
                                                            Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_LONG).show();
                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                            // Handle the error response from the server
                                                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }) {
                                                        @Nullable
                                                        @Override
                                                        protected Map<String, String> getParams() throws AuthFailureError {

                                                            // Create a Map to store the parameters for the POST request
                                                            Map<String, String> param = new HashMap<String, String>();

                                                            // Add the parameters to the Map
                                                            param.put("link", uri.toString());
                                                            param.put("image", base64Image);
                                                            param.put("details", details1);
                                                            param.put("email", email1);

                                                            return param;
                                                        }
                                                    };

                                                    // Create a RequestQueue and add the StringRequest to it
                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                    queue.add(request);

                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            // Image upload failed
                                            Toast.makeText(Test_Record.this,
                                                            "Failed to upload image ",
                                                            Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    });


                        } else {

                            // No bitmap available
                            Toast.makeText(getApplicationContext(), "Error uploading image", Toast.LENGTH_SHORT).show();
                        }

                        // Reset the ImageView and EditText after the upload
                        dp.setImageResource(R.drawable.add);
                        details.setText("");


                    }
                });

                // Add the negative button (Cancel)
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        dialog.cancel();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}