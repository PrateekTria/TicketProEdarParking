package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.SignCheckModel;
import com.ticketpro.parking.dar.model.VehMaintenanceModel;

import java.util.List;

public class ParamsVehMaintenance {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<VehMaintenanceModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<VehMaintenanceModel> getDetails() {
        return details;
    }

    public void setDetails(List<VehMaintenanceModel> details) {
        this.details = details;
    }
}

