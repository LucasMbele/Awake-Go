package com.example.awakego.Views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmWakefulReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String get_extra = intent.getStringExtra("extra");
        //String title = intent.getStringExtra("alarmName");
        Uri song = intent.getParcelableExtra("song");
        Intent ringtone = new Intent(context, AlarmService.class);
        ringtone.putExtra("extra", get_extra);
        ringtone.putExtra("song", song);
        //context.startService(ringtone);
        ContextCompat.startForegroundService(context, ringtone);
        startWakefulService(context, ringtone);
        //context.startForegroundService(ringtone);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent back_activity = new Intent();
            //String name_alarm = back_activity.getStringExtra("alarm");
            String idChannel = "A";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(idChannel, "Awake&Go", importance);
            mChannel.setDescription("Awake&Go");
            mChannel.setLightColor(Color.GREEN);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(mChannel);
        }

        Log.d("AlarmService", "Notification sent.");
        Toast.makeText(context, "Alarm received", Toast.LENGTH_LONG).show();

    }

}
