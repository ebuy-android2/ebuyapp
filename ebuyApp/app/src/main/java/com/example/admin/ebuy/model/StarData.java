package com.example.admin.ebuy.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class StarData {
    @JsonField(name = "countstar")
    private int star;
    @JsonField(name = "countfeedback")
    private int numPre;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getNumPre() {
        return numPre;
    }

    public void setNumPre(int numPre) {
        this.numPre = numPre;
    }
}
