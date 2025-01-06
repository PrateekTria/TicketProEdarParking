package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarLocationActivityTask;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarLocationActivityTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarLocationActivityTask(DarLocationActivityTask... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEquipment(DarLocationActivityTask data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarLocationActivityTaskList(List<DarLocationActivityTask> darLocationActivityTaskList);

    @Query("DELETE from dar_location_task")
    void removeAll();

    @Query("DELETE from dar_location_task where id=:id")
    void removeById(int id);

    @Query("select * from dar_location_task where id_assg=:id")
    Maybe<List<DarLocationActivityTask>> getDarLocationActivityTask(int id);
}
