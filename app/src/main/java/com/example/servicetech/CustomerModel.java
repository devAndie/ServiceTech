package com.example.servicetech;

public class CustomerModel {

    private static String custId, Names, Mail, Phone, Address, Password;

    //Constructors
    public CustomerModel() {
    }

    public CustomerModel(String custId, String Names, String mail,
                         String address,  String phone, String password) {
        this.custId = custId;
        this.Names = Names;
        this.Mail = mail;
        this.Address = address;
        this.Phone = phone;
        this.Password = password;
    }
    public static void setId(String Id) {
        CustomerModel.custId = Id;
    }

    public static String getId() {
        return custId;
    }

    public static String getNames() {
        return Names;
    }

    public static void setNames(String Names) {
        CustomerModel.Names = Names;
    }

    public static String getMail() {
        return Mail;
    }

    public static void setMail(String mail) {
        CustomerModel.Mail = mail;
    }

    public static String getAddress() {
        return Address;
    }
    public static void setAddress(String address) {
        CustomerModel.Address = address;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        CustomerModel.Password = password;
    }


    public static String getPhone() {
        return Phone;
    }

    public static void setPhone(String phone) {
        CustomerModel.Phone = phone;
    }

}
