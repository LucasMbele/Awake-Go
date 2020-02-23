package com.example.awakego.Views;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.awakego.R;

public class AlarmWakefulService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG =   AlarmService.class.getSimpleName();
    private static final String ACTION_DEBUG = "com.example.awakego.action.DEBUG";

    int startId;
    private boolean isRunning;
    private MediaPlayer mp;
    private AlarmReceiver mReceiver;

    public AlarmWakefulService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String idChannel = "Channel1";

        String state = intent.getStringExtra("extra");
        Uri song = intent.getParcelableExtra("song");

        assert state != null;

        switch (state)
        {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId =0;
                break;
        }
        //if there is no music playing, and the user presses "alarm on"

        if(!this.isRunning && startId == 1)
        {
            try{
                mp = MediaPlayer.create(this, song);
            }catch(NullPointerException e) {
                mp = MediaPlayer.create(this, R.raw.dream);
            }
            mp.start();

            this.isRunning = true;
            this.startId = 0;
        }
        //if there is music playing and the user pressed "alarm off"
        //music about stop playing
        else if (this.isRunning && startId == 0)
        {
            mp.stop();
            mp.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        //if there is music playing and the user pressed "alarm off"
        //do nothing
        else if (!this.isRunning && startId == 0)
        {
            this.isRunning = false;
            this.startId = 0;
        }
        //if there is music playing and the user pressed "alarm on"
        else if (this.isRunning && startId == 1)
        {
            this.isRunning = true;
            this.startId = 1;
        }
        else
        {
            Log.e("else","somehow you reached");
        }
       // return START_NOT_STICKY;
        Log.i("AlarmWakefulReceiver", "Completed service @ " + SystemClock.elapsedRealtime());
               AlarmWakefulReceiver.completeWakefulIntent(intent);

    }

}
