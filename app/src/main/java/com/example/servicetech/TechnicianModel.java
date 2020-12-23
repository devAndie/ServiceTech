package com.example.servicetech;

public class TechnicianModel extends CustomerModel {
    public String Specialty, edLevel, TimeOps, LocTether;

    public TechnicianModel(int id, String firstName, String lastName, String userName, String mail,
                           String address, int phone, String password, String specialty,
                           String edLevel, String timeOps, String locTether) {
        super(id, firstName, lastName, userName, mail, address, phone, password);
        Specialty = specialty;
        this.edLevel = edLevel;
        TimeOps = timeOps;
        LocTether = locTether;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public void setSpecialty(String specialty) {
        Specialty = specialty;
    }

    public String getEdLevel() {
        return edLevel;
    }

    public void setEdLevel(String edLevel) {
        this.edLevel = edLevel;
    }

    public String getTimeOps() {
        return TimeOps;
    }

    public void setTimeOps(String timeOps) {
        TimeOps = timeOps;
    }

    public String getLocTether() {
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
                ", TimeOps='" + TimeOps + '\'' +
                ", LocTether='" + LocTether + '\'' +
                '}';
    }
}
