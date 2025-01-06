package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.LunchBreakModel;

import java.util.List;

public class ParamLunchBreak {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<LunchBreakModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<LunchBreakModel> getDetails() {
        return details;
    }

    public void setDetails(List<LunchBreakModel> details) {
        this.details = details;
    }
}
