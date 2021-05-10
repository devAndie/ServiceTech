package com.example.servicetech;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerModel {

    private static String custId, Names, mail, phone, address, password;

    //Constructors
    public CustomerModel() {
    }

    public CustomerModel(String custId, String Names, String mail,
                         String address,  String phone, String password) {
        this.custId = custId;
        this.Names = Names;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }
    public static String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());

        return currentdate;
    }
    public static void setId(String Id) {
        Id = GetDate();
        CustomerModel.custId = Id;
    }

    public static String getId() {
        return custId;
    }

    public static String getNames() {
        return Names;
    }

    public static void setNames(String userName) {
        CustomerModel.Names = Names;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        CustomerModel.mail = mail;
    }

    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address) {
        CustomerModel.address = address;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CustomerModel.password = password;
    }


    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        CustomerModel.phone = phone;
    }

}
