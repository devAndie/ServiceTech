package com.example.servicetech;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TechnicianModel {
    public static String id, names, mail, phoneNo, specialty, tether, password;

    public TechnicianModel() {
    }

    public TechnicianModel(String Id, String Names, String PhoneNo,
                           String Mail, String Specialty, String Tether,
                            String Password) {
        this.id = Id;
        this.names = Names;
        this.mail = Mail;
        this.specialty = Specialty;
        this.phoneNo = PhoneNo;
        this.tether = Tether;
        this.password = Password;

    }
    public static String GetDate() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentdate = df.format(Calendar.getInstance().getTime());

        return currentdate;
    }
    public static void setId(String Id) {
        Id = GetDate();
        TechnicianModel.id = Id;
    }

    public static String getId() {
        return id;
    }

    public static String getNames() {
        return names;
    }

    public static void setNames(String names) {
        TechnicianModel.names = names;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        TechnicianModel.mail = mail;
    }

    public static String getSpecialty() {
        return specialty;
    }

    public static void setSpecialty(String specialty) {
        TechnicianModel.specialty = specialty;
    }

    public static String getTether() {
        return tether;
    }

    public static void setTether(String tether) {
        TechnicianModel.tether = tether;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        TechnicianModel.password = password;
    }

    public static String getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(String phoneNo) {
        TechnicianModel.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "TechnicianModel{}";
    }
}
