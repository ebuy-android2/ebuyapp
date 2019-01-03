package com.example.admin.ebuy.model.respon;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.example.admin.ebuy.model.ManageOrder;

import java.util.List;

/**
 * Created by Donald Trieu on 1/3/2019.
 */
@JsonObject
public class ManageOrderResponse extends BaseResponse {
    @JsonField(name = "data")
    private List<ManageOrder> manageOrders;

    public List<ManageOrder> getManageOrders() {
        return manageOrders;
    }

    public void setManageOrders(List<ManageOrder> manageOrders) {
        this.manageOrders = manageOrders;
    }
}
