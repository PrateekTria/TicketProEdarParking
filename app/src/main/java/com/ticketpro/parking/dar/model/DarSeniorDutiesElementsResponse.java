package com.ticketpro.parking.dar.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DarSeniorDutiesElementsResponse {

    @SerializedName("result")
    @Expose
    private List<DarSeniorDutiesElements> darSeniorDutiesElements = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;

    public List<DarSeniorDutiesElements> getDarSeniorDutiesElements() {
        return darSeniorDutiesElements;
    }

    public void setDarSeniorDutiesElements(List<DarSeniorDutiesElements> darSeniorDutiesElements) {
        this.darSeniorDutiesElements = darSeniorDutiesElements;
    }

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

}