package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.ServiceRequestModel;

import java.util.List;

public class ParamServiceRequest {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<ServiceRequestModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<ServiceRequestModel> getDetails() {
        return details;
    }

    public void setDetails(List<ServiceRequestModel> details) {
        this.details = details;
    }
}
