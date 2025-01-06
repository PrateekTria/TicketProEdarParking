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

@Entity(tableName = "school")
public class School {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private long id;
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    @Expose
    private String userId;

    @ColumnInfo(name = "schooldd_id")
    @SerializedName("schooldd_id")
    @Expose
    private String schoolddId;

    @ColumnInfo(name = "school_type")
    @SerializedName("school_type")
    @Expose
    private String schoolType;

    @ColumnInfo(name = "school_district")
    @SerializedName("school_district")
    @Expose
    private String schoolDistrict;

    @ColumnInfo(name = "council_district")
    @SerializedName("council_district")
    @Expose
    private String councilDistrict;

    @ColumnInfo(name = "dar_task_report_id")
    @SerializedName("dar_task_report_id")
    @Expose
    private String DarTaskReportId;


    @ColumnInfo(name = "school_address")
    @SerializedName("school_address")
    @Expose
    private String schoolAddress;

    @ColumnInfo(name = "contacts_principal")
    @SerializedName("contacts_principal")
    @Expose
    private String contactsPrincipal;

    @ColumnInfo(name = "school_name")
    @SerializedName("school_name")
    @Expose
    private String schoolName;

    @ColumnInfo(name = "contacts_staff")
    @SerializedName("contacts_staff")
    @Expose
    private String contactsStaff;

    @ColumnInfo(name = "mileage_id")
    @SerializedName("mileage_id")
    @Expose
    private String mileageId;

    @ColumnInfo(name = "issues_concerns")
    @SerializedName("issues_concerns")
    @Expose
    private String issuesConcerns;

    @ColumnInfo(name = "common_violations")
    @SerializedName("common_violations")
    @Expose
    private String commonViolations;

    @ColumnInfo(name = "speed_display_sign_deployed")
    @SerializedName("speed_display_sign_deployed")
    @Expose
    private String speedDisplaySignDeployed;

    @ColumnInfo(name = "reason")
    @SerializedName("reason")
    @Expose
    private String reason;

    @ColumnInfo(name = "number_of_citations_issued")
    @SerializedName("number_of_citations_issued")
    @Expose
    private String numberOfCitationsIssued;

    @ColumnInfo(name = "number_of_warnings_issued")
    @SerializedName("number_of_warnings_issued")
    @Expose
    private String numberOfWarningsIssued;

    @ColumnInfo(name = "drop_off_times")
    @SerializedName("drop_off_times")
    @Expose
    private String dropOffTimes;

    @ColumnInfo(name = "pick_up_time")
    @SerializedName("pick_up_time")
    @Expose
    private String pickUpTime;

    @ColumnInfo(name = "reason_for_no_school_visit")
    @SerializedName("reason_for_no_school_visit")
    @Expose
    private String reasonForNoSchoolVisit;

    @ColumnInfo(name = "date_time")
    @SerializedName("date_time")
    @Expose
    private String dateTime;

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
    private String assignmentId;

    @ColumnInfo(name = "equipment_id")
    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;

    @ColumnInfo(name = "vdr")
    @SerializedName("vdr")
    @Expose
    private String vdr;

    @ColumnInfo(name = "activity_id")
    @SerializedName("activity_id")
    @Expose
    private String activityId;

    @ColumnInfo(name = "mileage")
    @SerializedName("mileage")
    @Expose
    private String mileage ;

    @ColumnInfo(name = "vehicle")
    @SerializedName("vehicle")
    @Expose
    private String vehicle;

    @ColumnInfo(name = "device_id")
    @SerializedName("device_id")
    @Expose
    private String deviceid;

    @ColumnInfo(name = "action_id")
    @SerializedName("action_id")
    @Expose
    private String actionId;

    @ColumnInfo(name = "disinfecting")
    @SerializedName("disinfecting")
    @Expose
    private String disinfecting;

    @ColumnInfo(name = "sub_equipment_id")
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;
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
        nextId = ParkingDatabase.getInstance(TPApplication.getInstance()).schoolDao().getNextPrimaryId();
        return nextId + 1;
    }
    public static void removeAll() throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).schoolDao().removeAll();
    }

    public static void removeById(int id) throws Exception {
        ParkingDatabase.getInstance(TPApplication.getInstance()).schoolDao().removeById(id);
    }

    public static List<School> _getList() throws Exception{
        return ParkingDatabase.getInstance(TPApplication.getInstance()).schoolDao().getSchool();
    }

    @SuppressLint("CheckResult")
    public static void insertFieldContactDetails(@NotNull final School model) {

        final ParkingDatabase instance = ParkingDatabase.getInstance(TPApplication.getInstance());
        long time = System.currentTimeMillis();

        instance.schoolDao().insertSchool(model).subscribeOn(Schedulers.io()).subscribeWith(new CompletableObserver() {
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
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchoolddId() {
        return schoolddId;
    }

    public void setSchoolddId(String schoolddId) {
        this.schoolddId = schoolddId;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getCouncilDistrict() {
        return councilDistrict;
    }

    public void setCouncilDistrict(String councilDistrict) {
        this.councilDistrict = councilDistrict;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getContactsPrincipal() {
        return contactsPrincipal;
    }

    public void setContactsPrincipal(String contactsPrincipal) {
        this.contactsPrincipal = contactsPrincipal;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getContactsStaff() {
        return contactsStaff;
    }

    public void setContactsStaff(String contactsStaff) {
        this.contactsStaff = contactsStaff;
    }

    public String getIssuesConcerns() {
        return issuesConcerns;
    }

    public void setIssuesConcerns(String issuesConcerns) {
        this.issuesConcerns = issuesConcerns;
    }

    public String getCommonViolations() {
        return commonViolations;
    }

    public String getMileageId() {
        return mileageId;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public void setCommonViolations(String commonViolations) {
        this.commonViolations = commonViolations;
    }

    public String getSpeedDisplaySignDeployed() {
        return speedDisplaySignDeployed;
    }

    public void setSpeedDisplaySignDeployed(String speedDisplaySignDeployed) {
        this.speedDisplaySignDeployed = speedDisplaySignDeployed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNumberOfCitationsIssued() {
        return numberOfCitationsIssued;
    }

    public void setNumberOfCitationsIssued(String numberOfCitationsIssued) {
        this.numberOfCitationsIssued = numberOfCitationsIssued;
    }

    public String getNumberOfWarningsIssued() {
        return numberOfWarningsIssued;
    }

    public void setNumberOfWarningsIssued(String numberOfWarningsIssued) {
        this.numberOfWarningsIssued = numberOfWarningsIssued;
    }

    public String getDropOffTimes() {
        return dropOffTimes;
    }

    public void setDropOffTimes(String dropOffTimes) {
        this.dropOffTimes = dropOffTimes;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getReasonForNoSchoolVisit() {
        return reasonForNoSchoolVisit;
    }

    public void setReasonForNoSchoolVisit(String reasonForNoSchoolVisit) {
        this.reasonForNoSchoolVisit = reasonForNoSchoolVisit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getMileage() {
        return mileage ;
    }

    public void setMileage(String mileage ) {
        this.mileage  = mileage ;
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

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getDisinfecting() {
        return disinfecting;
    }

    public void setDisinfecting(String disinfecting) {
        this.disinfecting = disinfecting;
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
}
