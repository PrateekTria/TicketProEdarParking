package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.CommunityModel;

import java.util.List;

public class ParamsCommunity {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<CommunityModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<CommunityModel> getDetails() {
        return details;
    }

    public void setDetails(List<CommunityModel> details) {
        this.details = details;
    }

}
