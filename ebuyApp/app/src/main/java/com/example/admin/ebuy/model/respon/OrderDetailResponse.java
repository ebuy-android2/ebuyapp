package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.OrderDetailData;

import java.util.ArrayList;

@JsonObject
public class OrderDetailResponse extends BaseResponse {
    @JsonField(name = "data")
    ArrayList<OrderDetailData> data;

    public ArrayList<OrderDetailData> getData() {
        return data;
    }


}
