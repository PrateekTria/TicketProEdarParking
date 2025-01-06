package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarOffStreetPatrolDropDown;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarOffStreetPatrolDropDownDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffStreetPatrolDropDown(DarOffStreetPatrolDropDown... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffStreetPatrolDropDown(DarOffStreetPatrolDropDown data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarOffStreetPatrolDropDown(List<DarOffStreetPatrolDropDown> darOffsiteDropdownElementsResultList);

    @Query("DELETE from dar_off_street_patrol")
    void removeAll();

    @Query("DELETE from dar_off_street_patrol where id=:id")
    void removeById(int id);


    @Query("select * from dar_off_street_patrol where custid=:custId")
    Maybe<List<DarOffStreetPatrolDropDown>> getDarOffStreetPatrolDropDown(int custId);
}
