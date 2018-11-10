package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.LikeData;

@JsonObject
public class LikeResponse extends BaseResponse {
    @JsonField(name = "data")
    private LikeData data;

    public LikeData getData() {
        return data;
    }

    public void setData(LikeData data) {
        this.data = data;
    }
}
