package com.waqasahmad.remotecare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class StartScreen extends AppCompatActivity {
//    private static final String TAG = "PushNotification";
//    private static final String CHANNEL_ID="101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startscreen);

        new Handler().postDelayed(() -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null)
            {
//

                startActivity(new Intent(StartScreen.this, MainActivity2.class));
            }
            else
            {
                startActivity(new Intent(StartScreen.this, MainActivity_signin.class));

            }


            finish();
        }, 1000);
    }
//
//    private void getToken() {
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                //if task is failed then
//                if(!task.isSuccessful()){
//                    Log.d(TAG, "onComplete: Failed to get token");
//                }
//                String token = task.getResult();
//                Log.d(TAG, "onComplete: "+ token + "      : hahhaha");
////                StringRequest request=new StringRequest(Request.Method.POST, notify, new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////
////                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
////                        Log.d("responseeeeee ",response);
//////                            if(response.equals("success")){
////
////
////
//////
////
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
////                    }
////                }){
////                    @Nullable
////                    @Override
////                    protected Map<String, String> getParams() throws AuthFailureError {
////                        Map<String,String> param=new HashMap<String,String>();
////
//////
////                        param.put("tokeni",token);
//////
////
////                        return param;
////                    }
////                };
////                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
////                queue.add(request);
//            }
//        });
//    }
//    private void createNotificationChannel() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            CharSequence name="firebaseNotifChannel";
//
//            String description="Receive Firebase Notification";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
}
