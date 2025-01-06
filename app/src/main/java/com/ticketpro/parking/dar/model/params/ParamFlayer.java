package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.FlayerModel;

import java.util.List;

public class ParamFlayer {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<FlayerModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<FlayerModel> getDetails() {
        return details;
    }

    public void setDetails(List<FlayerModel> details) {
        this.details = details;
    }
}
