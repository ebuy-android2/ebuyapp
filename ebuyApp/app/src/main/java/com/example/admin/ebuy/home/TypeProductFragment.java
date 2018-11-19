package com.example.admin.ebuy.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ListCategoryProductAdapter;
import com.example.admin.ebuy.adapter.ListProductDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.TypeProductResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.MyGridview;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TypeProductFragment extends BaseFragment {
    public static final String TAG="TypeProductFragment";
    private RecyclerView recyclerViewProduct;
    private GridView gridViewProduct;
    private ListProductDetailAdapter listProductDetailAdapter;

    private ListCategoryProductAdapter listCategoryProductAdapter;
    private LinearLayoutManager linearLayoutManager;
    GestureDetector gestureDetector;
    int SWIPE_THERSHOLD=70;
    int SWIPE_VELOCITY =70;
    LinearLayout linearLayout;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setVisibleFinish(false);

        savedInstanceState = getActivity().getIntent().getExtras();
        int data= savedInstanceState.getInt("id");
        String name = savedInstanceState.getString("name");

        gridViewProduct = (MyGridview)view.findViewById(R.id.gridListProduct);
        ((MyGridview) gridViewProduct).setExpanded(true);
        recyclerViewProduct = (RecyclerView)view.findViewById(R.id.recyclerviewPro);
        listProductDetailAdapter = new ListProductDetailAdapter(null,this);
        listCategoryProductAdapter = new ListCategoryProductAdapter(this,null);
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout);

        ((BaseActivity)getActivity()).setTitle(true,name);
        gestureDerector();
        getTypeProduct(data);
        getListProductDetailByTypeProduct(data);
    }

    @Override
    public String getTagName() {
        return TAG;
    }
    private void gestureDerector()
    {
        gestureDetector = new GestureDetector(getActivity(),new TypeProductFragment.MyGesture());
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                gestureDetector.onTouchEvent(motionEvent);

                return true;
            }
        });
    }
    public class MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if(e2.getX() - e1.getX() >SWIPE_THERSHOLD && Math.abs(velocityX) >SWIPE_VELOCITY)
            {
                getActivity().onBackPressed();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
    void getTypeProduct(int type)
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getTypeProduct(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeProductResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("abc", "errro");
                    }

                    @Override
                    public void onNext(TypeProductResponse typeProductResponse) {
                        WriteLog.e("abc", typeProductResponse.toString());
                        recyclerViewProduct.setHasFixedSize(true);
                        recyclerViewProduct.setLayoutManager(linearLayoutManager);
                        listCategoryProductAdapter.setListTypeProduct(typeProductResponse.getData());
                        recyclerViewProduct.setAdapter(listCategoryProductAdapter);
                    }

                });
    }
    private void getListProductDetailByTypeProduct(int data)
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getProductDetailByTypeProduct(data)
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
