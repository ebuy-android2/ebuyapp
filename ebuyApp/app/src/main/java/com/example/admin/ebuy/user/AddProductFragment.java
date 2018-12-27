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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.activity.SupportActivity;
import com.example.admin.ebuy.adapter.ChooseListProductAdapter;
import com.example.admin.ebuy.adapter.ChooseListTypeAdapter;
import com.example.admin.ebuy.adapter.ChooseListTypeProductAdapter;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.model.request.ProductRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.model.respon.TypeProductResponse;
import com.example.admin.ebuy.model.respon.TypeResponse;
import com.example.admin.ebuy.network.EBServices;
import com.example.admin.ebuy.network.ServiceFactory;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.Navigator;
import com.example.admin.ebuy.util.PrefUtils;
import com.example.admin.ebuy.util.WriteLog;
import com.example.admin.ebuy.view.EBCustomFont;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends BaseFragment implements View.OnClickListener{
    public final static String TAG="AddProductFragment";
    private ImageView imageProduct;
    private EditText edtNameProdcut, edtDescribe, edtTrade, edtMaterial, edtPrice, edtCount, edtWeight, edtStatus;
    private RelativeLayout btnListProduct;
    private EBCustomFont txtTypeProduct, idList, btnFinish;
    private Uri filePath;
    private Spinner spinnerProduct, spinnerType, spinnerTypeProduct;
    private ChooseListProductAdapter chooseListProductAdapter;
    private ChooseListTypeAdapter chooseListTypeAdapter;
    private ChooseListTypeProductAdapter chooseListTypeProductAdapter;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    private ProductRequest productRequest;

    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected int getLayoutResourceId() {
        return R.layout.add_product_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {



        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        ((BaseActivity)getActivity()).setVisibleBack(true);
        ((BaseActivity)getActivity()).setTitle(true, getResources().getString(R.string.buy));
        spinnerProduct = view.findViewById(R.id.spinnerProduct);
        spinnerType = view.findViewById(R.id.spinnerType);
        spinnerTypeProduct = view.findViewById(R.id.spinnerTypeProduct);

        imageProduct = (ImageView)view.findViewById(R.id.imageProduct);
        edtNameProdcut = (EditText)view.findViewById(R.id.edtNameProdcut);
        edtDescribe = (EditText)view.findViewById(R.id.edtDescribe);
        edtTrade = (EditText)view.findViewById(R.id.edtTrade);
        edtMaterial = (EditText)view.findViewById(R.id.edtMaterial);
        edtPrice = (EditText)view.findViewById(R.id.edtPrice);
        edtCount = (EditText)view.findViewById(R.id.edtCount);
        edtWeight = (EditText)view.findViewById(R.id.edtWeight);
        edtStatus = (EditText)view.findViewById(R.id.edtStatus);
        btnFinish = (EBCustomFont)view.findViewById(R.id.btnFinish);
        productRequest = new ProductRequest();

        imageProduct.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        getListProduct();
        super.onResume();
    }

    @Override
    public String getTagName() {
        return TAG;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFinish:
                uploadImage();
                break;
            case R.id.imageProduct:
                chooseImage();
                break;
        }
    }

    public void postProduct(int id, String urlImage){

        productRequest.setId_list(Integer.parseInt(PrefUtils.getInstance().getString(PrefUtils.ID_LIST)));
        productRequest.setId_type(Integer.parseInt(PrefUtils.getInstance().getString(PrefUtils.ID_TYPE)));
        productRequest.setId_type_product(Integer.parseInt(PrefUtils.getInstance().getString(PrefUtils.ID_TYPE_PRODUCT)));

        productRequest.setImage(urlImage);
        productRequest.setDescribe(edtDescribe.getText().toString());
        productRequest.setMaterial(edtMaterial.getText().toString());
        productRequest.setName_product(edtNameProdcut.getText().toString());
        productRequest.setTrademark(edtTrade.getText().toString());
        productRequest.setPrice(Float.parseFloat(edtPrice.getText().toString()));
        productRequest.setQuantity(Integer.parseInt(edtCount.getText().toString()));
        productRequest.setWeight(edtWeight.getText().toString());
        productRequest.setProduct_status(Integer.parseInt(edtStatus.getText().toString()));
        ServiceFactory.createRetrofitService(EBServices.class, AppConfig.getApiEndpoint())
                .postProduct(id, productRequest)
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
                        getActivity().finish();
                    }
                });
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
                imageProduct.setImageBitmap(bitmap);

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
                                    postProduct(CurrentUser.getUserInfo().getId(), uri+"");
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

    private void getListProduct()
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread() )
                .subscribe(new Observer<ProductResponse>() {
                    @Override
                    public void onCompleted() {
                        WriteLog.e("TAG", "complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("TAG", "erro");
                    }

                    @Override
                    public void onNext(ProductResponse productResponse) {
                        WriteLog.e("product", productResponse.toString());
                        chooseListProductAdapter = new ChooseListProductAdapter(getContext(), R.layout.custom_spinner, productResponse.getData());
                        spinnerProduct.setAdapter(chooseListProductAdapter);
                        PrefUtils.getInstance().putString(PrefUtils.ID_LIST,
                                String.valueOf(chooseListProductAdapter.getItem(0).getId()));
                        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                PrefUtils.getInstance().putString(PrefUtils.ID_LIST,
                                       String.valueOf(chooseListProductAdapter.getItem(position).getId()));
                                spinnerType.setVisibility(View.VISIBLE);
                                getType(chooseListProductAdapter.getItem(position).getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });

    }
    private void getType(int type)
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
                        WriteLog.e("type", typeResponse.toString());
                        chooseListTypeAdapter = new ChooseListTypeAdapter(getContext(), R.layout.custom_spinner, typeResponse.getData());
                        spinnerType.setAdapter(chooseListTypeAdapter);
                        PrefUtils.getInstance().putString(PrefUtils.ID_TYPE,
                                String.valueOf(chooseListTypeAdapter.getItem(0).getId()));
                        spinnerTypeProduct.setVisibility(View.VISIBLE);
                        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                PrefUtils.getInstance().putString(PrefUtils.ID_TYPE,
                                        String.valueOf(chooseListTypeAdapter.getItem(position).getId()));
                                spinnerTypeProduct.setVisibility(View.VISIBLE);
                                getTypeProduct(chooseListTypeAdapter.getItem(position).getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                });
    }
    private void getTypeProduct(int type)
    {
        ServiceFactory.createRetrofitService(EBServices.class,AppConfig.getApiEndpoint())
                .getTypeProduct(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TypeProductResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        WriteLog.e("abc", "errro");
                    }

                    @Override
                    public void onNext(TypeProductResponse typeProductResponse) {
                        WriteLog.e("typeProduct", typeProductResponse.toString());
                        chooseListTypeProductAdapter = new ChooseListTypeProductAdapter(getContext(), R.layout.custom_spinner, typeProductResponse.getData());
                        spinnerTypeProduct.setAdapter(chooseListTypeProductAdapter);
                        PrefUtils.getInstance().putString(PrefUtils.ID_TYPE_PRODUCT,
                                String.valueOf(chooseListTypeProductAdapter.getItem(0).getId()));
                        spinnerTypeProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                PrefUtils.getInstance().putString(PrefUtils.ID_TYPE_PRODUCT,
                                        String.valueOf(chooseListTypeProductAdapter.getItem(position).getId()));

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                });
    }
}
