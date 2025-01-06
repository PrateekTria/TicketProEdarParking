package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.PPZModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface PPZModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdmin(PPZModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPPZModel(PPZModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPPZModelList(List<PPZModel> taskForm22500ModelList);

    @Query("select * from ppz")
    List<PPZModel> getPPZModel();

    @Query("DELETE from ppz")
    void removeAll();

    @Query("DELETE from ppz where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from ppz")
    long getNextPrimaryId();
}
