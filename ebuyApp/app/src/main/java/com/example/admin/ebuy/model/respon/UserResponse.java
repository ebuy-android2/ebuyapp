package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.User;


@JsonObject
public class UserResponse extends BaseResponse {
    @JsonField(name = "data")
    private User user;
    ///GETTER
    public User getUser() {
        return user;
    }
    ///SETTER
    public void setUser(User user) {
        this.user = user;
    }
}
