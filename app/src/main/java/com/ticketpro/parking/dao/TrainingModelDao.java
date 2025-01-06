package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.TrainingModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface TrainingModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrainingModel(TrainingModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTrainingModel(TrainingModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrainingModelList(List<TrainingModel> taskForm22500ModelList);

    @Query("select * from training")
    List<TrainingModel> getTrainingModel();

    @Query("DELETE from training")
    void removeAll();

    @Query("select max(id) as max_id from traffic_details")
    long getNextPrimaryId();

    @Query("DELETE from traffic_details where appId=:id")
    void removeById(int id);
}
