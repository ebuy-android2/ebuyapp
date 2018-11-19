package com.example.admin.ebuy.user;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.home.activity.HomeActivity;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.request.LoginRequest;
import com.example.admin.ebuy.model.respon.ConfigResponse;
import com.example.admin.ebuy.model.respon.UserResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends BaseFragment implements View.OnClickListener{
    public final static String TAG="LoginFragment";
    private Button btnLogin, btnRegister;
    private EditText txtUserName, txtPassword;
    private EBCustomFont btnforgotPass;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.login_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setTitle(true, getResources().getString(R.string.login));
        ((BaseActivity)getActivity()).setVisibleBack(true);
        ((BaseActivity)getActivity()).setVisibleFinish(false);

        txtUserName = (EditText)view.findViewById(R.id.txtUserName);
        txtPassword = (EditText)view.findViewById(R.id.txtPassword);
        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        btnRegister = (Button)view.findViewById(R.id.btnRegister);
        btnforgotPass = (EBCustomFont) view.findViewById(R.id.btnForgotPass);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        getConfig();

    }

    @Override
    public String getTagName() {
        return TAG;
    }

    private void getConfig(){
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .getConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConfigResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    WriteLog.e("TAG", e.getMessage().toString());
                }

                @Override
                public void onNext(ConfigResponse configResponse) {

                    WriteLog.e("TAG", configResponse.toString());
                    if(configResponse.getReplyCode()!= AppConfig.SUCCESS_CODE){

                    }else {
                        PrefUtils.getInstance().putString(CurrentUser.TOKEN_NO_LOGIN, configResponse.getData().getApiKey());

                    }
                }
        });
    }
    private boolean checkValidate() {
        if (txtUserName.getText().toString().isEmpty()) {
            alertError(getResources().getString(R.string.empty_user_name), SweetAlertDialog.WARNING_TYPE, getResources().getString(R.string.warning));
            return false;
        }
        if (txtPassword.getText().toString().isEmpty()) {
            alertError(getResources().getString(R.string.empty_pin), SweetAlertDialog.WARNING_TYPE, getResources().getString(R.string.warning));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                if (checkValidate()){
                    ((BaseActivity)getActivity()).setLoading(true);
                    byte[] data = new byte[0];
                    String base64="";
                    try {
                        data = txtPassword.getText().toString().getBytes("UTF-8");
                        base64 = Base64.encodeToString(data, Base64.NO_WRAP | Base64.URL_SAFE);
                        WriteLog.e("Triệu", base64);
                        Toast.makeText(getContext(), base64,Toast.LENGTH_SHORT).show();
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                    ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                            .customerLogin(new LoginRequest(txtUserName.getText().toString(), base64))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserResponse>() {
                        @Override
                        public void onCompleted() {
                            if (LoginFragment.this != null && getActivity() != null && isAdded())
                                ((BaseActivity) getActivity()).setLoading(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (LoginFragment.this != null && getActivity() != null && isAdded())
                                ((BaseActivity) getActivity()).setLoading(false);
                            alertError(e.getMessage(), SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.error));
                            onErrorReceive(e);
                        }

                        @Override
                        public void onNext(UserResponse userResponse) {
                            WriteLog.e("Triệu", userResponse.toString());

                            if (userResponse.getReplyCode() != AppConfig.SUCCESS_CODE) {
                                handlerError(userResponse);
                                alertError(userResponse.getReplyText(), SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.error));
                            } else {
                                    CurrentUser.setUserInfo(userResponse.getUser());
                                    CurrentUser.saveUserInfo(userResponse.getUser());

//                                    ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.user_fragment, null);
//                                    EBCustomFont btnLogin = (EBCustomFont)viewGroup.findViewById(R.id.btnLogin);
//                                    btnLogin.setVisibility(View.INVISIBLE);
//                                    EBCustomFont btnRegister = (EBCustomFont)viewGroup.findViewById(R.id.btnRegister);
//                                    btnRegister.setVisibility(View.INVISIBLE);

                                   getActivity().finish();

//                                    LayoutInflater layoutInflater = LayoutInflater.from(self.getContext());
//                                    View view = layoutInflater.inflate(R.layout.user_fragment,null,false);
//                                    view.findViewById(R.id.btnLogin).setVisibility(View.INVISIBLE);
//                                    view.findViewById(R.id.btnRegister).setVisibility(View.INVISIBLE);

//                                    Navigator.getInstance().startFragment();
//                                    getActivity().finish();
                            }
                        }
                    });
                }
                break;
            case R.id.btnRegister:
                Navigator.getInstance().startFragment(getContext(), RegisterFragment.TAG, UserActivity.class, null);
                getActivity().finish();
                break;
            case R.id.btnForgotPass:
                break;

        }
    }
}
