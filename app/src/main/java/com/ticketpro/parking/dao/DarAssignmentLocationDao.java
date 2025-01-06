package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.DarAssignmentLocation;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface DarAssignmentLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarAssignmentLocation(DarAssignmentLocation... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarAssignmentLocation(DarAssignmentLocation data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDarAssignmentLocationList(List<DarAssignmentLocation> darAssignmentLocationList);

    @Query("DELETE from dar_assignment_location")
    void removeAll();

    @Query("DELETE from dar_assignment_location where id_assg_loc=:id")
    void removeById(int id);

    @Query("select * from dar_assignment_location where id_asg=:id")
    Maybe<List<DarAssignmentLocation>> getDarAssignmentLocationByTitle(int id);
}
