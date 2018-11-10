package com.example.admin.ebuy.network;

import android.graphics.Bitmap;

import com.example.admin.ebuy.model.respon.CustomerRespose;
import com.example.admin.ebuy.model.respon.FeedBackResponse;
import com.example.admin.ebuy.model.respon.LikeResponse;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.model.respon.StarResponse;
import com.example.admin.ebuy.model.respon.TypeProductResponse;
import com.example.admin.ebuy.model.respon.TypeResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by tuan.nguyen on 23/6/18.
 */

public interface EBServices {

    String PATH="/api";
//    @GET("/api/configs")
//    Observable<ConfigResponse> getConfig();
//
//    @POST("/api/customers/login")
//    @Headers("Content-Type: application/json")
//    Observable<UserResponse> customerLogin(@Body LoginRequest loginRequest, @Query("time") String time);
//
//    @Multipart
//    @Headers("Content-Type: application/json")
//    @POST("api/orders/{id}/type/upload-task")
//    Observable<ImageResponse> postImage(@Path("id") int teacher_id, @Path("type") int type, @Query("task") Bitmap image);
    @GET(PATH+"/listproduct/getall")
    Observable<ProductResponse> getProduct();
    @GET(PATH+"/productdetail/getall")
    Observable<ProductDetailResponse> getProductDetail();
    @GET(PATH+"/type/gettype")
    Observable<TypeResponse> getType(@Query("id_ListProduct") int id);

    @GET(PATH+"/listproduct/getProductDetailByIdListProduct")
    Observable<ProductDetailResponse> getProductDetailByType(@Query("id_ListProduct")int listproduct);

    @GET(PATH+"/typeProduct/gettypeProduct")
    Observable<TypeProductResponse> getTypeProduct(@Query("id_Type") int id);

    @GET(PATH+"/type/getProductDetailByIdType")
    Observable<ProductDetailResponse> getProductDetailByTypeProduct(@Query("id_Type")int type);

    @GET(PATH+"/product/getCustomerByIdProduct")
    Observable<CustomerRespose> getCustomerByIdProduct(@Query("id_Product")int id);

    @GET(PATH+"/feedbacks/getAllByIdProductDetail")
    Observable<FeedBackResponse> getFeedbackByIdProduct(@Query("id_product_detail")int id);

    @GET(PATH+"/feedbacks/getStar")
    Observable<StarResponse> getStar(@Query("id_product_detail")int id);

    @GET(PATH+"/feedbacks/getCountLike")
    Observable<LikeResponse> getLike(@Query("id_product_detail")int id);


}
