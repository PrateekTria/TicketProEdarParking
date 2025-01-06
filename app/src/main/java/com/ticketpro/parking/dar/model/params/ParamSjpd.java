package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.SJPDModel;

import java.util.List;

public class ParamSjpd {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("deviceId")
    @Expose
    private Integer deviceId;
    @SerializedName("details")
    @Expose
    private List<SJPDModel> details;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public List<SJPDModel> getDetails() {
        return details;
    }

    public void setDetails(List<SJPDModel> details) {
        this.details = details;
    }
}
