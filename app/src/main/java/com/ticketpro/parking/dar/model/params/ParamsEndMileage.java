package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.Mileage;

import java.util.List;
public class ParamsEndMileage {

    @SerializedName("id")
    @Expose
    private Integer custId;
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

    public void setDetails(List<Mileage> details) {
        this.details = details;
    }

}
