package com.example.awakego.Models;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Alarm.class}, version = 2
)
public abstract class AlarmDatabase extends RoomDatabase
{
    private static AlarmDatabase INSTANCE;
    public abstract AlarmDao alarmDao();
    // synchronized is used to allow just a thread a the time to access to this method
    public static synchronized AlarmDatabase getInstance(Context context)
    {
        if (INSTANCE==null)
        {
            //When it happens to increase version number of database, it's right to call
            //fallbacktodestructiveMigration method allowing access to a new schema
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AlarmDatabase.class,"alarm_db")
           //.fallbackToDestructiveMigration()
                   // .addCallback(roomCallBack)
                    .build();
        }
        return INSTANCE;

    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

    // In case where, we want after create a database, populate data with default values
    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedAsyncTask(INSTANCE).execute();
        }
    };
    public static class PopulatedAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private AlarmDao mAlarmDao;
        public PopulatedAsyncTask( AlarmDatabase alarmDatabase) {
            mAlarmDao = alarmDatabase.alarmDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAlarmDao.insert(new Alarm("Awake",12,11 ,"Perfect Time",1));
            return null;
        }
    }
}
