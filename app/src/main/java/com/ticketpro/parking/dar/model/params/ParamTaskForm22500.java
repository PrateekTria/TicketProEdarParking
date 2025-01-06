package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.TaskForm22500Model;

import java.util.List;

public class ParamTaskForm22500 {
    @SerializedName("custId")
    @Expose
    private Integer custId;
    @SerializedName("details")
    @Expose
    private List<TaskForm22500Model> details = null;

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public List<TaskForm22500Model> getDetails() {
        return details;
    }

    public void setDetails(List<TaskForm22500Model> details) {
        this.details = details;
    }
}
