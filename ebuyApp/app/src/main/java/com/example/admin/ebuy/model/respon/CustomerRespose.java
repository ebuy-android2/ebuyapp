package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.CustomerData;

@JsonObject
public class CustomerRespose extends BaseResponse  {
    @JsonField(name = "data")
    private CustomerData data;

    public CustomerData getData() {
        return data;
    }

    public void setData(CustomerData data) {
        this.data = data;
    }
}
