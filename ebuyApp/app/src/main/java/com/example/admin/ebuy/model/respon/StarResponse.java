package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.StarData;

@JsonObject
public class StarResponse extends BaseResponse {
    @JsonField(name = "data")
    StarData data;

    public StarData getData() {
        return data;
    }

    public void setData(StarData data) {
        this.data = data;
    }
}
