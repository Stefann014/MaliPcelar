package com.example.malipcelar.activity.pomocneKlase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.malipcelar.R;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String napomena = intent.getStringExtra("NAPOMENA");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit").
                setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("Napomena!").setContentText(napomena).setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notifcationManager = NotificationManagerCompat.from(context);
        notifcationManager.notify(200, builder.build());
    }
}
