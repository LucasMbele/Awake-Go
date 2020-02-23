package com.example.awakego.Models;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private AlarmDao mAlarmDao;
    private LiveData<List<Alarm>> mAllData;
    private SingleLiveEvent<Long> insert_alarm = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> update_alarm = new SingleLiveEvent<>();
    private SingleLiveEvent<Integer> delete_alarm = new SingleLiveEvent<>();


    public AlarmRepository(Application application)
    {
        AlarmDatabase alarmDatabase = AlarmDatabase.getInstance(application);
        this.mAlarmDao = alarmDatabase.alarmDao();
        this.mAllData = mAlarmDao.getAll();
    }
    public LiveData<Long> get_insert_alarms()
    {
        return insert_alarm;
    }
    public LiveData<Integer> get_update_alarms()
    {
        return update_alarm;
    }
    public LiveData<Integer> get_delete_alarms()
    {
        return delete_alarm;
    }


    public void insert(Alarm alarm) {
        new InsertAsyncTask(mAlarmDao).execute(alarm);
    }

    public void update(Alarm alarm) {
        new UpdateAsyncTask(mAlarmDao).execute(alarm);
    }

    public void delete(Alarm alarm) {
        new DeleteAsyncTask(mAlarmDao).execute(alarm);
    }

    public void delete_Allalarms() {
        new DeleteAllAsyncTask(mAlarmDao).execute();
    }

    public LiveData<List<Alarm>> getAllData() {
        return mAllData;
    }


    public class InsertAsyncTask extends AsyncTask<Alarm, Void, Long>
    {
        private AlarmDao mAlarmDao;
        public InsertAsyncTask(AlarmDao alarmDao) {
            this.mAlarmDao = alarmDao;
        }
        @Override
        protected Long doInBackground(Alarm... alarms) {
            return mAlarmDao.insert(alarms[0]);
        }
        @Override
        protected void onPostExecute(Long along) {
            super.onPostExecute(along);
            insert_alarm.setValue(along);
        }
    }
    public class UpdateAsyncTask extends AsyncTask<Alarm, Void, Integer>
    {
        private AlarmDao mAlarmDao;
        public UpdateAsyncTask(AlarmDao alarmDao)
        {
            this.mAlarmDao = alarmDao;
        }
        @Override
        protected Integer doInBackground(Alarm... alarms)
        {
           return mAlarmDao.update(alarms[0]);
        }
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            update_alarm.setValue(integer);
        }
    }

    public  class DeleteAsyncTask extends AsyncTask<Alarm, Void,Integer>
    {
        private AlarmDao mAlarmDao;

        public DeleteAsyncTask(AlarmDao alarmDao)
        {
            this.mAlarmDao = alarmDao;
        }
        @Override
        protected Integer doInBackground(Alarm... alarms)
        {
           return mAlarmDao.delete(alarms[0]);
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);
            delete_alarm.setValue(integer);
        }
    }
    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private AlarmDao mAlarmDao;

        public DeleteAllAsyncTask(AlarmDao alarmDao) {
            this.mAlarmDao = alarmDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAlarmDao.deleteAll();
            return null;
        }
    }
}
