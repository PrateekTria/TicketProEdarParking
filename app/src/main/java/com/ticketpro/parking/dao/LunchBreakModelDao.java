package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.LunchBreakModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface LunchBreakModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLunchBreakModel(LunchBreakModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertLunchBreakModel(LunchBreakModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLunchBreakModelList(List<LunchBreakModel> taskForm22500ModelList);

    @Query("select * from break_lunch")
    List<LunchBreakModel> getLunchBreakModel();

    @Query("DELETE from break_lunch")
    void removeAll();

    @Query("DELETE from break_lunch where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from break_lunch")
    long getNextPrimaryId();

}
