package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.model.Body;
import com.ticketpro.parking.dar.model.Assignments;
import com.ticketpro.parking.dar.model.Equipments;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface AssignmentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignments(Assignments... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignments(Assignments data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignmentsList(List<Assignments> assignmentsList);

    @Query("DELETE from dar_assignment")
    void removeAll();

    @Query("DELETE from dar_assignment where id_asg=:id")
    void removeById(int id);


    @Query("select * from dar_assignment where custid=:custId")
    Maybe<List<Assignments>> getAssignment(int custId);
}
