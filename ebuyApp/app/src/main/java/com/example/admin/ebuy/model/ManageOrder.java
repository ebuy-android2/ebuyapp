package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Donald Trieu on 1/3/2019.
 */
@JsonObject
public class ManageOrder {
    @JsonField(name="id")
    private int id;
    @JsonField(name = "id_product_detail")
    private int id_product_detail;
    @JsonField(name = "id_customer")
    private int id_customer;
    @JsonField(name = "name_customer")
    private String name_customer;
    @JsonField(name = "name")
    private String name;
    @JsonField(name = "phobenumber")
    private String phobenumber;
    @JsonField(name = "date")
    private String date;
    @JsonField(name = "avatar")
    private String avatar;
    @JsonField(name = "address")
    private String address;
    @JsonField(name = "price")
    private float price;
    @JsonField(name = "quantity")
    private float quantity;
    @JsonField(name = "amount")
    private float amount;
    @JsonField(name="status")
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product_detail() {
        return id_product_detail;
    }

    public void setId_product_detail(int id_product_detail) {
        this.id_product_detail = id_product_detail;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhobenumber() {
        return phobenumber;
    }

    public void setPhobenumber(String phobenumber) {
        this.phobenumber = phobenumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
