package com.example.servicetech;

public class CustomerModel {

    private static String custId, Names;
    private static String mail;
    private static String address;
    private static String password;
    private static int phone;

    //Constructors
    public CustomerModel() {
    }

    public CustomerModel(String custId, String Names, String mail,
                         String address,  int phone, String password) {
        this.custId = custId;
        this.Names = Names;
        this.mail = mail;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    public static String getCustId() {
        return custId;
    }

    public static void setCustId(String custId) {
        CustomerModel.custId = custId;
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


    public static int getPhone() {
        return phone;
    }

    public static void setPhone(int phone) {
        CustomerModel.phone = phone;
    }

}
