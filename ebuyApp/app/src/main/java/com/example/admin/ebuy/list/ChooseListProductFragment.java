package com.example.admin.ebuy.list;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ChooseListAdpater;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseListProductFragment extends BaseFragment {
    public final static String TAG="ChooseListProductFragment";

    private RecyclerView recyclerView;
    private ChooseListAdpater chooseListAdpater;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.list_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setVisibleFinish(false);
        ((BaseActivity)getActivity()).setVisibleBack(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleViewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        chooseListAdpater = new ChooseListAdpater(this);
        getListProduct();
    }

    @Override
    public String getTagName() {
        return TAG;
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
                        recyclerView.setHasFixedSize(true);

                        chooseListAdpater.setListCategoryProduct(productResponse.getData());
                        recyclerView.setAdapter(chooseListAdpater);


                    }
                });

    }
}
