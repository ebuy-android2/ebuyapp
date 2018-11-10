package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.model.ProductDetailData;

import java.util.ArrayList;

@JsonObject
public class ProductDetailResponse extends BaseResponse {
    @JsonField(name = "data")
    ArrayList<ProductDetailData> data;

    public ArrayList<ProductDetailData> getData() {
        return data;
    }

    public void setData(ArrayList<ProductDetailData> data) {
        this.data = data;
    }
}
