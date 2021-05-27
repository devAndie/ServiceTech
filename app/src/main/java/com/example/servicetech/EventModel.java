package com.example.servicetech;

public class EventModel {
    public String id, custId, Item, Service, Location, Note, imageURL, Status;

    //constructors
    public EventModel() {
    }


    public EventModel(String id, String customerId, String itemName, String service,
                      String location, String Note, String imageURL, String Status) {
        this.id = id;
        this.custId = customerId;
        this.Item = itemName;
        this.Service = service;
        this.Location = location;
        this.Note = Note;
        this.imageURL = imageURL;
        this.Status = Status;
    }

    public String getCustomerId() {
        return custId;
    }

    public void setCustomerId(String customerId) {
        this.custId = customerId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setItemName(String itemName) {
        this.Item = itemName;
    }

    public void setService(String service) {
        this.Service = service;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public String getItemName() {
        return Item;
    }

    public String getService() {
        return Service;
    }

    public String getLocation() {
        return Location;
    }

    public String getNote() {
        return Note;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPicked() {
        return Status;
    }

    public void setPicked(String status) {
        this.Status = status;
    }
}

