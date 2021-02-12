package com.example.servicetech;

import android.graphics.Bitmap;
import android.net.Uri;

public class EventModel {
    private String id, itemName, Service, location, notes, imageURL, picked;

    //constructors
    public EventModel() {
    }

    public EventModel(String id, String itemName, String Service, String location,
                      String notes, String imageURL, String picked) {
        this.id = id;
        this.itemName = itemName;
        this.Service = Service;
        this.location = location;
        this.notes = notes;
        this.imageURL = imageURL;
        this.picked = picked;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setService(String service) {
        this.Service = service;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getItemName() {
        return itemName;
    }

    public String getService() {
        return Service;
    }

    public String getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPicked() {
        return picked;
    }

    public void setPicked(String picked) {
        this.picked = picked;
    }
}

