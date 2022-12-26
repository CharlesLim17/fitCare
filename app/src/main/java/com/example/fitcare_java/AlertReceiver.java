package com.example.fitcare_java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.firebase.installations.time.SystemClock;

public class AlertReceiver extends BroadcastReceiver {
    String title, message;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.title = intent.getStringExtra("TITLE");
        this.message = intent.getStringExtra("MESSAGE");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title, message);
        final int id = (int) System.currentTimeMillis();
        notificationHelper.getManager().notify(id, nb.build());
    }
}
