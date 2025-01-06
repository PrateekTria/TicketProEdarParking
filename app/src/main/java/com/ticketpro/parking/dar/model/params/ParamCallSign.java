package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.CallSignModel;

import java.util.List;

public class ParamCallSign {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<CallSignModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<CallSignModel> getDetails() {
        return details;
    }

    public void setDetails(List<CallSignModel> details) {
        this.details = details;
    }

}
