package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface AssignmentLocationReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignmentLocationReportModel(AssignmentLocationReportModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAssignmentLocationReportModel(AssignmentLocationReportModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssignmentLocationReportModelList(List<AssignmentLocationReportModel> taskForm22500ModelList);

    @Query("select * from assignment_loc_report")
    List<AssignmentLocationReportModel> getAssignmentLocationReportModel();

    @Query("DELETE from assignment_loc_report")
    void removeAll();

    @Query("DELETE from assignment_loc_report where id=:id")
    void removeById(int id);
    @Query("select max(id) as max_id from assignment_loc_report")
    long getNextPrimaryId();
}
