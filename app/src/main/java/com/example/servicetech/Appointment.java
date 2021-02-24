package com.example.servicetech;

public class Appointment extends EventModel{
    private String techId, recommendation, startTime, date;

   // public Appointment(){}

    public Appointment(String id, String itemName, String Service, String location, String notes,
                       String imageURL, String picked, String techId, String recommendation, String startTime,
                       String date) {
        super(id, itemName, Service, location, notes, imageURL, picked);
        this.techId = techId;
        this.recommendation = recommendation;
        this.startTime = startTime;
        this.date = date;
    }

    public Appointment() {}

    public String getTechId() {
        return techId;
    }

    public void setTechId(String techId) {
        this.techId = techId;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
