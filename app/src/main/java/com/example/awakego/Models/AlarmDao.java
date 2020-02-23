package com.example.awakego.Models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface AlarmDao
{
    @Query("SELECT * FROM alarm")
    LiveData<List<Alarm>> getAll();

    @Query("DELETE FROM alarm")
    void deleteAll();

    @Query ("SELECT * FROM alarm WHERE alarm_id IN (:alarmIds)")
    List <Alarm> loadAllByIds(int[] alarmIds);

    @Query ("SELECT * FROM alarm WHERE alarm_name LIKE :first" )
    Alarm findByName(String first);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Alarm alarm);

    @Update
    int update(Alarm alarm);

    @Delete
    int delete(Alarm alarm);


}
