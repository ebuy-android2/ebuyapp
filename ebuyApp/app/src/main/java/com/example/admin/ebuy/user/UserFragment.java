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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.user.activity.UserActivity;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends BaseFragment implements View.OnClickListener {
    public final static String TAG="UserFragment";
    private ImageView btnSetting;
    private CircleImageView imageAvatar;
    private EBCustomFont btnLogin, btnRegister, txtUsername, txtBuy, txtSale, line1, line2;
    private LinearLayout linearLayoutBuy, linearLayoutSale;
    private Uri filePath;


    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {


        ((BaseActivity)getActivity()).setVisibleToolbar(false);

        btnSetting = (ImageView)view.findViewById(R.id.btnSetting);
        imageAvatar = (CircleImageView) view.findViewById(R.id.imageAvatar);
        btnLogin = (EBCustomFont)view.findViewById(R.id.btnLogin);
        btnRegister = (EBCustomFont)view.findViewById(R.id.btnRegister);
        txtBuy = (EBCustomFont)view.findViewById(R.id.txtBuy);
        txtSale = (EBCustomFont)view.findViewById(R.id.txtSale);
        line1 = (EBCustomFont)view.findViewById(R.id.line1);
        line2 = (EBCustomFont)view.findViewById(R.id.line2);
        linearLayoutBuy = (LinearLayout)view.findViewById(R.id.linearLayoutBuy);
        linearLayoutSale = (LinearLayout)view.findViewById(R.id.linearLayoutSale);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        imageAvatar.setOnClickListener(this);
        txtBuy.setOnClickListener(this);
        txtSale.setOnClickListener(this);

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
                }
                else {
                    btnLogin.setVisibility(View.VISIBLE);
                    btnRegister.setVisibility(View.VISIBLE);
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
                Toast.makeText(getContext(),"Avatar", Toast.LENGTH_SHORT).show();
                chooseImage();
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
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        Toast.makeText(getContext(),"Chọn ảnh", Toast.LENGTH_SHORT).show();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageAvatar.setImageBitmap(bitmap);
                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        Toast.makeText(getContext(),"Uploading ảnh", Toast.LENGTH_SHORT).show();
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String url = UUID.randomUUID().toString();
            final StorageReference ref = storageReference.child(url);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getBaseContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getActivity().getBaseContext(), uri +"", Toast.LENGTH_SHORT).show();
                                    WriteLog.e("Trieu", uri +"");
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity().getBaseContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}
