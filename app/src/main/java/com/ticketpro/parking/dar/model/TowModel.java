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

@Entity(tableName = "tow")
public class TowModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name ="userId")
    @SerializedName("userId")
    @Expose
    private String userId;

    @ColumnInfo(name ="assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;

    @ColumnInfo(name ="equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;

    @ColumnInfo(name ="sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;

    @ColumnInfo(name ="task_id")
    @SerializedName("task_id")
    @Expose
    private String taskId;

    @ColumnInfo(name ="vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

    @ColumnInfo(name ="disinfecting")
    @SerializedName("disinfecting")
    @Expose
    private String disinfecting;

    @ColumnInfo(name ="mileage")
    @SerializedName("mileage")
    @Expose
    private String mileage;

    @ColumnInfo(name ="vehicle")
    @SerializedName("vehicle")
    @Expose
    private String vehicle;

    @ColumnInfo(name ="deviceid")
    @SerializedName("deviceid")
    @Expose
    private String deviceid;

    @ColumnInfo(name ="pd_event")
    @SerializedName("pd_event")
    @Expose
    private String pdEvent;

    @ColumnInfo(name ="tow_company_ddElemId")
    @SerializedName("tow_company_ddElemId")
    @Expose
    private String towCompanyDdElemId;

    @ColumnInfo(name ="tow_authority_ddElemId")
    @SerializedName("tow_authority_ddElemId")
    @Expose
    private String towAuthorityDdElemId;

    @ColumnInfo(name ="date_time")
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    @ColumnInfo(name ="dispatch_time")
    @SerializedName("dispatch_time")
    @Expose
    private String dispatchTime;

    @ColumnInfo(name ="tow_arrival")
    @SerializedName("tow_arrival")
    @Expose
    private String towArrival;

    @ColumnInfo(name ="location")
    @SerializedName("location")
    @Expose
    private String location;

    @ColumnInfo(name ="vehicle_licence_plate")
    @SerializedName("vehicle_licence_plate")
    @Expose
    private String vehicleLicencePlate;

    @ColumnInfo(name = "tow_refused_ddElemId")
    @SerializedName("tow_refused_ddElemId")
    @Expose
    private String towRefusedDdElemId;

    @ColumnInfo(name = "comments")
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("app_id")
    @Expose
    private Integer appId;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).towModelDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).towModelDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).towModelDao().removeById(id);
    }

    public static List<TowModel> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).towModelDao().getTowModel();
    }

    public static void insertTowModel(TowModel model) {
        new InsertTowModel(model).execute();
    }

    private static class InsertTowModel extends AsyncTask<Void,Void,Void> {
        private TowModel model;
        public InsertTowModel(TowModel model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
            instance.towModelDao().insertTowModel(model);
            return null;
        }
    }

    @SuppressLint("CheckResult")
    public static void insertTowModelTask(@NotNull final TowModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.towModelDao().insertTowModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getSubEquipmentId() {
        return subEquipmentId;
    }

    public void setSubEquipmentId(String subEquipmentId) {
        this.subEquipmentId = subEquipmentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getVdr() {
        return vdr;
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

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPdEvent() {
        return pdEvent;
    }

    public void setPdEvent(String pdEvent) {
        this.pdEvent = pdEvent;
    }

    public String getTowCompanyDdElemId() {
        return towCompanyDdElemId;
    }

    public void setTowCompanyDdElemId(String towCompanyDdElemId) {
        this.towCompanyDdElemId = towCompanyDdElemId;
    }
    public String getTowAuthorityDdElemId()
    {
        return  towAuthorityDdElemId;
    }
    public void setTowAuthorityDdElemId(String towAuthorityDdElemId){
        this.towAuthorityDdElemId=towAuthorityDdElemId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getTowArrival() {
        return towArrival;
    }

    public void setTowArrival(String towArrival) {
        this.towArrival = towArrival;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVehicleLicencePlate() {
        return vehicleLicencePlate;
    }

    public void setVehicleLicencePlate(String vehicleLicencePlate) {
        this.vehicleLicencePlate = vehicleLicencePlate;
    }

    public String getTowRefusedDdElemId() {
        return towRefusedDdElemId;
    }

    public void setTowRefusedDdElemId(String towRefusedDdElemId) {
        this.towRefusedDdElemId = towRefusedDdElemId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
