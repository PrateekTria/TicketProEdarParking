package com.ticketpro.parking.dar.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.model.Ticket;
import com.ticketpro.parking.activity.TPApplication;
import com.ticketpro.parking.dao.ParkingDatabase;
import com.ticketpro.util.StringUtil;
import com.ticketpro.util.TPUtility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Entity(tableName = "form_22500")
public class TaskForm22500Model {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    @Expose
    private String userId;

    @ColumnInfo(name = "task_id")
    @SerializedName("task_id")
    @Expose
    private String taskId;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;


    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;


    @ColumnInfo(name = "task_date")
    @SerializedName("task_date")
    @Expose
    private String taskDate;

    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    private String locationId;

    @ColumnInfo(name = "assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;

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

    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    private String location;

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

    @ColumnInfo(name = "pdEvent")
    @SerializedName("pdEvent")
    @Expose
    private String pdEvent;

    @ColumnInfo(name = "disposition_ddElemId")
    @SerializedName("disposition_ddElemId")
    @Expose
    private Integer dispositionDdElemId;

    @ColumnInfo(name = "activity_id")
    @SerializedName("activity_id")
    @Expose
    private String activityId;


    @ColumnInfo(name = "device_id")
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @ColumnInfo(name = "customerContact")
    @SerializedName("customerContact")
    @Expose
    private String customerContact;

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    private String notes;

    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private String actionId;

    @SerializedName("app_id")
    @Expose
    private Integer appId;
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).taskForm22500ModelDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).taskForm22500ModelDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).taskForm22500ModelDao().removeById(id);
    }

    public static List<TaskForm22500Model> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).taskForm22500ModelDao().getTaskForm22500Model();
    }

    public static void insertTaskForm22500Model(TaskForm22500Model model) {
        new InsertTaskForm22500Model(model).execute();
    }

    private static class InsertTaskForm22500Model extends AsyncTask<Void,Void,Void> {
        private TaskForm22500Model model;
        public InsertTaskForm22500Model(TaskForm22500Model model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());

            instance.taskForm22500ModelDao().insertTaskForm22500Model(model);
            return null;
        }
    }

    @SuppressLint("CheckResult")
    public static void insertTaskForm22500(@NotNull final TaskForm22500Model model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.taskForm22500ModelDao().insertTaskForm22500Model(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.i("Ticket End", "onComplete: " + (System.currentTimeMillis() - time));

            }

            @Override
            public void onError(@NonNull Throwable e) {
            //    log.error(TPUtility.getPrintStackTrace(e));
            }
        });
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPdEvent() {
        return pdEvent;
    }

    public void setPdEvent(String pdEvent) {
        this.pdEvent = pdEvent;
    }

    public Integer getDispositionDdElemId() {
        return dispositionDdElemId;
    }

    public void setDispositionDdElemId(Integer dispositionDdElemId) {
        this.dispositionDdElemId = dispositionDdElemId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getNotes() {
        return notes;
    }
    public String getDarTaskReportId() {
        return DarTaskReportId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        DarTaskReportId = darTaskReportId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSubEquipmentId() {
        return subEquipmentId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
