package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.TowModel;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface TowModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTowModel(TowModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTowModel(TowModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTowModel(List<TowModel> taskTowModelList);

    @Query("select * from tow")
    List<TowModel> getTowModel();

    @Query("DELETE from tow")
    void removeAll();

    @Query("DELETE from tow where appId=:id")
    void removeById(int id);
    @Query("select max(id) as max_id from tow")
    long getNextPrimaryId();
}
