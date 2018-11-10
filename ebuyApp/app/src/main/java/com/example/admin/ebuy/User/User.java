package com.example.admin.ebuy.User;

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
    @JsonField(name = "address")
    private String address;
    @JsonField(name = "phone")
    private String phone;
    @JsonField(name = "create_at")
    private String createAt;
    @JsonField(name = "update_at")
    private String updateAt;
    @JsonField(name = "token")
    private String token;
    @JsonField(name = "avatar")
    private String avatar;
    @JsonField(name = "role")
    private int role;
    @JsonField(name = "active")
    private int active;

    @JsonField(name = "access_token")
    private String accessToken="";


    @JsonField(name = "first_login")
    private int firstLogin;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(int firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
