package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class RegisterRequest {
    @JsonField(name = "username")
    private String username;
    @JsonField(name = "password")
    private String password;
    @JsonField(name = "phone_number")
    private String phone_number;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String password, String phone_number) {
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
