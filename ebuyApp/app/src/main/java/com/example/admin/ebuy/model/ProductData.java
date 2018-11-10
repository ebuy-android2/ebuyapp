package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProductData {
    @JsonField(name = "id_list_product")
    private int id;
    @JsonField(name = "image_list_product")
    private String image;
    @JsonField(name = "name_list_product")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductData() {

    }

    public ProductData(int id, String image, String name) {

        this.id = id;
        this.image = image;
        this.name = name;
    }
}
