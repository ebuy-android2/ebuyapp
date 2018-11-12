package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.Config;

@JsonObject
public class ConfigResponse extends  BaseResponse{
    @JsonField(name="data")
    private Config data;

    public Config getData() {
        return data;
    }

    public void setData(Config data) {
        this.data = data;
    }
}
