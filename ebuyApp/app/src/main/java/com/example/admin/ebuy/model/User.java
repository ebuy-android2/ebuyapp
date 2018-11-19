package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class User {
    @JsonField(name = "id")
    private int id;
    @JsonField(name = "email")
    private String email;
    @JsonField(name = "username")
    private String userName;
    @JsonField(name = "fullname")
    private String fullName;
//    @JsonField(name = "address")
//    private String address;
    @JsonField(name = "phone_number")
    private String phone;
    @JsonField(name = "token_type")
    private String token;
    @JsonField(name = "avatar")
    private String avatar="";
    @JsonField(name = "access_token")
    private String accessToken="";

    public static final int SHIPPER = 1;
    public static final int CUSTOMER = 2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
