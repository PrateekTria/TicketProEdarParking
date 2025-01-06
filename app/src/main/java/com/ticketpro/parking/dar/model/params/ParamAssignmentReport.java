package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.AssignmentReportModel;

import java.util.List;

public class ParamAssignmentReport {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<AssignmentReportModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<AssignmentReportModel> getDetails() {
        return details;
    }

    public void setDetails(List<AssignmentReportModel> details) {
        this.details = details;
    }

}
