package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.OffStreetModel;

import java.util.List;

public class ParamOffStreet {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<OffStreetModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<OffStreetModel> getDetails() {
        return details;
    }

    public void setDetails(List<OffStreetModel> details) {
        this.details = details;
    }
}
