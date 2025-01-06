package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.FieldContactDetails;

import java.util.List;

public class ParamFieldContact {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<FieldContactDetails> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<FieldContactDetails> getDetails() {
        return details;
    }

    public void setDetails(List<FieldContactDetails> details) {
        this.details = details;
    }
}
