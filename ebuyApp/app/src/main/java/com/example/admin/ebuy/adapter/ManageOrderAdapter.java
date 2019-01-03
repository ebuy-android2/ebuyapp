package com.example.admin.ebuy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.ManageOrder;
import com.example.admin.ebuy.view.EBCustomFont;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Donald Trieu on 1/3/2019.
 */
public class ManageOrderAdapter extends RecyclerView.Adapter<ManageOrderAdapter.ViewHolder>{

    private BaseFragment baseFragment;
    private List<ManageOrder> manageOrders = new ArrayList<>();

    public ManageOrderAdapter(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public BaseFragment getBaseFragment() {
        return baseFragment;
    }

    public void setBaseFragment(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public List<ManageOrder> getManageOrders() {
        return manageOrders;
    }

    public void setManageOrders(List<ManageOrder> manageOrders) {
        this.manageOrders = manageOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_item_orderdetail_sale, viewGroup, false);
        return new ManageOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(manageOrders.get(i));
    }

    @Override
    public int getItemCount() {
        return manageOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EBCustomFont txtNameProductDetail, txtNameCustomer, txtDate, txtPhonenumber, txtAddress, txtQuatity, txtCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProductDetail = itemView.findViewById(R.id.txtNameProductDetail);
            txtNameCustomer = itemView.findViewById(R.id.txtNameCustomer);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtPhonenumber = itemView.findViewById(R.id.txtPhonenumber);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtQuatity = itemView.findViewById(R.id.txtQuatity);
            txtCount = itemView.findViewById(R.id.txtCount);

        }
        private void bind(ManageOrder manageOrder){
            txtNameProductDetail.setText(manageOrder.getName());
            txtNameCustomer.setText(manageOrder.getName_customer());
            txtDate.setText(manageOrder.getDate());
            txtPhonenumber.setText(manageOrder.getPhobenumber());
            txtAddress.setText(manageOrder.getAddress());
            txtQuatity.setText(manageOrder.getAmount()+"");
            txtCount.setText(manageOrder.getQuantity()+"");
       }

    }
}
