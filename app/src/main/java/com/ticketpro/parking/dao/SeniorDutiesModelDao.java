package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.SeniorDutiesModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface SeniorDutiesModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeniorDutiesModel(SeniorDutiesModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSeniorDutiesModel(SeniorDutiesModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSeniorDutiesModelList(List<SeniorDutiesModel> taskForm22500ModelList);

    @Query("select * from senior_duties")
    List<SeniorDutiesModel> getSeniorDutiesModel();

    @Query("DELETE from senior_duties")
    void removeAll();

    @Query("DELETE from senior_duties where id=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from senior_duties")
    long getNextPrimaryId();
}
