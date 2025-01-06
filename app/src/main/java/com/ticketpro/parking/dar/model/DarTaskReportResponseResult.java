package com.ticketpro.parking.dar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DarTaskReportResponseResult {

    @SerializedName("app_id")
    @Expose
    private List<Integer> appId = null;
    @SerializedName("dar_task_report_id")
    @Expose
    private List<String> darTaskReportId = null;
    @SerializedName("result")
    @Expose
    private Boolean result;

    public List<Integer> getAppId() {
        return appId;
    }

    public void setAppId(List<Integer> appId) {
        this.appId = appId;
    }

    public List<String> getDarTaskReportId() {
        return darTaskReportId;
    }

    public void setDarTaskReportId(List<String> darTaskReportId) {
        this.darTaskReportId = darTaskReportId;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}