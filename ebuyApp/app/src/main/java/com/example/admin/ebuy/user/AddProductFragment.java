package com.example.admin.ebuy.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.list.ChooseListProductFragment;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.view.EBCustomFont;

public class AddProductFragment extends BaseFragment implements View.OnClickListener{
    public final static String TAG="AddProductFragment";
    private ImageView imageProduct;
    private EditText edtNameProdcut, edtDescribe, edtTrade, edtMaterial, edtPrice, edtCount, edtWeight, edtStatus;
    private RelativeLayout btnListProduct;
    private EBCustomFont txtTypeProduct;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_product_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setVisibleFinish(true);
        ((BaseActivity)getActivity()).setVisibleBack(true);
        ((BaseActivity)getActivity()).setTitle(true, getResources().getString(R.string.buy));

        imageProduct = (ImageView)view.findViewById(R.id.imageProduct);
        edtNameProdcut = (EditText)view.findViewById(R.id.edtNameProdcut);
        edtDescribe = (EditText)view.findViewById(R.id.edtDescribe);
        edtTrade = (EditText)view.findViewById(R.id.edtTrade);
        edtMaterial = (EditText)view.findViewById(R.id.edtMaterial);
        edtPrice = (EditText)view.findViewById(R.id.edtPrice);
        edtCount = (EditText)view.findViewById(R.id.edtCount);
        edtWeight = (EditText)view.findViewById(R.id.edtWeight);
        edtStatus = (EditText)view.findViewById(R.id.edtStatus);
//        btnListProduct = (RelativeLayout)view.findViewById(R.id.btnListProduct);
        txtTypeProduct = (EBCustomFont)view.findViewById(R.id.txtTypeProduct);

        if (savedInstanceState!=null) {
            savedInstanceState = getActivity().getIntent().getExtras();
            int data = savedInstanceState.getInt("id");
            String name = savedInstanceState.getString("name");
            txtTypeProduct.setText(name);
        }
        txtTypeProduct.setText("");

        txtTypeProduct.setOnClickListener(this);

    }

    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtTypeProduct:
                Navigator.getInstance().startFragment(getContext(), ChooseListProductFragment.TAG, SupportActivity.class, null);
                break;
        }
    }

}
