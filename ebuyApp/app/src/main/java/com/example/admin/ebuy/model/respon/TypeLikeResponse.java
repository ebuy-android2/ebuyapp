package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.TypeLikeData;

@JsonObject
public class TypeLikeResponse extends BaseResponse{
    @JsonField(name="data")
    TypeLikeData data;

    public TypeLikeData getData() {
        return data;
    }

    public void setData(TypeLikeData data) {
        this.data = data;
    }
}
