package com.example.servicetech;

public class Appointment {
    private String id, itemName, serviceType, place, startTime;

    public Appointment(){
    }
    public Appointment(String id, String itemName, String serviceType, String place, String startTime) {
        this.id = id;
        this.itemName = itemName;
        this.serviceType = serviceType;
        this.startTime = startTime;
        this.place = place;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId(){
        return id;
    }
}
