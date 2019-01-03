package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class OrderDetailData {
    @JsonField(name = "id")
    private int id;
    @JsonField(name = "id_product_detail")
    private int id_product_detail;
    @JsonField(name = "name")
    private String name;
    @JsonField(name = "price")
    private float price;
    @JsonField(name = "avatar")
    private String avatar;
    @JsonField(name = "status")
    private boolean status;
    @JsonField(name = "quantity")
    private int quantity;
    @JsonField(name = "max_quantity")
    private int max_quantity;

    public int getMax_quantity() {
        return max_quantity;
    }

    public void setMax_quantity(int max_quantity) {
        this.max_quantity = max_quantity;
    }

    public int getId_product_detail() {
        return id_product_detail;
    }

    public void setId_product_detail(int id_product_detail) {
        this.id_product_detail = id_product_detail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
