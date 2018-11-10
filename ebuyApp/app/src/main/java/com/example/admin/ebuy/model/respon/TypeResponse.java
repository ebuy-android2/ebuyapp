package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.TypeData;

import java.util.ArrayList;

@JsonObject
public class TypeResponse extends BaseResponse {
    @JsonField(name = "data")
    ArrayList<TypeData> data;

    public ArrayList<TypeData> getData() {
        return data;
    }

    public void setData(ArrayList<TypeData> data) {
        this.data = data;
    }
}
