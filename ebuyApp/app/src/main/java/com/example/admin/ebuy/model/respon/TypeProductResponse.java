package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.TypeProductData;

import java.util.ArrayList;

@JsonObject
public class TypeProductResponse extends BaseResponse {
    @JsonField(name = "data")
    private ArrayList<TypeProductData> data;

    public ArrayList<TypeProductData> getData() {
        return data;
    }

    public void setData(ArrayList<TypeProductData> data) {
        this.data = data;
    }
}
