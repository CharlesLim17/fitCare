package com.example.fitcare_java;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationHelper extends Application {

    public static final String channel1ID = "channel1ID";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    channel1ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(com.google.android.material.R.color.design_default_color_primary);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }
}
