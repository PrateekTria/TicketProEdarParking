package com.ticketpro.parking.syncbackup.syncmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Params {

    @SerializedName("custid")
    @Expose
    private Integer custid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("deviceid")
    @Expose
    private String deviceid;
    @SerializedName("access_token")
    @Expose

    private String accessToken;

    @SerializedName("module")
    @Expose
    String module;

    @SerializedName("debuginfo")
    @Expose
    private List<Debuginfo> debuginfos;

    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Debuginfo> getCsvinfo() {
        return debuginfos;
    }

    public void setCsvinfo(List<Debuginfo> csvinfo) {
        this.debuginfos = csvinfo;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}