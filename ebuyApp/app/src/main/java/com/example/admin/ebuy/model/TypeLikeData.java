package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class TypeLikeData  {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonField(name="type")
    int type;
}
