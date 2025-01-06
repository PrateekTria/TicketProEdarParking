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

@Entity(tableName = "service_request")
public class ServiceRequestModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "task_id")
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    @Expose
    private String locationId;
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

    @ColumnInfo(name = "sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;
    @ColumnInfo(name = "vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

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
    @ColumnInfo(name = "serviceRequest_ddElemId")
    @SerializedName("serviceRequest_ddElemId")
    @Expose
    private String serviceRequestDdElemId;
    @ColumnInfo(name = "deviceid")
    @SerializedName("deviceid")
    @Expose
    private String deviceid;

    @ColumnInfo(name = "image_extension")
    @SerializedName("image_extension")
    @Expose
    private String imageExtension;

    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    private String location;

    @ColumnInfo(name = "image_name")
    @SerializedName("image_name")
    @Expose
    private String imageName;

    @ColumnInfo(name = "image_path")
    @SerializedName("image_path")
    @Expose
    private String imagePath;

    @ColumnInfo(name = "image_resolution")
    @SerializedName("image_resolution")
    @Expose
    private String imageResolution;

    @ColumnInfo(name = "image_size")
    @SerializedName("image_size")
    @Expose
    private String imageSize;


    @SerializedName("app_id")
    @Expose
    private Integer appId;

    @ColumnInfo(name = "notes")
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public static List<ServiceRequestModel> _getList() throws Exception {
        return ParkingDatabase.getInstance(TPApplication.getInstance()).serviceRequestModelDao().getServiceRequestModel();
    }

    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).serviceRequestModelDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).serviceRequestModelDao().removeById(id);
    }

    public static long getNextPrimaryId() {
        long nextId = 0;
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).serviceRequestModelDao().getNextPrimaryId();
        return nextId + 1;
    }

    /* public static void insertServiceRequestModel(ServiceRequestModel model) {
         new InsertServiceRequestModel(model).execute();
     }
 */
    private static class InsertServiceRequestModel extends AsyncTask<Void, Void, Void> {
        private ServiceRequestModel model;

        public InsertServiceRequestModel(ServiceRequestModel model) {
            this.model = model;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingDatabase.getInstance(TPApplication.getInstance()).serviceRequestModelDao().insertServiceRequestModel(model);
            return null;
        }
    }

    @SuppressLint("CheckResult")
    public static void insertServiceRequestModel(ServiceRequestModel model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.serviceRequestModelDao().insertServiceRequestModel(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
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
    public String getNotes()
    {
        return notes;
    }
    public void setNotes(String notes)
    {
        this.notes=notes;
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

    public String getServiceRequestDdElemId() {
        return serviceRequestDdElemId;
    }

    public void setServiceRequestDdElemId(String serviceRequestDdElemId) {
        this.serviceRequestDdElemId = serviceRequestDdElemId;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageResolution() {
        return imageResolution;
    }

    public void setImageResolution(String imageResolution) {
        this.imageResolution = imageResolution;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getDarTaskReportId() {
        return DarTaskReportId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        DarTaskReportId = darTaskReportId;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }
}
