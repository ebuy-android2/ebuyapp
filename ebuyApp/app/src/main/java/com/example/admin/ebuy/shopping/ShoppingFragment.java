package com.example.admin.ebuy.shopping;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.adapter.CityAdapter;
import com.example.admin.ebuy.adapter.OrderDetailAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.interfaces.ItemCheckListener;
import com.example.admin.ebuy.model.CityData;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.OrderDetailData;
import com.example.admin.ebuy.model.ProductData;
import com.example.admin.ebuy.model.request.CreateOrderRequest;
import com.example.admin.ebuy.model.request.OrderDetailRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.model.respon.CityResponse;
import com.example.admin.ebuy.model.respon.OrderDetailResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.ManagementCacheObject;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG="ShoppingFragment";
    RecyclerView recyclerView;
    EBCustomFont sumPrice,btnLogin;
    TextView txtBuynow;
    OrderDetailAdapter orderDetailAdapter;
    LinearLayout linearLayout;
    CheckBox checkAll;
    Spinner spnCity,spnCountry,spnWard;
    int idCity, idConuty,idWard;
    CityAdapter cityAdapter,countyAdapter,wardAdapter;
    EditText edtPhone,edtNumber,edtStreet;
    Button btnBuy;
    ImageView imgMenu;
    float totalPrice=0;



    @Override
    protected int getLayoutResourceId() {
        return R.layout.shopping_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {
        mapped(view);
        WriteLog.e("toan",""+CurrentUser.getUserInfo().getId());

        orderDetailAdapter = new OrderDetailAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        orderDetailAdapter.setItemCheckListener(new ItemCheckListener() {
            @Override
            public void onCheck(float price) {

                totalPrice=totalPrice+price;
                sumPrice.setText(totalPrice+"");
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        if (!CurrentUser.getUserInfo().getAccessToken().isEmpty() || CurrentUser.isLogin())
        {
            getOrderDetail();


        }
        else {
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
        imgMenu=((BaseActivity)getActivity()).findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseSetting();
            }
        });

        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    totalPrice = 0;
                    sumPrice.setText(totalPrice+"");
                }
                orderDetailAdapter.setCheckAll(b);
                getOrderDetail();
            }
        });



    }

    void buyDialog()
    {
        AlertDialog.Builder a = new AlertDialog.Builder(getActivity());
        final View dialogView = getLayoutInflater().inflate(R.layout.fragment_buy,null);
        a.setView(dialogView);
        final AlertDialog alertDialog =  a.create();
        alertDialog.show();

        spnCity = dialogView.findViewById(R.id.city);
        spnCountry = dialogView.findViewById(R.id.county);
        spnWard = dialogView.findViewById(R.id.ward);
        edtNumber = dialogView.findViewById(R.id.edtnumber);
        edtPhone = dialogView.findViewById(R.id.edtPhone);
        edtStreet = dialogView.findViewById(R.id.edtstreet);
        btnBuy = dialogView.findViewById(R.id.btnbuy);

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtNumber.getText().toString().isEmpty() && !edtPhone.getText().toString().isEmpty() && !edtStreet.getText().toString().isEmpty()) {
                    addOrderDetail();
                    alertDialog.dismiss();
                }
                else alertError("Vui lòng nhập đủ thông tin",SweetAlertDialog.ERROR_TYPE,"Thông báo");
            }
        });


        getCity();




    }
    public void getCity() {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        alertError("lỗi", SweetAlertDialog.ERROR_TYPE, "lỗi");
                    }

                    @Override
                    public void onNext(final CityResponse cityResponse) {
                        WriteLog.e("responseCity",cityResponse.toString());
                        cityAdapter = new CityAdapter(getContext(),android.R.layout.simple_spinner_item,cityResponse.getData());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnCity.setAdapter(cityAdapter);
                        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idCity = cityAdapter.getItem(position).getId();

                                getCounty();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

    }
    public void getCounty() {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getCounty(idCity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        alertError("lỗi", SweetAlertDialog.ERROR_TYPE, "lỗi");
                    }

                    @Override
                    public void onNext(final CityResponse cityResponse) {
                        WriteLog.e("responseCity",cityResponse.toString());
                        countyAdapter = new CityAdapter(getContext(),android.R.layout.simple_spinner_item,cityResponse.getData());
                        countyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnCountry.setAdapter(countyAdapter);
                        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idConuty = countyAdapter.getItem(position).getId();
                                getWard();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

    }
    public void getWard() {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getWard(idConuty)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        alertError("lỗi", SweetAlertDialog.ERROR_TYPE, "lỗi");
                    }

                    @Override
                    public void onNext(final CityResponse cityResponse) {
                        WriteLog.e("responseCity",cityResponse.toString());
                        wardAdapter = new CityAdapter(getContext(),android.R.layout.simple_spinner_item,cityResponse.getData());
                        wardAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        spnWard.setAdapter(wardAdapter);
                        spnWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idWard = wardAdapter.getItem(position).getId();

//                                getSubjects(classmates.getId() + "");

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

    }
    public void deleteOrder(int id) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .deleteOrder(CurrentUser.getUserInfo().getId(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        alertError("lỗi", SweetAlertDialog.ERROR_TYPE, "lỗi");
                    }

                    @Override
                    public void onNext(final BaseResponse baseResponse) {
                        WriteLog.e("responseCity",baseResponse.toString());

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

        createOrderRequest.setAddress(edtNumber.getText().toString());
        createOrderRequest.setId_city(idCity);
        createOrderRequest.setId_district(idConuty);
        createOrderRequest.setId_ward(idWard);
        createOrderRequest.setStreetName(edtStreet.getText().toString());
        createOrderRequest.setFee(0);
        createOrderRequest.setAmount(totalPrice);
        createOrderRequest.setTotal_amount(totalPrice);
        createOrderRequest.setPhonenumber(edtPhone.getText().toString());
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
                        alertError("Thông báo",SweetAlertDialog.SUCCESS_TYPE,"Đặt hàng thành công");
                        getOrderDetail();
                    }
                });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.txtBuynow:
                WriteLog.e("size",orderDetailAdapter.getItemStateArray().size()+"");
                if(orderDetailAdapter.getItemStateArray().size()>0)
                    buyDialog();
                else
                    alertError("Vui lòng chọn sản phẩm đặt hàng",SweetAlertDialog.ERROR_TYPE,"Thông báo");
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

    // btnSetting
    private void chooseSetting(){
        PopupMenu popupMenu = new PopupMenu(getContext(),imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_delete, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menudelete:

                        SparseIntArray sparseIntArray = orderDetailAdapter.getItemStateArray();
                        for(int i =0 ;i<sparseIntArray.size();i++)
                        {
                            deleteOrder(sparseIntArray.keyAt(i));
                        }
                        getOrderDetail();
                        totalPrice =0;


                        break;

                }
                return false;
            }
        });
        popupMenu.show();

    }

}
