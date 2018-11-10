package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProductDetailData {
    @JsonField(name="id_product_detail")
    private int id_detail;
    @JsonField(name = "id_product")
    private int id_product;
    @JsonField(name = "id_type")
    private int id_type;
    @JsonField(name = "name_product_detail")
    private String name;
    @JsonField(name = "image_product_detail")
    private String image;
    @JsonField(name = "price_product_detail")
    private int price;
    @JsonField(name = "quantity_product_detail")
    private int quantity;
    @JsonField(name = "color_product_detail")
    private String color;
    @JsonField (name = "weigh_product_detail")
    private int weigh;
    @JsonField (name = "product_status")
    private boolean status;
    @JsonField(name = "describe_product_detail")
    private String describe;
    @JsonField (name = "material_product_detail")
    private String material;
    @JsonField(name = "trademark_product_detail")
    private String trademark;
    @JsonField(name = "address_from")
    private String address;
    @JsonField(name = "countstar")
    private  int numStar;
    @JsonField(name ="countfeedback")
    private int numFeedback;
    @JsonField(name = "countlike")
    private int numLike;

    public int getNumStar() {
        return numStar;
    }

    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }

    public int getNumFeedback() {
        return numFeedback;
    }

    public void setNumFeedback(int numFeedback) {
        this.numFeedback = numFeedback;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
