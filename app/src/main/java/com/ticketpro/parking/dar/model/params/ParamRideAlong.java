package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.RideAlongModel;

import java.util.List;

public class ParamRideAlong {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<RideAlongModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<RideAlongModel> getDetails() {
        return details;
    }

    public void setDetails(List<RideAlongModel> details) {
        this.details = details;
    }

}
