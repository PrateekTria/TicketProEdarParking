package com.ticketpro.model;

public class EquipmentModel {
    private  String deviceName;

    public EquipmentModel(String deviceName)
    {
        this.deviceName=deviceName;
    }


    public  String getdeviceName(){
        return deviceName;
    }


    public  void setDeviceName(String deviceName)
    {
        this.deviceName=deviceName;
    }
}
