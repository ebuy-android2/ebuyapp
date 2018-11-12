package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Config {
//    @JsonField(name = "api_domain")
//    private String apiDomain;

    @JsonField(name = "api_key")
    private String apiKey;

    @JsonField(name = "type")
    private String type;

    public Config() {
        super();
    }

    public Config(String apiKey, String type) {
        this.apiKey = apiKey;
        this.type = type;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //    @JsonField(name = "firebase")
//    private String firebase;
//
//    public Config() {
//    }
//
//    public Config(String apiDomain, String apiKey, String firebase) {
//        this.apiDomain = apiDomain;
//        this.apiKey = apiKey;
//        this.firebase = firebase;
//    }
//
//    public String getApiDomain() {
//        return apiDomain;
//    }
//
//    public void setApiDomain(String apiDomain) {
//        this.apiDomain = apiDomain;
//    }
//
//    public String getApiKey() {
//        return apiKey;
//    }
//
//    public void setApiKey(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public String getFirebase() {
//        return firebase;
//    }
//
//    public void setFirebase(String firebase) {
//        this.firebase = firebase;
//    }
}
