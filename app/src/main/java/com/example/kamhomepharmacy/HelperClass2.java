package com.example.kamhomepharmacy;

public class HelperClass2 {

    String name;
    String email;
    String contact;
    String address;
    String reason;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HelperClass2(String name, String email, String contact, String address, String reason) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.reason = reason;
    }

    public HelperClass2() {
    }
}