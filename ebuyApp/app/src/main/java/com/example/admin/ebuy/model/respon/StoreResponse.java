package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.StoreData;

import java.util.ArrayList;

@JsonObject
public class StoreResponse extends BaseResponse {
    @JsonField(name = "data")
    private ArrayList<StoreData> data;

    public ArrayList<StoreData> getData() {
        return data;
    }

    public void setData(ArrayList<StoreData> data) {
        this.data = data;
    }
}
