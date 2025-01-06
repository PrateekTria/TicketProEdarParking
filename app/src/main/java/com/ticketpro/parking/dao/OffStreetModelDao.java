package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.OffStreetModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface OffStreetModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOffStreetModel(OffStreetModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOffStreetModel(OffStreetModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOffStreetModelList(List<OffStreetModel> taskForm22500ModelList);

    @Query("select * from off_street")
    List<OffStreetModel> getOffStreetModel();

    @Query("DELETE from off_street")
    void removeAll();

    @Query("DELETE from off_street where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from off_street")
    long getNextPrimaryId();
}
