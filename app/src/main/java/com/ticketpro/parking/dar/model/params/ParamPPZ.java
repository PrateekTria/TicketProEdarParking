package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.PPZModel;

import java.util.List;

public class ParamPPZ {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<PPZModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<PPZModel> getDetails() {
        return details;
    }

    public void setDetails(List<PPZModel> details) {
        this.details = details;
    }
}
