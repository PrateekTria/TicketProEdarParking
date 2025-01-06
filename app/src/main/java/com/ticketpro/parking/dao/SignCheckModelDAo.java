package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.Admin;
import com.ticketpro.parking.dar.model.SignCheckModel;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface SignCheckModelDAo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdmin(SignCheckModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSignCheckModel(SignCheckModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSignCheckModelList(List<SignCheckModel> taskForm22500ModelList);

    @Query("select * from sign_check")
    List<SignCheckModel> getSignCheckModel();

    @Query("DELETE from sign_check")
    void removeAll();

    @Query("DELETE from sign_check where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from sign_check")
    long getNextPrimaryId();
}
