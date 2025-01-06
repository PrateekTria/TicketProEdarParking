package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.TrainingModel;

import java.util.List;

public class ParamTraining {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<TrainingModel> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<TrainingModel> getDetails() {
        return details;
    }

    public void setDetails(List<TrainingModel> details) {
        this.details = details;
    }
}
