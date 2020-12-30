package com.example.servicetech;

import java.sql.Blob;

public class EventModel {
    private static int id;
    private static String Type;
    private static String Service;
    private static String Time;
    private static String location;
    private static String address;
    private static String context;
    private static byte[] img;

    //constructors

    public EventModel(int id, String Type, String Service, String Time, String location,
                      String address, String context, Blob img) {
    //    this.id - id;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        EventModel.id = id;
    }

    public static String getType() {
        return Type;
    }

    public static void setType(String type) {
        Type = type;
    }

    public static String getService() {
        return Service;
    }

    public static void setService(String service) {
        Service = service;
    }

    public static String getTime() {
        return Time;
    }

    public static void setTime(String time) {
        Time = time;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        EventModel.location = location;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        EventModel.address = address;
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        EventModel.context = context;
    }

    public static byte[] getImg() {
        return img;
    }

    public static void setImg(byte[] img) {
        EventModel.img = img;
    }


}
