package com.example.awakego.Views;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.multidex.MultiDex;

public class Channels extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Intent back_activity = new Intent();
            //String name_alarm = back_activity.getStringExtra("alarm");
            String idChannel = "Channel1";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel  = new NotificationChannel(idChannel, "Awake&Go", importance);
            mChannel.setDescription("Awake&Go");
            mChannel.setLightColor(Color.GREEN);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(mChannel);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
