package com.example.fitcare_java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = 0;
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(reminderAddFragment.getTitle(), reminderAddFragment.getMessage());
        notificationHelper.getManager().notify(id++, nb.build());
    }
}
