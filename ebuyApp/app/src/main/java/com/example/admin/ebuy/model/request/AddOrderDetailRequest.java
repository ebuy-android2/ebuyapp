package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class AddOrderDetailRequest {
    @JsonField(name = "id_product_detail")
    private int id;
    @JsonField(name = "name")
    private String name;
    @JsonField(name = "price")
    private float price;
    @JsonField(name = "quantity")
    private int quantity;
    @JsonField(name = "amount")
    private float amount;
    @JsonField(name = "status")
    private boolean status;

    public AddOrderDetailRequest() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
}
