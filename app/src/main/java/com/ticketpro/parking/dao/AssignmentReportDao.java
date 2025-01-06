package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.AssignmentReportModel;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface AssignmentReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignmentReportModel(AssignmentReportModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAssignmentReportModel(AssignmentReportModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignmentReportModelList(List<AssignmentReportModel> taskForm22500ModelList);

    @Query("select * from assignment_report")
    List<AssignmentReportModel> getAssignmentReportModel();

    @Query("DELETE from assignment_report")
    void removeAll();

    @Query("DELETE from assignment_report where id=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from assignment_report")
    long getNextPrimaryId();
}
