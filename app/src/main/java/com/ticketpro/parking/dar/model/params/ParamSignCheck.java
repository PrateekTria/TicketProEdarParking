package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.SignCheckModel;

import java.util.List;

public class ParamSignCheck {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<SignCheckModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<SignCheckModel> getDetails() {
        return details;
    }

    public void setDetails(List<SignCheckModel> details) {
        this.details = details;
    }
}
