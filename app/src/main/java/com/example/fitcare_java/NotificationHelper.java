package com.example.fitcare_java;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper{

    public static final String channel1ID = "Workout Reminder Channel";
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        createNotificationChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannels() {
        NotificationChannel channel1 = new NotificationChannel(
                channel1ID,
                "Workout Reminder",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(com.google.android.material.R.color.design_default_color_primary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if(notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notif)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);

    }
}
