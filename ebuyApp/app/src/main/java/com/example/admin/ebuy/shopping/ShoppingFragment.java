package com.example.admin.ebuy.shopping;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.OrderDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.OrderDetailData;
import com.example.admin.ebuy.model.request.CreateOrderRequest;
import com.example.admin.ebuy.model.request.OrderDetailRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.model.respon.OrderDetailResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShoppingFragment extends BaseFragment implements View.OnClickListener{
    public static final String TAG="ShoppingFragment";
    RecyclerView recyclerView;
    EBCustomFont sumPrice,btnLogin;
    TextView txtBuynow;
    OrderDetailAdapter orderDetailAdapter;
    LinearLayout linearLayout;
    CheckBox checkAll;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.shopping_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity)getActivity()).setVisibleFinish(false);
        mapped(view);
        WriteLog.e("toan",""+CurrentUser.getUserInfo().getId());

        orderDetailAdapter = new OrderDetailAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);



    }

    @Override
    public void onResume() {
        super.onResume();
        if (!CurrentUser.getUserInfo().getAccessToken().isEmpty() || CurrentUser.isLogin())
        {
            getOrderDetail();


        }
        else {
            Toast.makeText(getContext(), "Vui lòng đăng nhập!!", Toast.LENGTH_SHORT).show();
            orderDetailAdapter.setListOrder(new ArrayList<OrderDetailData>());
        }
    }

    @Override
    public String getTagName() {
        return TAG;
    }
    private void mapped(View view)
    {
        recyclerView = view.findViewById(R.id.recyclerviewOrder);
        sumPrice = view.findViewById(R.id.txtSumprice);
        txtBuynow = view.findViewById(R.id.txtBuynow);
        txtBuynow.setOnClickListener(this);
        linearLayout = view.findViewById(R.id.shopping);
        checkAll = view.findViewById(R.id.checkAll);
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                orderDetailAdapter.setCheckAll(b);
                getOrderDetail();
            }
        });



    }
    void getOrderDetail()
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getOrderDetail(CurrentUser.getUserInfo().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("toan","Erro");
                    }

                    @Override
                    public void onNext(OrderDetailResponse orderDetailResponse) {
                        WriteLog.e("TAG", orderDetailResponse.toString());
                        orderDetailAdapter.setListOrder(orderDetailResponse.getData());
                        recyclerView.setAdapter(orderDetailAdapter);

                    }
                });
    }
    private void addOrderDetail() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        ArrayList<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
        SparseIntArray sparseIntArray = orderDetailAdapter.getItemStateArray();
        for(int i=0;i<sparseIntArray.size();i++)
        {
           orderDetailRequests.add(new OrderDetailRequest(sparseIntArray.keyAt(i))) ;
        }
        Toast.makeText(getContext(), orderDetailRequests.toString(), Toast.LENGTH_SHORT).show();
        createOrderRequest.setAddress("abc");
        createOrderRequest.setId_city(1);
        createOrderRequest.setId_district(1);
        createOrderRequest.setId_ward(1);
        createOrderRequest.setStreetName("name");
        createOrderRequest.setFee(100000);
        createOrderRequest.setAmount(300000);
        createOrderRequest.setTotal_amount(300000);
        createOrderRequest.setOrderDetail(orderDetailRequests);
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .createOrder(CurrentUser.getUserInfo().getId(), createOrderRequest)
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
                        Toast.makeText(getContext(), "Đã gửi yêu cầu", Toast.LENGTH_SHORT).show();
                        getOrderDetail();
                    }
                });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.txtBuynow:

                addOrderDetail();
                break;

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            onResume();
        }
    }
}
