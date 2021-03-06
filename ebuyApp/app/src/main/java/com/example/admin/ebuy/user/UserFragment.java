package com.example.admin.ebuy.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.adapter.ListProductAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.home.ShopDetailFragment;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.location.MapsFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.CustomerData;
import com.example.admin.ebuy.model.respon.CustomerRespose;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends BaseFragment implements View.OnClickListener {
    public final static String TAG="UserFragment";
    private ImageView btnSetting;
    private CircleImageView imageAvatar;
    private EBCustomFont btnLogin, btnRegister, txtUsername, txtBuy, txtSale, line1, line2, btnDelivering, btnAddProductDetail, btnSeeShop;
    private LinearLayout linearLayoutBuy, linearLayoutSale;
    private CustomerData customerData;
    private LinearLayoutManager linearLayoutManagerHorizontal;
    private RecyclerView recyclerView;
    private ListProductAdapter listProductAdapter;
    private RelativeLayout relativerOrder;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {
        
        txtUsername = (EBCustomFont)view.findViewById(R.id.txtUserName);
        btnSetting = (ImageView)view.findViewById(R.id.btnSetting);
        imageAvatar = (CircleImageView) view.findViewById(R.id.imageAvatar);
        btnLogin = (EBCustomFont)view.findViewById(R.id.btnLogin);
        btnRegister = (EBCustomFont)view.findViewById(R.id.btnRegister);
        txtBuy = (EBCustomFont)view.findViewById(R.id.txtBuy);
        txtSale = (EBCustomFont)view.findViewById(R.id.txtSale);
        line1 = (EBCustomFont)view.findViewById(R.id.line1);
        line2 = (EBCustomFont)view.findViewById(R.id.line2);
        btnDelivering = (EBCustomFont)view.findViewById(R.id.btnDelivering);
        btnAddProductDetail = (EBCustomFont)view.findViewById(R.id.btnAddProductDetail);
        linearLayoutBuy = (LinearLayout)view.findViewById(R.id.linearLayoutBuy);
        linearLayoutSale = (LinearLayout)view.findViewById(R.id.linearLayoutSale);
        btnSeeShop = (EBCustomFont)view.findViewById(R.id.seeShop);
        relativerOrder = view.findViewById(R.id.relativerOrder);

        listProductAdapter = new ListProductAdapter(this);
        linearLayoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = view.findViewById(R.id.recyclerviewPro);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imageAvatar.setOnClickListener(this);
        txtBuy.setOnClickListener(this);
        txtSale.setOnClickListener(this);
        btnDelivering.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnAddProductDetail.setOnClickListener(this);
        btnSeeShop.setOnClickListener(this);
        if (CurrentUser.isLogin() || !CurrentUser.getUserInfo().getAccessToken().isEmpty()) {
            relativerOrder.setOnClickListener(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        (new Handler()).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if (CurrentUser.isLogin() || !CurrentUser.getUserInfo().getAccessToken().isEmpty()) {
                    btnLogin.setVisibility(View.INVISIBLE);
                    btnRegister.setVisibility(View.INVISIBLE);
                    if (CurrentUser.getUserInfo().getAvatar()!=null&&!CurrentUser.getUserInfo().getAvatar().isEmpty()){
                        Picasso.with(getContext())
                                .load(CurrentUser.getUserInfo().getAvatar())
                                .placeholder(R.drawable.logo)
                                .error(R.drawable.logo)
                                .into(imageAvatar);
                    }else {
                        imageAvatar.setImageResource(R.drawable.logo);
                    }
                    txtUsername.setText(CurrentUser.getUserInfo().getUserName());
                    getCustomerByID(CurrentUser.getUserInfo().getId());
                    getListProductDetailByIdCustomer(CurrentUser.getUserInfo().getId());
                }
                else {
                    btnLogin.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
                    txtUsername.setText(getResources().getString(R.string.username));
                    imageAvatar.setImageResource(R.drawable.logo);
                }
            }
        },2000);
    }

    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSetting:
                if (!CurrentUser.isLogin() || CurrentUser.getUserInfo().getAccessToken().isEmpty()){
                    Navigator.getInstance().startFragment(getContext(), LoginFragment.TAG, UserActivity.class, null);
                }else {
                    chooseSetting();
                }
                break;
            case R.id.btnLogin:
                Navigator.getInstance().startFragment(getContext(),LoginFragment.TAG, UserActivity.class,null);
                break;
            case R.id.btnRegister:
                Navigator.getInstance().startFragment(getContext(),RegisterFragment.TAG, UserActivity.class,null);
                break;

            case R.id.txtBuy:
                linearLayoutBuy.setVisibility(View.VISIBLE);
                linearLayoutSale.setVisibility(View.GONE);
                txtBuy.setTextColor(getResources().getColor(R.color.color_main));
                txtSale.setTextColor(getResources().getColor(R.color.black));
                line1.setVisibility(View.VISIBLE);
                line2.setVisibility(View.INVISIBLE);
                break;
            case R.id.txtSale:
                linearLayoutSale.setVisibility(View.VISIBLE);
                linearLayoutBuy.setVisibility(View.GONE);
                txtSale.setTextColor(getResources().getColor(R.color.color_main));
                txtBuy.setTextColor(getResources().getColor(R.color.black));
                line2.setVisibility(View.VISIBLE);
                line2.setBackgroundColor(getResources().getColor(R.color.color_main));
                line1.setVisibility(View.INVISIBLE);
                break;
            case R.id.btnDelivering:
                Bundle bundle = new Bundle();
                bundle.putString("LocationShop","Trần Trọng Kim, Phường 22, Bình Thạnh, Hồ Chí Minh, Việt Nam");
                bundle.putString("Title",getResources().getString(R.string.delivering));
                bundle.putInt("Type", 2);
                Navigator.getInstance().startFragment(getContext(), MapsFragment.TAG, SupportActivity.class, bundle);
                break;
            case R.id.btnAddProductDetail:
                Navigator.getInstance().startFragment(getContext(), AddProductFragment.TAG, UserActivity.class, null);
                break;
            case R.id.seeShop:
                Bundle bundle1 = new Bundle();
                Gson gson = new Gson();
                String data = gson.toJson(customerData);
                bundle1.putString("data",data);
                Navigator.getInstance().startFragment(getContext(), ShopDetailFragment.TAG, SupportActivity.class,bundle1);
                break;
            case R.id.relativerOrder:
                Navigator.getInstance().startFragment(getContext(), ManageOrderFragment.TAG, SupportActivity.class, null);
                break;
        }
    }
    // btnSetting
    private void chooseSetting(){
        PopupMenu popupMenu = new PopupMenu(getContext(),btnSetting);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuEdit:
                        Navigator.getInstance().startFragment(getContext(), EditProfileFragment.TAG, UserActivity.class, null);
                        break;
                    case R.id.menuLogout:
                        CurrentUser.saveUserInfo(null);
                        CurrentUser.setUserInfo(null);
                        onResume();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();

    }
    private void getCustomerByID(int id) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getCustomerById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CustomerRespose>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CustomerRespose customerRespose) {
                        customerData = customerRespose.getData();
                    }
                });
    }

    private void getListProductDetailByIdCustomer(int id) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
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

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(linearLayoutManagerHorizontal);

                        listProductAdapter.setListProduct(productDetailResponse.getData());
                        recyclerView.setAdapter(listProductAdapter);

                    }
                });
    }

}
