package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class FeedBackData {
    @JsonField(name = "id")
    private int id;
    @JsonField(name = "name_customer")
    private String name;
    @JsonField(name = "id_product_detail")
    private int id_product;
    @JsonField(name = "express")
    private int express;
    @JsonField(name = "feedback")
    private float feedback;
    @JsonField(name = "comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getExpress() {
        return express;
    }

    public void setExpress(int express) {
        this.express = express;
    }

    public float getFeedback() {
        return feedback;
    }

    public void setFeedback(float feedback) {
        this.feedback = feedback;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
