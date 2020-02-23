package com.example.awakego.Models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class AlarmViewModel extends AndroidViewModel {
    private AlarmRepository mAlarmRepository;
    private LiveData<List<Alarm>> mallAlarms;
    private LiveData<Long> insert_alarm;
    private LiveData<Integer> update_alarm;
    private LiveData<Integer> delete_alarm;



    public AlarmViewModel(@NonNull Application application)
    {
        super(application);
        mAlarmRepository = new AlarmRepository(application);
        mallAlarms = mAlarmRepository.getAllData();
        insert_alarm = mAlarmRepository.get_insert_alarms();
        update_alarm =mAlarmRepository.get_update_alarms();
        delete_alarm = mAlarmRepository.get_delete_alarms();
    }


    public void insert(Alarm alarm)
    {
        mAlarmRepository.insert(alarm);

    }

    public void update(Alarm alarm) {
        mAlarmRepository.update(alarm);
    }

    public void delete(Alarm alarm) {
        mAlarmRepository.delete(alarm);
    }

    public void deleteAllAlarms() {
        mAlarmRepository.delete_Allalarms();
    }

    public LiveData<List<Alarm>> getMallAlarms() {
        return mallAlarms;
    }

    public LiveData<Long> getInsert_alarm() {
        return insert_alarm;
    }

    public LiveData<Integer> getUpdate_alarm() {
        return update_alarm;
    }

    public LiveData<Integer> getDelete_alarm() {
        return delete_alarm;
    }
}
