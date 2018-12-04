package com.example.admin.ebuy.user;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.request.LoginRequest;
import com.example.admin.ebuy.model.request.RegisterRequest;
import com.example.admin.ebuy.model.respon.ConfigResponse;
import com.example.admin.ebuy.model.respon.RegisterResponse;
import com.example.admin.ebuy.model.respon.UserResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    public final static String TAG="RegisterFragment";
    EditText txtPassword, txtConfirmPassword, txtUserName, txtPhonenumber;
    private Button btnLogin, btnRegister;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.register_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity)getActivity()).setTitle(true, getResources().getString(R.string.register));
        ((BaseActivity)getActivity()).setVisibleBack(true);
        ((BaseActivity)getActivity()).setVisibleFinish(false);

        txtPhonenumber = (EditText)view.findViewById(R.id.txtPhonenumber);
        txtPassword = (EditText)view.findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText)view.findViewById(R.id.txtConfirmPassword);
        txtUserName = (EditText)view.findViewById(R.id.txtUserName);
//        btnLogin = (Button)view.findViewById(R.id.btnLogin);
        btnRegister = (Button)view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

    }
    private boolean checkValidate() {
        if (txtPhonenumber.getText().toString().isEmpty()) {
            alertError(getResources().getString(R.string.empty_phonenumber), SweetAlertDialog.WARNING_TYPE,getResources().getString(R.string.warning));
            return false;
        }
        if (txtPassword.getText().toString().isEmpty() || txtConfirmPassword.getText().toString().isEmpty()) {
            alertError(getResources().getString(R.string.empty_pin), SweetAlertDialog.WARNING_TYPE,getResources().getString(R.string.warning));
            return false;
        }
        if (txtConfirmPassword.getText().toString().equals(txtPassword.getText().toString())==false){
            alertError(getResources().getString(R.string.diffirent_pass), SweetAlertDialog.WARNING_TYPE,getResources().getString(R.string.warning));
            return false;
        }
        if (txtUserName.getText().toString().isEmpty()) {
            alertError(getResources().getString(R.string.empty_user_name), SweetAlertDialog.WARNING_TYPE,getResources().getString(R.string.warning));
            return false;
        }
        return true;
    }


    public void login(String username, String password){
        byte[] data = new byte[0];
        String base64="";
        try {
            data = password.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.NO_WRAP | Base64.URL_SAFE);
            WriteLog.e("Triệu", base64);
            Toast.makeText(getContext(), base64,Toast.LENGTH_SHORT).show();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .customerLogin(new LoginRequest(username, base64))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UserResponse>() {
            @Override
            public void onCompleted() {
//                            if (LoginFragment.this != null && getActivity() != null && isAdded())
//                                ((BaseActivity) getActivity()).setLoading(false);
            }

            @Override
            public void onError(Throwable e) {
//                            if (RegisterFragment.this != null && getActivity() != null && isAdded())
//                                ((BaseActivity) getActivity()).setLoading(false);
//                alertError(e.getMessage(), SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.error));
            }

            @Override
            public void onNext(UserResponse userResponse) {
                WriteLog.e("Triệu", userResponse.toString());

                if (userResponse.getReplyCode() != AppConfig.SUCCESS_CODE) {
                    alertError(userResponse.getReplyText(), SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.error));
                } else {
                    CurrentUser.setUserInfo(userResponse.getUser());
                    CurrentUser.saveUserInfo(userResponse.getUser());
                    getActivity().onBackPressed();
//                                    Navigator.getInstance().startFragment();
//                                    getActivity().finish();
                }
            }
        });
    }
    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:

                if (checkValidate()){
                    ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                        .customerRegister(new RegisterRequest(txtUserName.getText().toString(), txtPassword.getText().toString(), txtPhonenumber.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<RegisterResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(RegisterResponse registerResponse) {

                                if (registerResponse.getReplyCode() != AppConfig.SUCCESS_CODE) {
                                    alertError(registerResponse.getReplyText(), SweetAlertDialog.ERROR_TYPE, getResources().getString(R.string.error));
                                } else{
                                    login(txtUserName.getText().toString(), txtPassword.getText().toString());
                                }
                            }
                    }   );
                }
                else {

                }
                break;
        }
    }
}
