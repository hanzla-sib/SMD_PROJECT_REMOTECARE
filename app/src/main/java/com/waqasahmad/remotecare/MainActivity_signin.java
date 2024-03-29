package com.waqasahmad.remotecare;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity_signin extends AppCompatActivity {
    TextView signup;
    EditText email, password;
    ImageView signin;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    CheckBox bt_show;

    //
    FirebaseDatabase database;
    DatabaseReference reference;

    String token = "";

    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "101";
    String userstr = "";
    String strdoctor = "Doctor";
    String strpatient = "Patient";


//    private static final String user_token="http://"+Ip_server.getIpServer()+"/smd_project/user_token.php";

    String url1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        createNotificationChannel();
        getToken();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String s1 = sh.getString("Ip", "");
        url1 = "http://" + s1 + "/smd_project/user_token.php";


        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signinbutton);
        email = findViewById(R.id.email2);
        password = findViewById(R.id.password2);
        bt_show = findViewById(R.id.bt_show);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // taking back to sign up screen  if signup is pressed
                startActivity(new Intent(MainActivity_signin.this, MainActivity_signup.class));

            }
        });

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        bt_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          mAuth.signInWithEmailAndPassword(
                                                          email.getText().toString(),
                                                          password.getText().toString()
                                                  )
                                                  .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                      @Override
                                                      public void onSuccess(AuthResult authResult) {


                                                          String savecurrentdate;
                                                          Calendar calendar = Calendar.getInstance();
                                                          SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyy");
                                                          savecurrentdate = currentdate.format(calendar.getTime());
                                                          SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                                                          String savetime = currentTime.format(calendar.getTime());
                                                          HashMap<String, Object> onlinestatus = new HashMap<>();
                                                          onlinestatus.put("time", savetime);
                                                          onlinestatus.put("date", savecurrentdate);
                                                          onlinestatus.put("status", "online");
                                                          onlinestatus.put("player_id", token);
                                                          String curruserid = mAuth.getUid();
                                                          reference.child(curruserid).updateChildren(onlinestatus);


                                                          //

                                                          String useremail = mAuth.getCurrentUser().getEmail();
                                                          Log.d("useremail", useremail);

                                                          // adding user with its token in firebase as well as on storage
                                                          db.collection("users").
                                                                  document(useremail).get().
                                                                  addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                      @Override
                                                                      public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                                          DocumentSnapshot document = task.getResult();
                                                                          StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                                                              @Override
                                                                              public void onResponse(String response) {

                                                                                  //   Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
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
                                                                                  param.put("email", useremail);
                                                                                  param.put("Token", token);
                                                                                  return param;
                                                                              }
                                                                          };
                                                                          RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                          queue.add(request);

                                                                          JSONObject obj;
                                                                          obj = new JSONObject(document.getData());

                                                                          try {
                                                                              String usertype = obj.getString("User_Type");

                                                                              if (usertype.equals(strpatient)) {
                                                                                  startActivity(new Intent(MainActivity_signin.this, MainActivity2.class)); // for patient view
                                                                              } else if (usertype.equals(strdoctor)) {
                                                                                  startActivity(new Intent(MainActivity_signin.this, Doctor1.class)); // for doctor view
                                                                              }

                                                                          } catch (JSONException e) {
                                                                              e.printStackTrace();
                                                                          }

                                                                      }
                                                                  });
                                                      }
                                                  })

                                                  .addOnFailureListener(new OnFailureListener() {
                                                      @Override
                                                      public void onFailure(@NonNull Exception e) {
                                                          Toast.makeText(
                                                                  MainActivity_signin.this,
                                                                  "Failed to sign-in",
                                                                  Toast.LENGTH_LONG
                                                          ).show();
                                                      }
                                                  });
                                      } // onclick of sign in button ends here


                                  }
        );
    }



    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                //if task is failed then
                if (!task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Failed to get token");
                }
                token = task.getResult();
                Log.d(TAG, "onComplete: " + token + "      : hahhaha");
//
            }
        });
    }

    // generating notification
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "firebaseNotifChannel";

            String description = "Receive Firebase Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}


