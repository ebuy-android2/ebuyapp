package com.example.admin.ebuy.list;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ListCategoryProductAdapter;
import com.example.admin.ebuy.adapter.ListProductDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.model.respon.StoreResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.MyGridview;

import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListProductFragment extends BaseFragment {
    public static final String TAG = "ListProductFragment";
    private RecyclerView recyclerViewProduct;
    private ListCategoryProductAdapter listCategoryProductAdapter;
    GridLayoutManager gridLayoutManager;
    TextView txtDanhMuc,txtSanPham;
    ListProductDetailAdapter listProductDetailAdapter;
    MyGridview gridViewProduct;
    @Override

    protected int getLayoutResourceId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {
        recyclerViewProduct = (RecyclerView) view.findViewById(R.id.recyclerviewPro);
        txtDanhMuc = (TextView)view.findViewById(R.id.txtDanhmuc);
        txtSanPham = (TextView)view.findViewById(R.id.txtSanPham);
        gridViewProduct = (MyGridview)view.findViewById(R.id.gridListProduct);
        ((MyGridview) gridViewProduct).setExpanded(true);
        listProductDetailAdapter = new ListProductDetailAdapter(null,this);
        txtSanPham.setText("Gợi ý");
        txtDanhMuc.setText("Cửa hàng");

        listCategoryProductAdapter = new ListCategoryProductAdapter(this, null);
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);

        getAllStore();
        Random rd = new Random();
        getListProductDetailByType(rd.nextInt(6));

}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            Random rd = new Random();
            getListProductDetailByType(rd.nextInt(6));
        }
    }

    @Override
    public String getTagName() {
        return TAG;
    }

    private void getAllStore() {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getAllCustomer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoreResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StoreResponse storeResponse) {
                        WriteLog.e("TAG", storeResponse.toString());
//

                        recyclerViewProduct.setHasFixedSize(true);
                        recyclerViewProduct.setLayoutManager(gridLayoutManager);
                        listCategoryProductAdapter.setListStore(storeResponse.getData());
                        recyclerViewProduct.setAdapter(listCategoryProductAdapter);


                    }
                });
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
                        WriteLog.e("TOAN123", productDetailResponse.toString());


                        listProductDetailAdapter.setLisProductDetail(productDetailResponse.getData());
                        gridViewProduct.setAdapter(listProductDetailAdapter);


                    }
                });
    }

}
