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

@Entity(tableName = "task_report")
public class TaskReportModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    @Expose
    private Integer userid;

    @ColumnInfo(name = "assignment_loc_report_id")
    @SerializedName("assignment_loc_report_id")
    @Expose
    private String assignmentLocReportId;
    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String dartaskReportId;
    @ColumnInfo(name = "start_time")
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @ColumnInfo(name = "end_time")
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @ColumnInfo(name = "task_id")
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @ColumnInfo(name = "deviceid")
    @SerializedName("deviceid")
    @Expose
    private String deviceid;

    @ColumnInfo(name = "ass_location_report_id")
    @SerializedName("ass_location_report_id")
    @Expose
    private String asslocationReportId;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;
    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    private String locationId;

    @ColumnInfo(name = "assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;
    @ColumnInfo(name = "mileage")
    @SerializedName("mileage")
    @Expose
    private String mileage;

    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private String actionId;

    @ColumnInfo(name = "disinfecting")
    @SerializedName("disinfecting")
    @Expose
    private String disinfecting;

    @ColumnInfo(name = "equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;

    @ColumnInfo(name = "sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;

    @ColumnInfo(name = "vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

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
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).taskReportModelDao().getNextPrimaryId();
        return nextId + 1;
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).taskReportModelDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).taskReportModelDao().removeById(id);
    }

    public static List<TaskReportModel> _getList() throws Exception {
        return ParkingDatabase.getInstance(TPApplication.getInstance()).taskReportModelDao().getTaskReportModel();
    }

    @SuppressLint("CheckResult")
    public static void insertTaskReport(@NotNull final TaskReportModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.taskReportModelDao().insertTaskReportModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getDartaskReportId() {
        return dartaskReportId;
    }

    public void setDartaskReportId(String dartaskReportId) {
        this.dartaskReportId = dartaskReportId;
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

    public String getAssignmentLocReportId() {
        return assignmentLocReportId;
    }

    public void setAssignmentLocReportId(String assignmentLocReportId) {
        this.assignmentLocReportId = assignmentLocReportId;
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

    public String getAssLocationReportId() {
        return asslocationReportId;
    }

    public void setAssLocationReportId(String assLocationReportId) {
        this.asslocationReportId = assLocationReportId;
    }

    public String getAsslocationReportId() {
        return asslocationReportId;
    }

    public void setAsslocationReportId(String asslocationReportId) {
        this.asslocationReportId = asslocationReportId;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }
    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setDisinfecting(String disinfecting) {
        this.disinfecting = disinfecting;
    }

    public String getDisinfecting() {
        return disinfecting;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setSubEquipmentId(String subEquipmentId) {
        this.subEquipmentId = subEquipmentId;
    }

    public String getSubEquipmentId() {
        return subEquipmentId;
    }

    public void setVdr(String vdr) {
        this.vdr = vdr;
    }

    public String getVdr() {
        return vdr;
    }
}

