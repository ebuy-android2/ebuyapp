package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class OrderDetailRequest {
    @JsonField(name = "id")
    private int id;

    public OrderDetailRequest(int id) {
        this.id = id;
    }

    public OrderDetailRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
