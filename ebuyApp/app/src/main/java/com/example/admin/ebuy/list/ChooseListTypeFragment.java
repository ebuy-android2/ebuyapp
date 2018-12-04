package com.example.admin.ebuy.list;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.ChooseListAdpater;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.respon.TypeResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.WriteLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseListTypeFragment extends BaseFragment {
    public final static String TAG="ChooseListTypeFragment";
    private RecyclerView recyclerView;
    private ChooseListAdpater chooseListAdpater;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.list_type_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        savedInstanceState = getActivity().getIntent().getExtras();
        int data= savedInstanceState.getInt("id");
        String name = savedInstanceState.getString("name");

        ((BaseActivity)getActivity()).setVisibleFinish(false);
        ((BaseActivity)getActivity()).setVisibleBack(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycleViewList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        chooseListAdpater = new ChooseListAdpater(this);

        getType(data);
    }

    @Override
    public String getTagName() {
        return TAG;
    }

    void getType(int type)
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getType(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TypeResponse typeResponse) {
                        WriteLog.e("TAG", typeResponse.toString());



                        chooseListAdpater.setListType(typeResponse.getData());
                        recyclerView.setAdapter(chooseListAdpater);
                    }

                });
    }
}
