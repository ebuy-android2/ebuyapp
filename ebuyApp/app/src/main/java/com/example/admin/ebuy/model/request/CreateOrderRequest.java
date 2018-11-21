package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;

@JsonObject
public class CreateOrderRequest {
    @JsonField(name = "id_city")
    private int id_city;
    @JsonField(name = "id_district")
    private int id_district;
    @JsonField(name ="id_ward")
    private  int id_ward;
    @JsonField(name = "streetname")
    private String streetName;
    @JsonField(name = "address_full_text")
    private String address;
    @JsonField(name = "fee")
    private float fee;
    @JsonField(name = "amount")
    private float amount;
    @JsonField(name = "total_amount")
    private float total_amount;
    @JsonField(name = "orderDetail")
    ArrayList<OrderDetailRequest> orderDetail;

    public CreateOrderRequest() {
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public int getId_district() {
        return id_district;
    }

    public void setId_district(int id_district) {
        this.id_district = id_district;
    }

    public int getId_ward() {
        return id_ward;
    }

    public void setId_ward(int id_ward) {
        this.id_ward = id_ward;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public ArrayList<OrderDetailRequest> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailRequest> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
