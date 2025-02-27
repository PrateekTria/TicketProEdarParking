package com.ticketpro.parking.dar.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
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

@Entity(tableName = "admin")
public class Admin {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;

    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    @Expose
    private String userId;

    @ColumnInfo(name = "dd_item")
    @SerializedName("dd_item")
    @Expose
    private String ddItem;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;


    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    @Expose
    private String comment;

    @ColumnInfo(name = "task_id")
    @SerializedName("task_id")
    @Expose
    private String taskId;

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    private String locationId;

    @ColumnInfo(name = "task_date")
    @SerializedName("task_date")
    @Expose
    private String taskDate;

    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;

    @ColumnInfo(name = "device_id")
    @SerializedName("device_id")
    @Expose
    private String deviceid;


    @ColumnInfo(name = "assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;

    @ColumnInfo(name = "equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;

    @ColumnInfo(name = "vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

    @ColumnInfo(name = "disinfecting")
    @SerializedName("disinfecting")
    @Expose
    private String disinfecting;

    @ColumnInfo(name = "mileage")
    @SerializedName("mileage")
    @Expose
    private String mileage;

    @ColumnInfo(name = "vehicle")
    @SerializedName("vehicle")
    @Expose
    private String vehicle;

    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private String actionId;

    @ColumnInfo(name = "sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;
    @SerializedName("app_id")
    @Expose
    private Integer appId;

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    private String notes;

    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    private String GPSlocation;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }
    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).adminDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).adminDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).adminDao().removeById(id);
    }
    public static List<Admin> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).adminDao().getAdmin();
    }
  /*  public static void insertAdmin(Admin model) {
        new InsertAdmin(model).execute();
    }*/

    private static class InsertAdmin extends AsyncTask<Void,Void,Void> {
        private Admin model;
        public InsertAdmin(Admin model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).adminDao().insertAdmin(model);
            return null;
        }
    }
    @SuppressLint("CheckResult")
    public static void insertAdmin(@NotNull final Admin model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.adminDao().insertAdmin(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDdItem() {
        return ddItem;
    }

    public void setDdItem(String ddItem) {
        this.ddItem = ddItem;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getGPSlocation() {
        return GPSlocation;
    }

    public void setGPSlocation(String GPSlocation) {
        this.GPSlocation = GPSlocation;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getVdr() {
        return vdr;
    }

    public void setVdr(String vdr) {
        this.vdr = vdr;
    }
    public String getNotes()
    {
        return notes;
    }
    public void setNotes(String notes)
    {
        this.notes=notes;
    }

    public String getDisinfecting() {
        return disinfecting;
    }

    public void setDisinfecting(String disinfecting) {
        this.disinfecting = disinfecting;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String millage) {
        this.mileage = millage;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getSubEquipmentId() {
        return subEquipmentId;
    }

    public String getDarTaskReportId() {
        return DarTaskReportId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        DarTaskReportId = darTaskReportId;
    }

    public void setSubEquipmentId(String subEquipmentId) {
        this.subEquipmentId = subEquipmentId;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
