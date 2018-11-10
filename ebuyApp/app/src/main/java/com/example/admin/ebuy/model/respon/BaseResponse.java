package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.IOException;

/**
 * Created by lamthinh on 2/15/18.
 */
@JsonObject
public class BaseResponse {

    @JsonField(name = "status")
    private int replyCode;
    @JsonField(name = "message")
    private String replyText;
    ///GETTER
    public int getReplyCode() {
        return replyCode;
    }
    public String getReplyText() {
        return replyText;
    }
    ///SETTER
    public void setReplyCode(int replyCode) {
        this.replyCode = replyCode;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    @Override
    public String toString() {
        try {
            return "response: "+ LoganSquare.serialize(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
