package com.example.quin.Model;

public class Admin_Orders {
    private String name, phone , address,city,date, time, state , Grand_total;

    public Admin_Orders() {
    }

    public Admin_Orders(String name, String phone, String address, String city, String date, String time, String state, String grand_total) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.date = date;
        this.time = time;
        this.state = state;
        Grand_total = grand_total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrand_total() {
        return Grand_total;
    }

    public void setGrand_total(String grand_total) {
        Grand_total = grand_total;
    }
}
