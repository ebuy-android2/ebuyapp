package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.ProductData;

import java.util.ArrayList;

@JsonObject
public class ProductResponse extends BaseResponse {



    @JsonField(name = "data")
    private ArrayList<ProductData> data;

    public ArrayList<ProductData> getData() {
        return data;
    }

    public void setData(ArrayList<ProductData> data) {
        this.data = data;
    }
}
