package com.waqasahmad.remotecare;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    TextView Name, Email, Gender, U_Type;

    //for logging out
    DatabaseReference reference1;
    FirebaseAuth auth1;
    FirebaseDatabase database1;

    private FirebaseUser user;
    Bitmap bitmap;
    DatabaseHandler objectdatabasehandler;
    EditText current_password, new_password;

    // URLs for server requests
    String url1 = "", url2 = "", url3 = "", url4 = "";

    FirebaseAuth mAuth;
    String currentemail;

    // Firestore
    FirebaseFirestore db;
    DocumentReference reference;
    String current_password_str, new_password_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        profile_circle = findViewById(R.id.profile_circle);
        objectdatabasehandler = new DatabaseHandler(this);


        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Gender = findViewById(R.id.Gender);
        U_Type = findViewById(R.id.U_Type);

        // Getting the server IP from SharedPreferences
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");

        // Constructing the server URLs
        url1 = "http://" + s1 + "/smd_project/imageupload.php";
        url2 = "http://" + s1 + "/smd_project/update_password.php";
        url3 = "http://" + s1 + "/smd_project/user_token_delete.php";
        url4 = "http://" + s1 + "/smd_project/fetch_alldata_from_user.php";


        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);
        update_btn = findViewById(R.id.update_btn);


        //Initializing Firebase MAuth instance
        mAuth = FirebaseAuth.getInstance();

        //getting email of the logged in user for document
        currentemail = mAuth.getCurrentUser().getEmail();

        //Initializing Firebase MAuth instance
        db = FirebaseFirestore.getInstance();


        // for logging out
        auth1 = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();
        reference1 = database1.getReference("Users");

        // Fetching user data from the server
        StringRequest request = new StringRequest(Request.Method.POST, url4, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj2 = new JSONArray(response);


                    for (int i = 0; i < obj2.length(); i++) {
                        JSONObject jsonObject = obj2.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("imageurl");
                        String user_type = jsonObject.getString("user_type");
                        String gender = jsonObject.getString("gender");

                        // Setting the fetched data in the TextViews
                        Name.setText("Name             " + name);
                        Email.setText("Email              " + currentemail);
                        Gender.setText("Gender           " + gender);

                        if (user_type.equals("1")) {
                            U_Type.setText("User Type      " + "Patient");
                        } else {
                            U_Type.setText("User Type      " + "Doctor");
                        }

                        // Loading the profile image using Picasso library
                        if (!image.trim().equals("null")) {
                            Picasso.get().load("http://" + s1 + "/smd_project/" + image).into(profile_circle);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

                param.put("email", currentemail);

                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        // Listener for the update button click
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setMessage("Do you want to update your password?")
                        .setTitle("Update Password");

                // Add the positive button (OK button) to the AlertDialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        // Get the current password and new password entered by the user
                        current_password_str = current_password.getText().toString();
                        new_password_str = new_password.getText().toString();

                        // Check if the current password and new password are not empty
                        if (!(current_password_str.equals("") && new_password_str.equals(""))) {

                            // Get the current user
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            final String email = user.getEmail();

                            // Create an AuthCredential object using the current email and password
                            AuthCredential credential = EmailAuthProvider.getCredential(email, current_password_str);

                            // Re-authenticate the user with the provided credentials
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // User re-authenticated successfully
                                        // Update the user's password with the new password

                                        user.updatePassword(new_password_str).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (!task.isSuccessful()) {
                                                    // Password update failed
                                                    Toast.makeText(getApplicationContext(), " Password not Updated", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();

                                                    // Send a POST request to the server
                                                    StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

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

                                                            // Create a parameter map for the request
                                                            Map<String, String> param = new HashMap<String, String>();

                                                            // Add the new password and email to the parameters
                                                            param.put("password", new_password_str);
                                                            param.put("email", email);
                                                            return param;
                                                        }
                                                    };
                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                    queue.add(request);
                                                }

                                                // Update the password in the Firebase Firestore database
                                                Map<String, Object> new_password_obj = new HashMap<>();
                                                new_password_obj.put("Password", new_password_str);
                                                db.collection("users").document(email).set(new_password_obj, SetOptions.merge())
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                // Password updated in the Firestore database
                                                            }
                                                        });
                                            }
                                        });
                                    } else {
                                        // Re-authentication failed
                                        Toast.makeText(
                                                getApplicationContext(),
                                                " Authentication Failed for password",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {

                            // Password fields are empty
                            Toast.makeText(getApplicationContext(), "Password is empty", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                // Add the negative button (Cancel button) to the AlertDialog
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

                // Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        // Listener for the profile circle click
        profile_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Open the image picker to select a profile image
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 30);

            }
        });
    }

    //return to same screen by redirecting
    public void ClickProfile(View view) {
        recreate();
    }

    //Open/Close drawer function
    public void ClickMenu(View view) {
        MainActivity2.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        MainActivity2.closeDrawer(drawerLayout);
    }

    //Go to other screens by intent
    public void ClickPro(View view) {
        MainActivity2.redirectActivity(this, MainActivity2.class);
    }

    public void ClickPrescriptionDetails(View view) {
        MainActivity2.redirectActivity(this, Prescription_Details.class);
    }

    public void ClickMeals(View view) {
        MainActivity2.redirectActivity(this, Meals.class);
    }

    public void ClickOverview(View view) {
        MainActivity2.redirectActivity(this, OverView.class);
    }

    public void ClickChat(View view) {
        MainActivity2.redirectActivity(this, messagemain.class);
    }

    // ClickLogout function to log out the user
    public void ClickLogout(View view) {

        // Save the current date and time
        String savecurrentdate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate = currentdate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String savetime = currentTime.format(calendar.getTime());

        // Update the user's online status and player ID
        HashMap<String, Object> onlinestatus = new HashMap<>();
        onlinestatus.put("time", savetime);
        onlinestatus.put("date", savecurrentdate);
        onlinestatus.put("status", "offline");
        onlinestatus.put("player_id", "");
        String curruserid = auth1.getUid();
        reference1.child(curruserid).updateChildren(onlinestatus);

        // Send a POST request to the server
        StringRequest request = new StringRequest(Request.Method.POST, url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

                // Create a parameter map for the request
                Map<String, String> param = new HashMap<String, String>();
                param.put("email", currentemail);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        // Sign out the user
        auth1.signOut();
        finish();

        // Redirect to the MainActivity_signin screen
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

            // Handle the image selection from the image picker
            // Get the selected image URI
            image = data.getData();
            try {

                // Convert the URI to a bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                imagetoStore = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                objectdatabasehandler.storeImage(new ModelClassoffline(currentemail, imagetoStore));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Compress the bitmap and convert it to a base64 string
            ByteArrayOutputStream byteArrayOutputStream;
            byteArrayOutputStream = new ByteArrayOutputStream();
            if (bitmap != null) {

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                String strDate = mdformat.format(calendar.getTime());

                // Send a POST request to the server with the base64 image and email
                StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the server response
                        Toast.makeText(getApplicationContext(), "Uploaded successfully", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        // Create a parameter map for the request
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("image", base64Image);
                        param.put("email", currentemail);
                        return param;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

                // Upload the image to Firebase Storage
                Calendar c = Calendar.getInstance();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference ref = storage.getReference().child("dp/" + c.getTimeInMillis() + ".jpg");
                ref.putFile(image)

                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // Get the download URL for the uploaded image
                                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        // Load the profile image from the download URL using Picasso
                                        Picasso.get().load(uri.toString()).into(profile_circle);

                                        // Update the user's profile image in Firestore
                                        reference = db.collection("users").document(currentemail);

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("Dp", uri.toString());

                                        reference.set(map, SetOptions.merge())
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(
                                                                Profile.this,
                                                                "Successfully uploaded image",
                                                                Toast.LENGTH_LONG
                                                        ).show();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(
                                                                Profile.this,
                                                                "Could not upload image",
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
}