package com.example.admin.ebuy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.interfaces.ItemCheckListener;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.OrderDetailData;
import com.example.admin.ebuy.model.request.AddOrderDetailRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.view.EBCustomFont;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    BaseFragment baseFragment;
    ArrayList<OrderDetailData> listOrder;
    private SparseIntArray itemStateArray = new SparseIntArray();
    ItemCheckListener itemCheckListener;
    private boolean checkAll;

    public void setItemCheckListener(ItemCheckListener itemCheckListener) {
        this.itemCheckListener = itemCheckListener;
    }

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
        View view = layoutInflater.inflate(R.layout.order_detail_item, viewGroup, false);
        return new OrderDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(listOrder.get(i), i);


    }

    @Override
    public int getItemCount() {
        if (listOrder != null)
            return listOrder.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        EBCustomFont txtName, txtPrice;
        ImageView imageView;
        CheckBox checkBox;
        ImageView btnMinus, btnPlus;
        EditText edtQantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAvatar);
            txtName = itemView.findViewById(R.id.txtNamePro);
            txtPrice = itemView.findViewById(R.id.txtpricePro);
            checkBox = itemView.findViewById(R.id.checkbox);
            edtQantity = itemView.findViewById(R.id.edtQuantity);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);

        }

        void bind(final OrderDetailData orderDetailData, final int i) {
            edtQantity.setText(orderDetailData.getQuantity() + "");
            Picasso.with(baseFragment.getContext())
                    .load(orderDetailData.getAvatar())
                    .placeholder(R.mipmap.logo)

                    .into(imageView);
            txtName.setText(orderDetailData.getName());
            txtPrice.setText(orderDetailData.getPrice() + "");
            if (checkAll) {
                checkBox.setChecked(true);
                itemCheckListener.onCheck(orderDetailData.getPrice()*Integer.parseInt(edtQantity.getText().toString()));
            }

            if (checkBox.isChecked()) {
                itemStateArray.put(orderDetailData.getId(), getAdapterPosition());
            } else itemStateArray.delete(orderDetailData.getId());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        itemStateArray.put(orderDetailData.getId(), getAdapterPosition());
                        itemCheckListener.onCheck(orderDetailData.getPrice()*Integer.parseInt(edtQantity.getText().toString()));
                    } else {
                        itemStateArray.delete(orderDetailData.getId());
                        itemCheckListener.onCheck(-orderDetailData.getPrice()*Integer.parseInt(edtQantity.getText().toString()));

                    }
                }
            });
            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity1 = Integer.parseInt(edtQantity.getText().toString());
                    quantity1++;
                    if (quantity1 > orderDetailData.getMax_quantity())
                        quantity1 = orderDetailData.getMax_quantity();
                    else if(checkBox.isChecked())
                        itemCheckListener.onCheck(orderDetailData.getPrice());
                    edtQantity.setText(quantity1 + "");

                    addOrderDetail(orderDetailData, quantity1);
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = Integer.parseInt(edtQantity.getText().toString());
                    quantity--;
                    if (quantity < 1)
                        quantity = 1;
                    else  if(checkBox.isChecked())
                        itemCheckListener.onCheck(-orderDetailData.getPrice());
                    edtQantity.setText(quantity + "");

                    addOrderDetail(orderDetailData, quantity);
                }
            });
            edtQantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(edtQantity.getText()==null || edtQantity.getText().toString().isEmpty()) edtQantity.setText("1");
                    if(Integer.parseInt(edtQantity.getText().toString())>orderDetailData.getMax_quantity())
                    {
                        edtQantity.setText("1");
                        Toast.makeText(baseFragment.getContext(), "Hiện chỉ còn "+orderDetailData.getMax_quantity()+" sản phẩm", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        private void addOrderDetail(OrderDetailData orderDetailData, int quantity) {
            AddOrderDetailRequest addOrderDetailRequest = new AddOrderDetailRequest();
            addOrderDetailRequest.setId(orderDetailData.getId_product_detail());
            addOrderDetailRequest.setName(orderDetailData.getName());
            addOrderDetailRequest.setAmount(orderDetailData.getPrice());
            addOrderDetailRequest.setPrice(orderDetailData.getPrice() * quantity);
            addOrderDetailRequest.setStatus(false);
            addOrderDetailRequest.setQuantity(quantity);
            ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                    .addOrderDetail(CurrentUser.getUserInfo().getId(), addOrderDetailRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseResponse>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
//                        Toast.makeText(getContext(), "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
