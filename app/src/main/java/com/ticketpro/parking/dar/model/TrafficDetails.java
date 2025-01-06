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

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Entity(tableName = "traffic_details")
public class TrafficDetails {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    @Expose
    private String userId;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;
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
    @ColumnInfo(name = "nonInforcement_dd_elementId")
    @SerializedName("nonInforcement_dd_elementId")
    @Expose
    private String nonInforcementDdElementId;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo(name = "badge")
    @SerializedName("badge")
    @Expose
    private String badge;
    @ColumnInfo(name = "datetime")
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @ColumnInfo(name = "event_name")
    @SerializedName("event_name")
    @Expose
    private String eventName;

    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;

    @ColumnInfo(name = "start_shift")
    @SerializedName("start_shift")
    @Expose
    private String startShift;
    @ColumnInfo(name = "release_post_time")
    @SerializedName("release_post_time")
    @Expose
    private String releasePostTime;
    @ColumnInfo(name = "end_shift")
    @SerializedName("end_shift")
    @Expose
    private String endShift;
    @ColumnInfo(name = "deviceid")
    @SerializedName("deviceid")
    @Expose
    private String deviceid;
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
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).trafficDetailsDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static List<TrafficDetails> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).trafficDetailsDao().getTrafficDetails();
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).trafficDetailsDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).trafficDetailsDao().removeById(id);
    }

   /* public static void insertTrafficDetails(TrafficDetails model) {
        new InsertTrafficDetails(model).execute();
    }*/

    private static class InsertTrafficDetails extends AsyncTask<Void,Void,Void> {
        private TrafficDetails model;
        public InsertTrafficDetails(TrafficDetails model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).trafficDetailsDao().insertTrafficDetails(model);
            return null;
        }
    }
    @SuppressLint("CheckResult")
    public static void insertTrafficDetails(TrafficDetails model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.trafficDetailsDao().insertTrafficDetails(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getNonInforcementDdElementId() {
        return nonInforcementDdElementId;
    }

    public void setNonInforcementDdElementId(String nonInforcementDdElementId) {
        this.nonInforcementDdElementId = nonInforcementDdElementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartShift() {
        return startShift;
    }

    public void setStartShift(String startShift) {
        this.startShift = startShift;
    }

    public String getReleasePostTime() {
        return releasePostTime;
    }

    public void setReleasePostTime(String releasePostTime) {
        this.releasePostTime = releasePostTime;
    }

    public String getEndShift() {
        return endShift;
    }

    public void setEndShift(String endShift) {
        this.endShift = endShift;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public String getDarTaskReportId() {
        return DarTaskReportId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        DarTaskReportId = darTaskReportId;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}