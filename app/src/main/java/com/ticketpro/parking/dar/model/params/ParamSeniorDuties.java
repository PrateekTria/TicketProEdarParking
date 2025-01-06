package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.SeniorDutiesModel;

import java.util.List;

public class ParamSeniorDuties {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<SeniorDutiesModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<SeniorDutiesModel> getDetails() {
        return details;
    }

    public void setDetails(List<SeniorDutiesModel> details) {
        this.details = details;
    }
}
