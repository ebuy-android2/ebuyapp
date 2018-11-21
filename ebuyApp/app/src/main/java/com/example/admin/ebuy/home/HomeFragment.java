package com.example.admin.ebuy.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ListCategoryProductAdapter;
import com.example.admin.ebuy.adapter.ListProductDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.MyGridview;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import rx.Observer;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

public class HomeFragment extends BaseFragment implements ObservableScrollViewCallbacks{
    public static final String TAG="HomeFragment";
    
    private ImageView imageView;
    private RecyclerView recyclerViewProduct;
    private ListCategoryProductAdapter listCategoryProductAdapter;
    LinearLayoutManager linearLayoutManager;
    private GridView gridViewProduct;
    private ListProductDetailAdapter listProductDetailAdapter;
    GridLayoutManager gridLayoutManager;
    ObservableScrollView observableScrollView;
    RelativeLayout rlHeader;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.home_fragment;
    }


    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

//        imageView = (ImageView)view.findViewById(R.id.custom_image);
//        Picasso.with(getContext())
//                .load("http://sv1.upsieutoc.com/2018/10/18/thoitrangnam.png")
//                .placeholder(R.mipmap.ic_launcher)
//                .into(imageView);
        rlHeader = (RelativeLayout)container.findViewById(R.id.rlHeader);
//        observableScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView) ;
//        observableScrollView.setScrollViewCallbacks(this);
        gridViewProduct = (MyGridview)view.findViewById(R.id.gridListProduct);
        ((MyGridview) gridViewProduct).setExpanded(true);
        recyclerViewProduct = (RecyclerView)view.findViewById(R.id.recyclerviewPro);
        listProductDetailAdapter = new ListProductDetailAdapter(null,this);
        listCategoryProductAdapter = new ListCategoryProductAdapter(this,null);
        gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
//       linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), linearLayoutManager.getOrientation());
//        recyclerViewProduct.addItemDecoration(dividerItemDecoration);
        ((BaseActivity)getActivity()).setTitle(true,"trang chủ");
        ((BaseActivity)getActivity()).setVisibleFinish(false);

        getListProduct();
        getListProductDetail();



    }

    @Override
    public String getTagName() {
        return TAG;
    }

    private void getListProductDetail()
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getProductDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                       onError(e);
                    }

                    @Override
                    public void onNext(ProductDetailResponse productDetailResponse) {
                        WriteLog.e("TAG", productDetailResponse.toString());
                        listProductDetailAdapter.setLisProductDetail(productDetailResponse.getData());
                        gridViewProduct.setAdapter(listProductDetailAdapter);


                    }
                });
    }
    private void getListProduct()
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread() )
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onCompleted() {
                        WriteLog.e("TAG", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("TAG", "erro");
                    }

                    @Override
                    public void onNext(ProductResponse productResponse) {
                        WriteLog.e("TAG", productResponse.toString());
                        recyclerViewProduct.setHasFixedSize(true);


                        recyclerViewProduct.setLayoutManager(gridLayoutManager);
                        listCategoryProductAdapter.setListCategoryProduct(productResponse.getData());
                        recyclerViewProduct.setAdapter(listCategoryProductAdapter);


                    }
                });

    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (scrollState == ScrollState.UP) {
//            rlHeader.setVisibility(View.VISIBLE);
//            homeActivity.rlHeader.setVisibility(View.VISIBLE);
        } else if (scrollState == ScrollState.DOWN) {
//            rlHeader.setVisibility(View.GONE);
//            homeActivity.rlHeader.setVisibility(View.GONE);
        }
    }
}
