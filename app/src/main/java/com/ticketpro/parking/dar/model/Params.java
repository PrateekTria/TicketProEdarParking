package com.ticketpro.parking.dar.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("fullSync")
    @Expose
    private Boolean fullSync;
    @SerializedName("details")
    @Expose
    private List<TrafficDetails> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Boolean getFullSync() {
        return fullSync;
    }

    public void setFullSync(Boolean fullSync) {
        this.fullSync = fullSync;
    }

    public List<TrafficDetails> getDetails() {
        return details;
    }

    public void setDetails(List<TrafficDetails> details) {
        this.details = details;
    }

}
