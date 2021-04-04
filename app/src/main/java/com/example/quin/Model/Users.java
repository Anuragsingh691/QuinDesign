package com.example.quin.Model;

public class Users {
    private String Name,phone,Pass,image,address;

    public Users()
    {

    }

    public Users(String name, String phone, String pass, String image, String address) {
        Name = name;
        this.phone = phone;
        Pass = pass;
        this.image = image;
        this.address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
