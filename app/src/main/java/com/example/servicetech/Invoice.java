package com.example.servicetech;

public class Invoice {
    private String installCompn, usedCompn1, usedCompn2, replCompn1, replCompn2;
    private  double icCost, uc1Cost, uc2Cost, rc1Cost, rc2Cost, total;

    private String sumTotal;
//    public Invoice() {    }


    public Invoice(String installCompn, String usedCompn1, String usedCompn2, String replCompn1,
                   String replCompn2, double icCost, double uc1Cost, double uc2Cost, double rc1Cost,
                   double rc2Cost, double total) {
        this.installCompn = installCompn;
        this.usedCompn1 = usedCompn1;
        this.usedCompn2 = usedCompn2;
        this.replCompn1 = replCompn1;
        this.replCompn2 = replCompn2;
        this.icCost = icCost;
        this.uc1Cost = uc1Cost;
        this.uc2Cost = uc2Cost;
        this.rc1Cost = rc1Cost;
        this.rc2Cost = rc2Cost;
        this.total = total;
    }

    public String getInstallCompn() {
        return installCompn;
    }

    public void setInstallCompn(String installCompn) {
        this.installCompn = installCompn;
    }

    public String getUsedCompn1() {
        return usedCompn1;
    }

    public void setUsedCompn1(String usedCompn1) {
        this.usedCompn1 = usedCompn1;
    }

    public String getUsedCompn2() {
        return usedCompn2;
    }

    public void setUsedCompn2(String usedCompn2) {
        this.usedCompn2 = usedCompn2;
    }

    public String getReplCompn1() {
        return replCompn1;
    }

    public void setReplCompn1(String replCompn1) {
        this.replCompn1 = replCompn1;
    }

    public String getReplCompn2() {
        return replCompn2;
    }

    public void setReplCompn2(String replCompn2) {
        this.replCompn2 = replCompn2;
    }

    public double getIcCost() {
        return icCost;
    }

    public void setIcCost(double icCost) {
        this.icCost = icCost;
    }

    public double getUc1Cost() {
        return uc1Cost;
    }

    public void setUc1Cost(double uc1Cost) {
        this.uc1Cost = uc1Cost;
    }

    public double getUc2Cost() {
        return uc2Cost;
    }

    public void setUc2Cost(double uc2Cost) {
        this.uc2Cost = uc2Cost;
    }

    public double getRc1Cost() {
        return rc1Cost;
    }

    public void setRc1Cost(double rc1Cost) {
        this.rc1Cost = rc1Cost;
    }

    public double getRc2Cost() {
        return rc2Cost;
    }

    public void setRc2Cost(double rc2Cost) {
        this.rc2Cost = rc2Cost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = icCost + uc1Cost + uc2Cost + rc1Cost + rc2Cost + total;
    }
    public String getSumTotal(){
        return "Kshs: " + total;
    }
}
