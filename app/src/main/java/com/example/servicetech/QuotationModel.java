package com.example.servicetech;

public class QuotationModel {
    public int id;
    public static String Status;
    public static String Items;
    public static double Total;

    public QuotationModel(int id, String status, String items, double total) {
        this.id = id;
        Status = status;
        Items = items;
        Total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public static String getItems() {
        return Items;
    }

    public void setItems(String items) {
        Items = items;
    }

    public static double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    @Override
    public String toString() {
        return "QuotationModel{" +
                "id=" + id +
                ", Status='" + Status + '\'' +
                ", Items='" + Items + '\'' +
                ", Total=" + Total +
                '}';
    }
}
