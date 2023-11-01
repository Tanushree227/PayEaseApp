package com.example.payeaseapp;

public class Contacts {
    private int id;
    private String name;
    private String phoneNumber, balance;

    public Contacts(String name, String phoneNumber, String balance) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBalance() {
        return balance;
    }
}
