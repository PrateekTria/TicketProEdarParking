package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.Mileage;
import com.ticketpro.parking.dar.model.School;

import java.util.List;

public class ParamMileage {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("isOverride")
    @Expose
    private String isOverride;
    @SerializedName("details")
    @Expose
    private List<Mileage> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<Mileage> getDetails() {
        return details;
    }

    public String getIsOverride() {
        return isOverride;
    }

    public void setIsOverride(String isOverride) {
        this.isOverride = isOverride;
    }

    public void setDetails(List<Mileage> details) {
        this.details = details;
    }
}

