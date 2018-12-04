package com.example.admin.ebuy.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.adapter.FeedbackAdapter;
import com.example.admin.ebuy.adapter.HomeAdapter;
import com.example.admin.ebuy.adapter.ListProductAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.location.MapsFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.ProductDetailData;
import com.example.admin.ebuy.model.request.AddOrderDetailRequest;
import com.example.admin.ebuy.model.request.UpdateProfileRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.model.respon.CustomerRespose;
import com.example.admin.ebuy.model.respon.FeedBackResponse;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.UpdateProfileResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.shopping.ShoppingFragment;
import com.example.admin.ebuy.user.LoginFragment;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductDetailFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "ProductDetailFragment";
    EBCustomFont txtpricePro, txtDanhmuc, txtThuonhieu, txtChatlieu, txtGuitu, txtDetailPro, txtNumLike, txtNumStar;
    TextView txtNamePro, txtNameShop, txtAddressShop, txtBuynow;
    CircleImageView imgAvatarShop;
    ImageView imgview, imgNagavition;
    FeedbackAdapter feedbackAdapter;
    RecyclerView recyclerViewCommet, recyclerViewPro;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerHorizontal;

    ListProductAdapter listProductAdapter;
    LinearLayout linearLayout, addProduct;
    GestureDetector gestureDetector;
    int SWIPE_THERSHOLD = 70;
    int SWIPE_VELOCITY = 70;
    RatingBar ratingBar;
    ProductDetailData productDetailData;
    String address="";
    @Override
    protected int getLayoutResourceId() {
        return R.layout.product_detail_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity) getActivity()).setVisibleFinish(false);

        mapped(view);
        Gson gson = new Gson();
        savedInstanceState = getActivity().getIntent().getExtras();
        productDetailData = gson.fromJson(savedInstanceState.getString("data").toString(), ProductDetailData.class);
        Picasso.with(getContext())
                .load(productDetailData.getImage())
                .placeholder(R.mipmap.logo)
                .into(imgview);
        txtThuonhieu.setText(productDetailData.getTrademark());
        txtpricePro.setText(productDetailData.getPrice() + "");
        txtNamePro.setText(productDetailData.getName());
        txtGuitu.setText(productDetailData.getAddress());
        txtChatlieu.setText(productDetailData.getMaterial());
        txtDetailPro.setText(productDetailData.getDescribe());
        txtNumLike.setText(productDetailData.getNumLike() + "");
        txtNumStar.setText("(" + productDetailData.getNumFeedback() + ")");
        ratingBar.setRating(productDetailData.getNumStar());


        feedbackAdapter = new FeedbackAdapter(this);
        linearLayoutManagerHorizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getContext(), linearLayoutManager.getOrientation());
        recyclerViewCommet.addItemDecoration(dividerItemDecoration);


        listProductAdapter = new ListProductAdapter(this);


        ((BaseActivity) getActivity()).setTitle(true, productDetailData.getName());
        gestureDerector();
        getCustomerByID(productDetailData.getId_product());
        getFeedbackByIDProduct(productDetailData.getId_product());
        getListProductDetailByTypeProduct(productDetailData.getId_type());

        imgNagavition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.isEmpty()){
                    Toast.makeText(getContext(), "Shop chưa cập nhật địa chỉ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("LocationShop",txtAddressShop.getText().toString());
                    bundle.putString("Title",getResources().getString(R.string.location_shop));
                    bundle.putInt("Type", 1);
                    Navigator.getInstance().startFragment(getContext(), MapsFragment.TAG, SupportActivity.class,bundle);
                }
            }
        });
    }

    @Override
    public String getTagName() {
        return TAG;
    }

    private void mapped(View view) {
        imgview = (ImageView) view.findViewById(R.id.imgview);
        imgNagavition = (ImageView) view.findViewById(R.id.btnNavigation);
        txtChatlieu = (EBCustomFont) view.findViewById(R.id.txtChatlieu);
        txtDanhmuc = (EBCustomFont) view.findViewById(R.id.txtDanhmuc);
        txtDetailPro = (EBCustomFont) view.findViewById(R.id.txtDetailPro);
        txtGuitu = (EBCustomFont) view.findViewById(R.id.txtGuitu);
        txtNamePro = (TextView) view.findViewById(R.id.txtNamePro);
        txtpricePro = (EBCustomFont) view.findViewById(R.id.txtpricePro);
        txtThuonhieu = (EBCustomFont) view.findViewById(R.id.txtThuonhieu);
        txtNameShop = (EBCustomFont) view.findViewById(R.id.txtNameShop);
        txtAddressShop = (EBCustomFont) view.findViewById(R.id.txtAddress);
        imgAvatarShop = (CircleImageView) view.findViewById(R.id.imgAvatarShop);
        recyclerViewCommet = (RecyclerView) view.findViewById(R.id.recyFeedbackList);
        recyclerViewPro = (RecyclerView) view.findViewById(R.id.recyclerviewPro);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        txtNumLike = (EBCustomFont) view.findViewById(R.id.txtNumLike);
        txtNumStar = (EBCustomFont) view.findViewById(R.id.txtNumStar);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        txtBuynow = (TextView) view.findViewById(R.id.txtBuynow);
        addProduct = (LinearLayout) view.findViewById(R.id.addProduct);
        addProduct.setOnClickListener(this);
        txtBuynow.setOnClickListener(this);

    }

    private void gestureDerector() {
        gestureDetector = new GestureDetector(getActivity(), new ProductDetailFragment.MyGesture());
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                gestureDetector.onTouchEvent(motionEvent);

                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtBuynow:
                if (!CurrentUser.isLogin() || CurrentUser.getUserInfo().getAccessToken().isEmpty()) {
                    Navigator.getInstance().startFragment(getContext(), LoginFragment.TAG, UserActivity.class, null);
                } else {
                    addOrderDetail();
                    Bundle bundle;
                    bundle = new Bundle();
                    bundle.putInt("data", 2);
                    Navigator.getInstance().startActivity(getContext(), HomeActivity.class, bundle);
                    getActivity().finish();
                }
                break;
            case R.id.addProduct:
                addOrderDetail();
                break;

        }
    }

    public class MyGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e2.getX() - e1.getX() > SWIPE_THERSHOLD && Math.abs(velocityX) > SWIPE_VELOCITY) {
                getActivity().onBackPressed();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void getCustomerByID(int id) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getCustomerByIdProduct(id)
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
                        address = customerRespose.getData().getAddress();
                        txtAddressShop.setText(customerRespose.getData().getAddress());
                        txtNameShop.setText(customerRespose.getData().getName());
                        Picasso.with(getContext())
                                .load(customerRespose.getData().getImg())
                                .placeholder(R.mipmap.logo)
                                .into(imgAvatarShop);

                    }
                });


    }

    private void getFeedbackByIDProduct(int id) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getFeedbackByIdProduct(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FeedBackResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FeedBackResponse feedBackResponse) {
                        WriteLog.e("TAG", feedBackResponse.toString());
                        recyclerViewCommet.setHasFixedSize(true);
                        recyclerViewCommet.setLayoutManager(linearLayoutManager);
                        feedbackAdapter.setListFeedBackData(feedBackResponse.getData());
                        recyclerViewCommet.setAdapter(feedbackAdapter);
                    }
                });

    }

    private void getListProductDetailByTypeProduct(int data) {
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
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
                        recyclerViewPro.setHasFixedSize(true);
                        recyclerViewPro.setLayoutManager(linearLayoutManagerHorizontal);

                        listProductAdapter.setListProduct(productDetailResponse.getData());
                        recyclerViewPro.setAdapter(listProductAdapter);


                    }
                });
    }

    private void addOrderDetail() {
        AddOrderDetailRequest addOrderDetailRequest = new AddOrderDetailRequest();
        addOrderDetailRequest.setId(productDetailData.getId_detail());
        addOrderDetailRequest.setName(productDetailData.getName());
        addOrderDetailRequest.setAmount(productDetailData.getPrice());
        addOrderDetailRequest.setPrice(productDetailData.getPrice());
        addOrderDetailRequest.setStatus(false);
        addOrderDetailRequest.setQuantity(1);
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .addOrderDetail(CurrentUser.getUserInfo().getId(), addOrderDetailRequest)
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
                        Toast.makeText(getContext(), "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}