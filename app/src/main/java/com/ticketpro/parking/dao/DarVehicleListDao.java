package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarVehicleList;
import com.ticketpro.parking.dar.model.DarVehicleTask;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarVehicleListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleList(DarVehicleList... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleList(DarVehicleList data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVehicleListList(List<DarVehicleList> darVehicleLists);

    @Query("DELETE from dar_vehicle_list")
    void removeAll();

    @Query("DELETE from dar_vehicle_list where veh_id=:id")
    void removeById(int id);

    @Query("select * from dar_vehicle_list where custid = :userid")
    Maybe<List<DarVehicleList>> getDarVehicleList(int userid);

    @Query("SELECT type from dar_vehicle_list WHERE veh_id = :vehicleId")
    String getTypeByVehicleId(int vehicleId);

}
