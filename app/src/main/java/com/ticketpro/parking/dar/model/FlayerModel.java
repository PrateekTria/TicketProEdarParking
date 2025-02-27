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

@Entity(tableName = "flayer")
public class FlayerModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "userId")
    @SerializedName("userId")
    @Expose
    private String userId;
    @ColumnInfo(name = "assignment_id")
    @SerializedName("assignment_id")
    @Expose
    private Integer assignmentId;
    @ColumnInfo(name = "equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;
    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;
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
    private String mileage ;
    @ColumnInfo(name = "vehicle")
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @ColumnInfo(name = "nonInforcement_dd_elementId")
    @SerializedName("nonInforcement_dd_elementId")
    @Expose
    private String nonInforcementDdElementId;
    @ColumnInfo(name = "datetime")
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    @Expose
    private String comment;
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
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).flayerModelDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static List<FlayerModel> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).flayerModelDao().getFlayerModel();
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).flayerModelDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).flayerModelDao().removeById(id);
    }

   /* public static void insertFlayerModel(FlayerModel model) {
        new InsertFlayerModel(model).execute();
    }
*/
    private static class InsertFlayerModel extends AsyncTask<Void,Void,Void> {
        private FlayerModel model;
        public InsertFlayerModel(FlayerModel model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).flayerModelDao().insertFlayerModel(model);
            return null;
        }
    }
    @SuppressLint("CheckResult")
    public static void insertFlayerModel(FlayerModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.flayerModelDao().insertFlayerModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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
        return mileage ;
    }

    public void setMileage(String millage) {
        this.mileage  = millage;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

}
