package com.example.admin.ebuy.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ListProductDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CustomerData;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;
import com.example.admin.ebuy.view.MyGridview;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShopDetailFragment extends BaseFragment {
    public final static String TAG = "ShopDetailFragment";

    TextView txtNamePro,txtNameShop,txtAddressShop;
    CircleImageView imgAvatarShop;
    ListProductDetailAdapter listProductDetailAdapter;
    GridView gridViewProduct;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.shop_detail_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {
        mapped(view);
        savedInstanceState = getActivity().getIntent().getExtras();
        String data = savedInstanceState.getString("data");
        Gson gson =new Gson();
        CustomerData customerData = gson.fromJson(data,CustomerData.class);
        Toast.makeText(getContext(), ""+data, Toast.LENGTH_SHORT).show();
        txtAddressShop.setText(customerData.getAddress());
        txtNameShop.setText(customerData.getName());
        Picasso.with(getContext())
                .load(customerData.getImg())
                .placeholder(R.mipmap.logo)
                .into(imgAvatarShop);



        ((MyGridview) gridViewProduct).setExpanded(true);
        listProductDetailAdapter = new ListProductDetailAdapter(null,this);
        ((BaseActivity)getActivity()).setTitle(true,customerData.getName());
        getListProductDetailByType(customerData.getId());
    }

    @Override
    public String getTagName() {
        return TAG;
    }
    private void mapped(View view)
    {
        txtAddressShop = (TextView)view.findViewById(R.id.txtAddress);
        txtNameShop = (EBCustomFont)view.findViewById(R.id.txtNameShop);
        txtAddressShop = (EBCustomFont)view.findViewById(R.id.txtAddress);
        imgAvatarShop = (CircleImageView)view.findViewById(R.id.imgAvatarShop);
        gridViewProduct = (MyGridview)view.findViewById(R.id.gridListProduct);
    }
    private void getListProductDetailByType(int id)
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getProductDetailOfCustomer(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductDetailResponse productDetailResponse) {
                        WriteLog.e("TAG", productDetailResponse.toString());


                        listProductDetailAdapter.setLisProductDetail(productDetailResponse.getData());
                        gridViewProduct.setAdapter(listProductDetailAdapter);


                    }
                });
    }
}
