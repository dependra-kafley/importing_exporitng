package com.dependra.importing_exporitng.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private String outletName;
    private String merchantName;
    private String address1;
    private String address2;
    private String area;
    private int pin;
    private String city;
    private String state;
    private String newCategory;
    private String qrEnabled;

    public Coupon() {
    }

    public Coupon(String outletName, String merchantName, String address1, String address2, String area, int pin, String city, String state, String newCategory, String qrEnabled) {
        this.outletName = outletName;
        this.merchantName = merchantName;
        this.address1 = address1;
        this.address2 = address2;
        this.area = area;
        this.pin = pin;
        this.city = city;
        this.state = state;
        this.newCategory = newCategory;
        this.qrEnabled = qrEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(String newCategory) {
        this.newCategory = newCategory;
    }

    public String isQrEnabled() {
        return qrEnabled;
    }

    public void setQrEnabled(String qrEnabled) {
        this.qrEnabled = qrEnabled;
    }
}
