package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.CityData;

import java.util.ArrayList;
import java.util.List;

@JsonObject
public class CityResponse extends BaseResponse{
    @JsonField(name = "data")
    private List<CityData> data;

    public List<CityData> getData() {
        return data;
    }

    public void setData(List<CityData> data) {
        this.data = data;
    }
}
