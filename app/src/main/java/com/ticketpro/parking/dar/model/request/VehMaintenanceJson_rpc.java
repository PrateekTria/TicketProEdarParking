package com.ticketpro.parking.dar.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ticketpro.parking.dar.model.params.ParamSignCheck;
import com.ticketpro.parking.dar.model.params.ParamsVehMaintenance;

public class VehMaintenanceJson_rpc {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("params")
    @Expose
    private ParamsVehMaintenance params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ParamsVehMaintenance getParams() {
        return params;
    }

    public void setParams(ParamsVehMaintenance params) {
        this.params = params;
    }
}