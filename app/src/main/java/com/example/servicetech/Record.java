package com.example.servicetech;

public class Record extends Appointment {
    private String endTime;

    public Record(String id, String customerId, String itemName, String service,
                  String location, String notes, String imageURL, String picked, String techId,
                  String recommendation, String startTime, String date, String endTime) {
        super(id, customerId, itemName, service, location, notes, imageURL, picked, techId,
                recommendation, startTime, date);
        this.endTime = endTime;
    }

    public Record() {    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
