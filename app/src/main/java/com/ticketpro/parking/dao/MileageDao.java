package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.School;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface MileageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMileage(Mileage data);

    @Query("select * from mileage")
    List<Mileage> getmileage();

    @Query("DELETE from mileage")
    void removeAll();

    @Query("DELETE from mileage where custid=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from mileage")
    long getNextPrimaryId();
}
