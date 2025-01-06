package com.ticketpro.parking.dao;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.SignCheckModel;
import com.ticketpro.parking.dar.model.VehMaintenanceModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface VehMaintenanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdmin(VehMaintenanceModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertVehMaintenanceModel(VehMaintenanceModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVehMaintenanceModelList(List<VehMaintenanceModel> taskForm22500ModelList);

    @Query("select * from veh_maintenance")
    List<VehMaintenanceModel> getVehMaintenanceModel();

    @Query("DELETE from veh_maintenance")
    void removeAll();

    @Query("DELETE from veh_maintenance where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from veh_maintenance")
    long getNextPrimaryId();
}
