package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.ticketpro.parking.dar.model.DarVehicleTask;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarVehicleTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleTask(DarVehicleTask... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleTask(DarVehicleTask data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleTaskList(List<DarVehicleTask> equipmentList);

    @Query("DELETE from dar_vehicle_task")
    void removeAll();

    @Query("DELETE from dar_vehicle_task where veh_task_id=:id")
    void removeById(int id);

    @Query("select * from dar_vehicle_task where custid = :userid")
    Maybe<List<DarVehicleTask>> getDarVehicleTask(int userid);

}
