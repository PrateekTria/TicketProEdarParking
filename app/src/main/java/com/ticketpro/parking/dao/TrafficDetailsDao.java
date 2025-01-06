package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.TrafficDetails;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface TrafficDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrafficDetails(TrafficDetails... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTrafficDetails(TrafficDetails data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrafficDetailsList(List<TrafficDetails> taskForm22500ModelList);

    @Query("select * from traffic_details")
    List<TrafficDetails> getTrafficDetails();

    @Query("DELETE from traffic_details")
    void removeAll();

    @Query("DELETE from traffic_details where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from traffic_details")
    long getNextPrimaryId();
}
