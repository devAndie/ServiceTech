package com.example.servicetech;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class CustomerUpload {
    private String firstName, lastName, userName, mail, address, password;
    private int phone;

    private DatabaseReference mDatabaseRef;

    public CustomerUpload(){
        //empty constructor needed
    }

    public CustomerUpload(String fName, String lName, String uName,
                          String email, String Address, String Password, int phoneNo){
        firstName = fName;  lastName = lName;    userName = uName;
        mail = email;   address = Address;  password = Password;
        phone = phoneNo;
    }

    public void setFirstName(String fName) { firstName = fName; }
    public String getFirstName() { return firstName; }

    public void setLastName(String lName) { lastName = lName; }

    public String getLastName() { return lastName; }

    public void setUserName(String uName) { userName = uName; }
    public String getUserName() { return userName; }

    public void setMail(String email) { mail = email; }
    public String getMail() { return mail; }

    public void setAddress(String Address) { address = Address; }
    public String getAddress() { return address; }

    public void setPhone(int phone) { this.phone = phone; }
    public int getPhone() { return phone; }

    DatabaseReference customersRef = mDatabaseRef.child("customers");
   /*
    Map<String, Customers> customers new HashMap<>();
    customers.put("", new Customer());

    customersRef.setValueAsync(customers)


    */
}
