package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SJPDModel {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("logout")
    @Expose
    private String logout;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("dar_call_signin_id")
    @Expose
    private String dar_call_signin_id;
    @SerializedName("disinfecting")
    @Expose
    private String disinfecting;
    @SerializedName("vdr")
    @Expose
    private String vdr;

    @SerializedName("equipment_id")
    @Expose
    private String equipmentId;
    @SerializedName("sub_equipment_id")
    @Expose
    private String subEquipmentId;

    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;

    @SerializedName("location_id")
    @Expose
    private String locationId;

    @SerializedName("mileage_id")
    @Expose
    private String mileageId;

    @SerializedName("mileage")
    @Expose
    private String mileage;

    @SerializedName("action_id")
    @Expose
    private String actionId;

    @SerializedName("task_id")
    @Expose
    private String taskId;

    @SerializedName("dar_task_report_id")
    @Expose
    private String darTaskReportId;

    @SerializedName("shift_start_time")
    @Expose
    private String shiftStTime;
    @SerializedName("shift_end_time")
    @Expose
    private String shiftEndTime;
    @SerializedName("shift_type")
    @Expose
    private String shiftType;


    @SerializedName("notes")
    @Expose
    private String notes;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisinfecting() {
        return disinfecting;
    }

    public void setDisinfecting(String disinfecting) {
        this.disinfecting = disinfecting;
    }

    public String getVdr() {
        return vdr;
    }

    public String getShiftType() {
        return shiftType;
    }

    public String getShiftEndTime() {
        return shiftEndTime;
    }

    public String getShiftStTime() {
        return shiftStTime;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public void setShiftEndTime(String shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public void setShiftStTime(String shiftStTime) {
        this.shiftStTime = shiftStTime;
    }

    public void setVdr(String vdr) {
        this.vdr = vdr;
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

    public String getDar_call_signin_id() {
        return dar_call_signin_id;
    }

    public void setDar_call_signin_id(String dar_call_signin_id) {
        this.dar_call_signin_id = dar_call_signin_id;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public void setMileageId(String mileageId) {
        this.mileageId = mileageId;
    }

    public String getMileageId() {
        return mileageId;
    }

    public String getDarTaskReportId() {
        return darTaskReportId;
    }

    public void setDarTaskReportId(String darTaskReportId) {
        this.darTaskReportId = darTaskReportId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}


