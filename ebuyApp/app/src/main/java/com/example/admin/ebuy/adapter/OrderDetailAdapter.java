package com.example.admin.ebuy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.OrderDetailData;
import com.example.admin.ebuy.view.EBCustomFont;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    BaseFragment baseFragment;
    ArrayList<OrderDetailData> listOrder;
    private SparseIntArray itemStateArray= new SparseIntArray();
    private boolean checkAll;

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public SparseIntArray getItemStateArray() {
        return itemStateArray;
    }

    public OrderDetailAdapter(BaseFragment baseFragment) {
        this.baseFragment = baseFragment;
    }

    public void setListOrder(ArrayList<OrderDetailData> listOrder) {
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(baseFragment.getContext());
        View view = layoutInflater.inflate(R.layout.order_detail_item,viewGroup,false);
        return new OrderDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(listOrder.get(i),i);

    }

    @Override
    public int getItemCount() {
        if (listOrder != null)
            return listOrder.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EBCustomFont txtName,txtPrice;
        ImageView imageView;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAvatar);
            txtName = itemView.findViewById(R.id.txtNamePro);
            txtPrice = itemView.findViewById(R.id.txtpricePro);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        void bind(final OrderDetailData orderDetailData, final int i)
        {
            Picasso.with(baseFragment.getContext())
                    .load(orderDetailData.getAvatar())
                    .placeholder(R.mipmap.logo)

                    .into(imageView);
            txtName.setText(orderDetailData.getName());
            txtPrice.setText(orderDetailData.getPrice()+"");
            if (checkAll)
            {
                checkBox.setChecked(true);
            }
            if (checkBox.isChecked())
            {
                itemStateArray.put(orderDetailData.getId(),getAdapterPosition());
            }
            else itemStateArray.delete(orderDetailData.getId());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        itemStateArray.put(orderDetailData.getId(),getAdapterPosition());
                    }
                    else itemStateArray.delete(orderDetailData.getId());
                }
            });
        }
    }
}
