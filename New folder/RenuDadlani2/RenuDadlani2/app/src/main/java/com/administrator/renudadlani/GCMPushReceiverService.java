package com.administrator.renudadlani;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Administrator on 8/25/2016.
 */
public class GCMPushReceiverService extends GcmListenerService {
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings, pwd;
    String a, message, adminfont;
    String settings1;

    public void onMessageReceived(String from, Bundle data) {
        settings=this.getSharedPreferences(PREFS_NAME,this.MODE_PRIVATE);
        if (settings.getString("logged", "").toString().equals("logged"))
        {
            if(settings.getString("admin", "").toString().equals("admin")||settings.getString("dadlanirenu@hotmail.com", "").toString().equals("dadlanirenu@hotmail.com")) {
                message = data.getString("message");
                adminfont=settings.getString("user","");
                sendNotification1(message);
            }

        }
        message = data.getString("message");
        adminfont=settings.getString("user","");
        sendNotification(message);
    }

    private void sendNotification1(String message) {
        Intent intent = new Intent(this, com.administrator.renudadlani.ReplyList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message",message);
        intent.putExtra("id",adminfont);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Renu Replies!")
                .setSmallIcon(R.drawable.newnotification)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setContentText(message);
        notificationManager.notify(0, noBuilder.build());
    }

    private void sendNotification(String message) {
        Intent intent = new Intent(this, Home1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Home1.MESSAGE_RECEIVED);
        intent.putExtra("message",message);
        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Renu Updates!")
                .setSmallIcon(R.drawable.newnotification)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setContentText(message);
        notificationManager.notify(0, noBuilder.build());

    }


}

