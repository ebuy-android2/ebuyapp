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
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.User;
import com.example.admin.ebuy.model.request.UpdateProfileRequest;
import com.example.admin.ebuy.model.respon.UpdateProfileResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.util.WriteLog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {
    public final static String TAG="EditProfileFragment";

    private CircleImageView imageAvatar;

    private Uri filePath;

    private User user;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;


    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.edit_profile_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {


        ((BaseActivity)getActivity()).setTitle(true, getResources().getString(R.string.edit_profile));
        ((BaseActivity)getActivity()).setVisibleBack(true);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageAvatar = (CircleImageView) view.findViewById(R.id.imageAvatar);
        imageAvatar.setOnClickListener(this);

        (new Handler()).postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                if (CurrentUser.isLogin() || !CurrentUser.getUserInfo().getAccessToken().isEmpty()) {
                    if (!CurrentUser.getUserInfo().getAvatar().isEmpty()){
                        Picasso.with(getContext())
                                .load(CurrentUser.getUserInfo().getAvatar())
                                .placeholder(R.drawable.logo)
                                .error(R.drawable.logo)
                                .into(imageAvatar);
                    }else {
                        imageAvatar.setImageResource(R.drawable.logo);
                    }
                }
                else {
                    imageAvatar.setImageResource(R.drawable.logo);
                }
            }
        },2000);
    }

    private void updateProfile(String avatar){
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .updateProfile(new UpdateProfileRequest(avatar))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateProfileResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateProfileResponse updateProfileResponse) {

                        WriteLog.e("TAG", updateProfileResponse.toString());
                        if(updateProfileResponse.getReplyCode()!= AppConfig.SUCCESS_CODE){
                            Toast.makeText(getContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
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
            case R.id.imageAvatar:
                Toast.makeText(getContext(),"Avatar", Toast.LENGTH_SHORT).show();
                chooseImage();
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
                                    updateProfile(uri+"");

                                        CurrentUser.getUserInfo().setAvatar(uri+"");

                                    Toast.makeText(getContext(), CurrentUser.getUserInfo().getAvatar(),Toast.LENGTH_SHORT).show();

                                    WriteLog.e("Trieu", uri +"");
                                    WriteLog.e("AvataReplace", CurrentUser.getUserInfo().getAvatar());

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
