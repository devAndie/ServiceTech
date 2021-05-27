package com.example.servicetech;

public class TechnicianModel {
    public static String techId, Names, Mail, Phone, Specialty, Tether, Password;

    public TechnicianModel() {
    }

    public TechnicianModel(String Id, String Names, String PhoneNo,
                           String Mail, String Specialty, String Tether,
                            String Password) {
        this.techId = Id;
        this.Names = Names;
        this.Mail = Mail;
        this.Specialty = Specialty;
        this.Phone = PhoneNo;
        this.Tether = Tether;
        this.Password = Password;

    }
    public static void setId(String Id) {
        TechnicianModel.techId = Id;
    }

    public static String getId() {
        return techId;
    }

    public static String getNames() {
        return Names;
    }

    public static void setNames(String names) {
        TechnicianModel.Names = names;
    }

    public static String getMail() {
        return Mail;
    }

    public static void setMail(String mail) {
        TechnicianModel.Mail = mail;
    }

    public static String getSpecialty() {
        return Specialty;
    }

    public static void setSpecialty(String specialty) {
        TechnicianModel.Specialty = specialty;
    }

    public static String getTether() {
        return Tether;
    }

    public static void setTether(String tether) {
        TechnicianModel.Tether = tether;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        TechnicianModel.Password = password;
    }

    public static String getPhone() {
        return Phone;
    }

    public static void setPhone(String phone) {
        TechnicianModel.Phone = phone;
    }

    @Override
    public String toString() {
        return "TechnicianModel{}";
    }
}
