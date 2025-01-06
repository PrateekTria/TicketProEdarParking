package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.AssignmentLocationReportModel;

import java.util.List;

public class ParamAssignmentLocationReport {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<AssignmentLocationReportModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<AssignmentLocationReportModel> getDetails() {
        return details;
    }

    public void setDetails(List<AssignmentLocationReportModel> details) {
        this.details = details;
    }

}
