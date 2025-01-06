package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.Admin;
import java.util.List;

import io.reactivex.Completable;

@Dao
public interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdmin(Admin... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAdmin(Admin data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdminList(List<Admin> taskForm22500ModelList);

    @Query("select * from admin")
    List<Admin> getAdmin();

    @Query("DELETE from admin")
    void removeAll();

    @Query("DELETE from admin where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from admin")
    long getNextPrimaryId();
}
