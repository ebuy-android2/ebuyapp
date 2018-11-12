package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class LoginRequest {
    @JsonField(name = "username")
    private String username;
    @JsonField(name = "password")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    ///GETTER
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    ///SETTER
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
