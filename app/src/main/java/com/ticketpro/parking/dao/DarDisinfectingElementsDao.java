package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarDisinfectingElements;
import com.ticketpro.parking.dar.model.DarVehicleList;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarDisinfectingElementsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarDisinfectingElements(DarDisinfectingElements... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarDisinfectingElements(DarDisinfectingElements data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarDisinfectingElements(List<DarDisinfectingElements> darVehicleLists);

    @Query("DELETE from dar_disinfecting")
    void removeAll();

    @Query("DELETE from dar_disinfecting where id=:id")
    void removeById(int id);

    @Query("select * from dar_disinfecting where veh_task_id = :userid")
    Maybe<List<DarDisinfectingElements>> getDarDisinfectingElements(int userid);

    @Query("select * from dar_disinfecting order by order_number, name")
    Maybe<List<DarDisinfectingElements>> getDarDisinfectingList();
}
