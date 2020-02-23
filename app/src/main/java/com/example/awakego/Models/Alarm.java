package com.example.awakego.Models;


import android.media.RingtoneManager;
import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;


@Entity (tableName = "alarm")
public class Alarm{

    public String alarm_date;
    public String alarm_time;

    @PrimaryKey (autoGenerate = true)
    public long alarm_id = 0;

    @ColumnInfo(name ="alarm_name")
    public String alarm_name;

    @ColumnInfo(name="alarm_hour")
    public int alarm_hour;

    @ColumnInfo (name="alarm_minute")
    public int alarm_minute;

    @ColumnInfo (name ="alarm_day")
    public int day;

    @ColumnInfo (name ="alarm_song")
    public String alarm_song;

    public Alarm(String alarm_name, int alarm_hour,int alarm_minute, String alarm_song,int day)
    {
        this.alarm_name = alarm_name;
        this.alarm_hour = alarm_hour;
        this.alarm_minute = alarm_minute;
        this.alarm_song = alarm_song;
        this.day = day;
    }


    public int getDay()
    {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(long alarm_id)
    {
        this.alarm_id = alarm_id;
    }

    public String getAlarm_song() {
        Uri song = Uri.parse(alarm_song);
        //RingtoneManager.getRingtone()
        return alarm_song;
    }

    public void setAlarm_song(String alarm_song) {
        this.alarm_song = alarm_song;
    }


    public String getAlarm_name()
    {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public String getAlarm_date()
    {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date)
    {
        this.alarm_date = alarm_date;
    }

    public String getAlarm_time()
    {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time)
    {
        this.alarm_time = alarm_time;
    }

    public int getAlarm_hour() {
        return alarm_hour;
    }

    public void setAlarm_hour(int alarm_hour) {
        this.alarm_hour = alarm_hour;
    }

    public int getAlarm_minute() {
        return alarm_minute;
    }

    public void setAlarm_minute(int alarm_minute)
    {
        this.alarm_minute = alarm_minute;
    }
}
