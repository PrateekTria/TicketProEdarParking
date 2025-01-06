package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.FlayerModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface FlayerModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlayerModel(FlayerModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFlayerModel(FlayerModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFlayerModelList(List<FlayerModel> taskForm22500ModelList);

    @Query("select * from flayer")
    List<FlayerModel> getFlayerModel();

    @Query("DELETE from flayer")
    void removeAll();

    @Query("DELETE from flayer where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from flayer")
    long getNextPrimaryId();
}
