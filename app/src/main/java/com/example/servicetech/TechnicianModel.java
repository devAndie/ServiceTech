package com.example.servicetech;

public class TechnicianModel {
    public static String Names, mail, Specialty, Tether, password;
    public static int id, phoneNo;

    public TechnicianModel() {
    }

    public TechnicianModel(int id, String Names, int phoneNo,
                           String mail, String Specialty, String Tether, String password) {
        this.id = id;
        this.Names = Names;
        this.mail = mail;
        this.Specialty = Specialty;
        this.phoneNo = phoneNo;
        this.Tether = Tether;
        this.password = password;

    }

    public static String getNames() {
        return Names;
    }

    public static void setNames(String names) {
        Names = names;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        TechnicianModel.mail = mail;
    }

    public static String getSpecialty() {
        return Specialty;
    }

    public static void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public static String getTether() {
        return Tether;
    }

    public static void setTether(String tether) {
        Tether = tether;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        TechnicianModel.password = password;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        TechnicianModel.id = id;
    }

    public static int getPhoneNo() {
        return phoneNo;
    }

    public static void setPhoneNo(int phoneNo) {
        TechnicianModel.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "TechnicianModel{}";
    }
}
