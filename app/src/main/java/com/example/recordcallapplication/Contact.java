package com.example.recordcallapplication;

public class Contact {
    public String name;
    public String phoneNumber;

    public Contact(String name, String phoneNumber ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return name + " : " + phoneNumber;
    }
}