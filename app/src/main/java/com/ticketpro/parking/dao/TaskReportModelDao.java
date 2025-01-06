package com.ticketpro.parking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ticketpro.parking.dar.model.TaskReportModel;

import java.util.List;

import io.reactivex.Completable;
@Dao
public interface TaskReportModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskReportModel(TaskReportModel... data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTaskReportModel(TaskReportModel data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskReportModelList(List<TaskReportModel> taskForm22500ModelList);

    @Query("select * from task_report")
    List<TaskReportModel> getTaskReportModel();

    @Query("DELETE from task_report")
    void removeAll();

    @Query("DELETE from task_report where id=:id")
    void removeById(int id);

    @Query("select max(id) as max_id from task_report")
    long getNextPrimaryId();
}
