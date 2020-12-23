package com.example.servicetech;

public class TechnicianModel extends CustomerModel {
    public static String Specialty, edLevel, OpsTime, LocTether;

    public TechnicianModel(int id, String firstName, String lastName, String userName, String mail,
                           String address, int phone, String password, String specialty,
                           String edLevel, String opsTime, String locTether) {
        super(id, firstName, lastName, userName, mail, address, phone, password);
        Specialty = specialty;
        this.edLevel = edLevel;
        OpsTime = opsTime;
        LocTether = locTether;
    }

    public static String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public static String getEdLevel() {
        return edLevel;
    }

    public void setEdLevel(String edLevel) {
        this.edLevel = edLevel;
    }

    public static String getOpsTime() {
        return OpsTime;
    }

    public void setOpsTime(String opsTime) {
        OpsTime = opsTime;
    }

    public static String getLocTether() {
        return LocTether;
    }

    public void setLocTether(String locTether) {
        LocTether = locTether;
    }

    @Override
    public String toString() {
        return "TechnicianModel{" +
                "Specialty='" + Specialty + '\'' +
                ", edLevel='" + edLevel + '\'' +
                ", TimeOps='" + OpsTime + '\'' +
                ", LocTether='" + LocTether + '\'' +
                '}';
    }
}
