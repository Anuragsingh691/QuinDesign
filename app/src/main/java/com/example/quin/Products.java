package com.example.quin;

public class Products  {
    private String Pcaption,location,pid,price,Category,date,time,image,Uname,Uphone;

    public Products(String pcaption, String location, String pid, String price, String category, String date, String time, String image, String uname, String uphone) {
        Pcaption = pcaption;
        this.location = location;
        this.pid = pid;
        this.price = price;
        Category = category;
        this.date = date;
        this.time = time;
        this.image = image;
        Uname = uname;
        Uphone = uphone;
    }

    public String getPcaption() {
        return Pcaption;
    }

    public void setPcaption(String pcaption) {
        Pcaption = pcaption;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUphone() {
        return Uphone;
    }

    public void setUphone(String uphone) {
        Uphone = uphone;
    }

    public Products()
    {

    }

}
