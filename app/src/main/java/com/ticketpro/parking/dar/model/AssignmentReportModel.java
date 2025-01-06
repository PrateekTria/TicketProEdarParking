package com.ticketpro.parking.dar.model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dao.ParkingDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Entity(tableName = "assignment_report")
public class AssignmentReportModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @ColumnInfo(name = "assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @ColumnInfo(name = "deviceid")
    @SerializedName("deviceid")
    @Expose
    private String deviceid;
    @ColumnInfo(name = "assignment_report_id")
    @SerializedName("assignment_report_id")
    @Expose
    private String assignmentreportId;
    @SerializedName("app_id")
    @Expose
    private Integer appId;
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentReportDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentReportDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentReportDao().removeById(id);
    }

    public static List<AssignmentReportModel> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentReportDao().getAssignmentReportModel();
    }

    @SuppressLint("CheckResult")
    public static void insertFieldContactDetails(@NotNull final AssignmentReportModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.assignmentReportDao().insertAssignmentReportModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i("Ticket End", "onComplete: " + (System.currentTimeMillis() - time));

            }

            @Override
            public void onError(@NonNull Throwable e) {
                // log.error(TPUtility.getPrintStackTrace(e));
            }
        });
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAssignmentreportId() {
        return assignmentreportId;
    }

    public void setAssignmentreportId(String assignmentreportId) {
        this.assignmentreportId = assignmentreportId;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
