package com.example.admin.ebuy.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.User;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.view.EBCustomFont;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends BaseFragment implements View.OnClickListener {
    public final static String TAG="UserFragment";
    private ImageView btnSetting;
    private CircleImageView imageAvatar;
    private EBCustomFont btnLogin, btnRegister, txtUsername;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        btnSetting = (ImageView)view.findViewById(R.id.btnSetting);
        imageAvatar = (CircleImageView) view.findViewById(R.id.imageAvatar);
        btnLogin = (EBCustomFont)view.findViewById(R.id.btnLogin);
        btnRegister = (EBCustomFont)view.findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSetting:
                break;
            case R.id.btnLogin:
                Navigator.getInstance().startFragment(getContext(),LoginFragment.TAG, UserActivity.class,null);
                getActivity().finish();
                break;
            case R.id.btnRegister:
                Navigator.getInstance().startFragment(getContext(),RegisterFragment.TAG, UserActivity.class,null);
                getActivity().finish();
                break;
            case R.id.imageAvatar:
                break;
        }
    }
}
