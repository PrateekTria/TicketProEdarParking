package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.TaskReportModel;

import java.util.List;

public class ParamTaskReport {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<TaskReportModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<TaskReportModel> getDetails() {
        return details;
    }

    public void setDetails(List<TaskReportModel> details) {
        this.details = details;
    }
}
