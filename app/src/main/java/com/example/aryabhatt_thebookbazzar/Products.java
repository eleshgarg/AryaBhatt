package com.example.aryabhatt_thebookbazzar;

import java.util.HashMap;

public class Products {

    private String date, time, productname, description, price, category, image1, image2, image3, image4, pid, phonenumber, quantity, state, city, address, pincode, phonenumberuser, name, actualprice, paymethod;

    public Products() {
    }

    public Products(String date, String time, String productname, String description, String price, String category, String image1, String image2, String image3, String image4, String pid, String phonenumber, String quantity, String state, String city, String address, String pincode, String phonenumberuser, String name, String actualprice, String paymethod) {
        this.date = date;
        this.time = time;
        this.productname = productname;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.pid = pid;
        this.phonenumber = phonenumber;
        this.quantity = quantity;
        this.state = state;
        this.city = city;
        this.address = address;
        this.pincode = pincode;
        this.phonenumberuser = phonenumberuser;
        this.name = name;
        this.actualprice = actualprice;
        this.paymethod = paymethod;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getActualprice() {
        return actualprice;
    }

    public void setActualprice(String actualprice) {
        this.actualprice = actualprice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhonenumberuser() {
        return phonenumberuser;
    }

    public void setPhonenumberuser(String phonenumberuser) {
        this.phonenumberuser = phonenumberuser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
