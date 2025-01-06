package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.Admin;

import java.util.List;

public class ParamAdmin {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("fullSync")
    @Expose
    private Boolean fullSync;
    @SerializedName("details")
    @Expose
    private List<Admin> details = null;

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

    public List<Admin> getDetails() {
        return details;
    }

    public void setDetails(List<Admin> details) {
        this.details = details;
    }

}
