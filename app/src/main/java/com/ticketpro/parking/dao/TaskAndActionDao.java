package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.ticketpro.parking.dar.model.TaskAndAction;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface TaskAndActionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskAndAction(TaskAndAction... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskAndAction(TaskAndAction data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskAndActionList(List<TaskAndAction> taskAndActionList);

    @Query("DELETE from dar_location_task")
    void removeAll();

    @Query("DELETE from dar_location_task where id=:id")
    void removeById(int id);

    /*@Query("select * from dar_location_task")
    Maybe<TaskAndAction> getTaskAndLocation();*/
}
