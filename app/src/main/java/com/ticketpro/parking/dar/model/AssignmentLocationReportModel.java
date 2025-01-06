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

@Entity(tableName = "assignment_loc_report")
public class AssignmentLocationReportModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userid")
    @SerializedName("userid")
    @Expose
    private Integer userid;

    @ColumnInfo(name = "assignment_report_id")
    @SerializedName("assignment_report_id")
    @Expose
    private String assignmentReportId;

    @ColumnInfo(name = "ass_report_id")
    @SerializedName("ass_report_id")
    @Expose
    private String assReportId;


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

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    private String locationId;

    @ColumnInfo(name = "assignment_loc_id")
    @SerializedName("assignment_loc_id")
    @Expose
    private String assignmentLocId;



    @ColumnInfo(name = "ass_location_report_id")
    @SerializedName("ass_location_report_id")
    @Expose
    private String AssLocationReportId;

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
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentLocationReportDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentLocationReportDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentLocationReportDao().removeById(id);
    }

    public static List<AssignmentLocationReportModel> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).assignmentLocationReportDao().getAssignmentLocationReportModel();
    }

    @SuppressLint("CheckResult")
    public static void insertFieldContactDetails(@NotNull final AssignmentLocationReportModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.assignmentLocationReportDao().insertAssignmentLocationReportModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getAssReportId() {
        return assReportId;
    }

    public void setAssReportId(String assReportId) {
        this.assReportId = assReportId;
    }


    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public String getAssLocationReportId() {
        return AssLocationReportId;
    }

    public void setAssLocationReportId(String assLocationReportId) {
        AssLocationReportId = assLocationReportId;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAssignmentReportId() {
        return assignmentReportId;
    }

    public void setAssignmentReportId(String assignmentReportId) {
        this.assignmentReportId = assignmentReportId;
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

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAssignmentLocId() {
        return assignmentLocId;
    }

    public void setAssignmentLocId(String assignmentLocId) {
        this.assignmentLocId = assignmentLocId;
    }
}
