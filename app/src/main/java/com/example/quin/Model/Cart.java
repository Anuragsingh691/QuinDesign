package com.example.quin.Model;

public class Cart {
    private String PName,discount,pid,price,quantity,date,time,image;

    public Cart() {
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Cart(String PName, String discount, String pid, String price, String quantity, String date, String time, String image) {
        this.PName = PName;
        this.discount = discount;
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.time = time;
        this.image = image;
    }
}
