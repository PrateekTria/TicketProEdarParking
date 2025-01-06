package com.ticketpro.parking.dar.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.model.DutyReport;

import java.util.List;

public class ParamsDutyRequestResponse {
    @SerializedName("chalkId")
    @Expose
    private Integer chalkId;
    @SerializedName("dutyReports")
    @Expose
    private List<DutyReport> dutyReports;

    public Integer getChalkId() {
        return chalkId;
    }

    public void setChalkId(Integer chalkId) {
        this.chalkId = chalkId;
    }

    public List<DutyReport> getDutyReports() {
        return dutyReports;
    }

    public void setDutyReports(List<DutyReport> dutyReports) {
        this.dutyReports = dutyReports;
    }

}