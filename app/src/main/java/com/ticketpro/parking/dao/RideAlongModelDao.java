package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.RideAlongModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface RideAlongModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRideAlongModel(RideAlongModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRideAlongModel(RideAlongModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRideAlongModelList(List<RideAlongModel> taskForm22500ModelList);

    @Query("select * from ride_along")
    List<RideAlongModel> getRideAlongModel();

    @Query("DELETE from ride_along")
    void removeAll();

    @Query("DELETE from ride_along where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from ride_along")
    long getNextPrimaryId();
}
