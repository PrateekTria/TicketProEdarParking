package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.ServiceRequestModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface ServiceRequestModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServiceRequestModel(ServiceRequestModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertServiceRequestModel(ServiceRequestModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServiceRequestModelList(List<ServiceRequestModel> taskForm22500ModelList);

    @Query("select * from service_request")
    List<ServiceRequestModel> getServiceRequestModel();

    @Query("DELETE from service_request")
    void removeAll();

    @Query("DELETE from service_request where id=:id")
    void removeById(int id);
    @Query("select max(id) as max_id from service_request")
    long getNextPrimaryId();
}
