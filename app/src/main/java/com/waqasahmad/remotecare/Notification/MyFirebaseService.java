package com.waqasahmad.remotecare.Notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.waqasahmad.remotecare.R;
import com.waqasahmad.remotecare.StartScreen;

public class MyFirebaseService extends FirebaseMessagingService {
    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID="101";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
       showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }
    private void showNotification(String title,String message){
        // Create an intent to start the StartScreen activity
        Intent intent = new Intent(this, StartScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create a PendingIntent to handle the click event of the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)// Set the small icon for the notification
                .setContentTitle(title)// Set the title for the notification
                .setContentText(message)// Set the content text for the notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the priority of the notification
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
}
