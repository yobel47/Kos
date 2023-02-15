package com.binar.kos.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.binar.kos.R
import com.binar.kos.view.ui.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessageReceiver : FirebaseMessagingService() {


    // Override onNewToken to get new token
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebase", "onNewToken: " + token)
    }

    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // First case when notifications are received via
        // data event
        // Here, 'title' and 'message' are the assumed names
        // of JSON
        // attributes. Since here we do not have any data
        // payload, This section is commented out. It is
        // here only for reference purposes.
        /*if(remoteMessage.getData().size()>0){
            showNotification(remoteMessage.getData().get("title"),
                          remoteMessage.getData().get("message"));
        }*/

        // Second case when notification payload is
        // received.
        if (remoteMessage.notification != null) {
            // Since the notification is received directly from
            // FCM, the title and the body can be fetched
            // directly as below.
            remoteMessage.notification!!.title?.let { title ->
                remoteMessage.notification!!.body?.let { body ->
                    showNotification(
                        this,
                        title,
                        body
                    )
                }
            }
            Log.d(
                "MyFirebase",
                "onMessageReceived: FROM: " + remoteMessage.from + " \n DATA: " + remoteMessage.notification
            )
        }
    }


    // Method to display the notifications
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(
        context: Context,
        title: String,
        message: String
    ) {
        // Pass the intent to switch to the MainActivity
        val intent = Intent(this, MainActivity::class.java)
        // Assign channel ID
        val channel_id = "notification_channel"
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Pass the intent to PendingIntent to start the
        // next Activity
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context.applicationContext,
            channel_id
        )
            .setSmallIcon(R.drawable.logo_horizontal)
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setContentTitle(title)
            .setContentText(message)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager?
        // Check if the Android Version is greater than Oreo
        val notificationChannel = NotificationChannel(
            channel_id, "web_app",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager!!.createNotificationChannel(
            notificationChannel
        )
        notificationManager.notify(0, builder.build())
    }
}