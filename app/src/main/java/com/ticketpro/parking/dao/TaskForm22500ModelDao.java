package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.TaskForm22500Model;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface TaskForm22500ModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskForm22500Model(TaskForm22500Model... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTaskForm22500Model(TaskForm22500Model data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskForm22500ModelList(List<TaskForm22500Model> taskForm22500ModelList);

    @Query("select * from form_22500")
    List<TaskForm22500Model> getTaskForm22500Model();

    @Query("DELETE from form_22500")
    void removeAll();

    @Query("DELETE from form_22500 where appId=:id")
    void removeById(int id);
    @Query("select max(id) as max_id from form_22500")
    long getNextPrimaryId();
}
