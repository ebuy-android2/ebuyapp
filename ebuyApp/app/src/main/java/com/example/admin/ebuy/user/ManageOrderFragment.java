package com.example.admin.ebuy.user;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ManageOrderAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.respon.ManageOrderResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.view.EBCustomFont;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Donald Trieu on 1/3/2019.
 */
public class ManageOrderFragment extends BaseFragment{
    public final static String TAG="ManageOrderFragment";
    private RecyclerView recyclerView;
    private ManageOrderAdapter manageOrderAdapter;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_orderdetail_sale;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setVisibleBack(true);
        ((BaseActivity)getActivity()).setTitle(true, "Quản lý đơn hàng");

        recyclerView = view.findViewById(R.id.recyclerviewOrderDetail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        manageOrderAdapter = new ManageOrderAdapter(this);
        getAllOrderDetailOfCustomerSale(CurrentUser.getUserInfo().getId());
    }


    public void getAllOrderDetailOfCustomerSale(int id){
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getAllOrderDetailOfCustomerSale(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ManageOrderResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ManageOrderResponse manageOrderResponse) {

                        manageOrderAdapter.setManageOrders(manageOrderResponse.getManageOrders());
                        recyclerView.setAdapter(manageOrderAdapter);
                    }
                });


    }
    @Override
    public String getTagName() {
        return TAG;
    }
}
