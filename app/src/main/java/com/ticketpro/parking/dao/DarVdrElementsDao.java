package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.ticketpro.parking.dar.model.DarVdrElements;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface DarVdrElementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVdrElements(DarVdrElements... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVdrElements(DarVdrElements data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarVdrElementsList(List<DarVdrElements> darVehicleLists);

    @Query("DELETE from dar_vdr")
    void removeAll();

    @Query("DELETE from dar_vdr where id=:id")
    void removeById(int id);

    @Query("select * from dar_vdr where veh_task_id = :userid")
    Maybe<List<DarVdrElements>> getDarVdrElementsList(int userid);

    @Query("select * from dar_vdr order by order_number, name")
    Maybe<List<DarVdrElements>> getVdrList();

}
