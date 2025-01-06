package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DarinsertMileageRequestResult {
    @SerializedName("app_id")
    @Expose
    private List<String> appId = null;
    @SerializedName("end_mileage")
    @Expose
    private List<String> endMileage = null;
    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("status")
    @Expose
    private int status;

    public List<String> getAppId() {
        return appId;
    }

    public void setAppId(List<String> appId) {
        this.appId = appId;
    }

    public List<String> getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(List<String> endMileage) {
        this.endMileage = endMileage;
    }

    public Boolean getResult() {
        return result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}