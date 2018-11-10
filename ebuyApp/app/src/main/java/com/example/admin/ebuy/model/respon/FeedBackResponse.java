package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.FeedBackData;

import java.util.ArrayList;

@JsonObject
public class FeedBackResponse extends BaseResponse {
    @JsonField(name = "data")
    private ArrayList<FeedBackData> data;

    public ArrayList<FeedBackData> getData() {
        return data;
    }

    public void setData(ArrayList<FeedBackData> data) {
        this.data = data;
    }
}
