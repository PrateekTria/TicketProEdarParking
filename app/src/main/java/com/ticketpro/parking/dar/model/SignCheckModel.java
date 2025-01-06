package com.ticketpro.parking.dar.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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

@Entity(tableName = "sign_check")
public class SignCheckModel {
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
    @ColumnInfo(name = "vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;

    @ColumnInfo(name = "task_date")
    @SerializedName("task_date")
    @Expose
    private String taskDate;

    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;

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
    @ColumnInfo(name = "signcheck_ddElemId")
    @SerializedName("signcheck_ddElemId")
    @Expose
    private String signcheckDdElemId;
    @ColumnInfo(name = "permit_number")
    @SerializedName("permit_number")
    @Expose
    private String permitNumber;
    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    private String location;
    @ColumnInfo(name = "enforceable_input")
    @SerializedName("enforceable_input")
    @Expose
    private String enforceableInput;
    @ColumnInfo(name = "device_id")
    @SerializedName("device_id")
    @Expose
    private String deviceid;
    @ColumnInfo(name = "activity_id")
    @SerializedName("activity_id")
    @Expose
    private String activityId;
    @ColumnInfo(name = "sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;
    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private String actionId;
    @SerializedName("app_id")
    @Expose
    private Integer appId;
    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    private String notes;

    @ColumnInfo(name = "forceable_type")
    @SerializedName("forceable_type")
    @Expose
    private String forceable;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).signCheckModelDAo().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).signCheckModelDAo().removeById(id);
    }
    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).signCheckModelDAo().getNextPrimaryId();
        return nextId + 1;
    }
    public static List<SignCheckModel> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).signCheckModelDAo().getSignCheckModel();
    }

   /* public static void insertSignCheckModel(SignCheckModel model) {
        new InsertSignCheckModel(model).execute();
    }
*/
    private static class InsertSignCheckModel extends AsyncTask<Void,Void,Void> {
        private SignCheckModel model;
        public InsertSignCheckModel(SignCheckModel model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).signCheckModelDAo().insertSignCheckModel(model);
            return null;
        }
    }
    @SuppressLint("CheckResult")
    public static void insertSignCheckModel(SignCheckModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.signCheckModelDAo().insertSignCheckModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public void setVdr(String vdr) {
        this.vdr = vdr;
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
    public String getNotes(){
        return  notes;
    }
    public void setNotes(String notes)
    {
        this.notes=notes;
    }
    public String getForceable()
    {
        return  forceable;
    }
    public void setForceable(String forceable)
    {
              this.forceable=forceable;
    }

    public String getSigncheckDdElemId() {
        return signcheckDdElemId;
    }

    public void setSigncheckDdElemId(String signcheckDdElemId) {
        this.signcheckDdElemId = signcheckDdElemId;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEnforceableInput() {
        return enforceableInput;
    }

    public void setEnforceableInput(String enforceableInput) {
        this.enforceableInput = enforceableInput;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getSubEquipmentId() {
        return subEquipmentId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        DarTaskReportId = darTaskReportId;
    }

    public String getDarTaskReportId() {
        return DarTaskReportId;
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
}
