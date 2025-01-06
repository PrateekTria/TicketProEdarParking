package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.School;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface SchoolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchool(School... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertSchool(School data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSchoolList(List<School> taskForm22500ModelList);

    @Query("select * from school")
    List<School> getSchool();

    @Query("DELETE from school")
    void removeAll();

    @Query("DELETE from school where appId=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from school")
    long getNextPrimaryId();
}
