package com.example.admin.ebuy.model.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ProductRequest {
    @JsonField(name="id_list")
    private int id_list;

    @JsonField(name="id_type")
    private int id_type;

    @JsonField(name="id_type_product")
    private int id_type_product;

    @JsonField(name="image")
    private String image;

    @JsonField(name="name_product")
    private String name_product;

    @JsonField(name="describe")
    private String describe;

    @JsonField(name="material")
    private String material;

    @JsonField(name="trademark")
    private String trademark;

    @JsonField(name="price")
    private float price;

    @JsonField(name="quantity")
    private int quantity;

    @JsonField(name="weight")
    private String weight;

    @JsonField(name="product_status")
    private int product_status;

    public ProductRequest() {
    }

    public ProductRequest(int id_list, int id_type, int id_type_product, String image, String name_product, String describe, String material, String trademark, float price, int quantity, String weight, int product_status) {
        this.id_list = id_list;
        this.id_type = id_type;
        this.id_type_product = id_type_product;
        this.image = image;
        this.name_product = name_product;
        this.describe = describe;
        this.material = material;
        this.trademark = trademark;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
        this.product_status = product_status;
    }

    public int getId_list() {
        return id_list;
    }

    public void setId_list(int id_list) {
        this.id_list = id_list;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public int getId_type_product() {
        return id_type_product;
    }

    public void setId_type_product(int id_type_product) {
        this.id_type_product = id_type_product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getProduct_status() {
        return product_status;
    }

    public void setProduct_status(int product_status) {
        this.product_status = product_status;
    }
}
