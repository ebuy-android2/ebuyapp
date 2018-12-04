package com.example.admin.ebuy.network;

import com.example.admin.ebuy.model.request.AddOrderDetailRequest;
import com.example.admin.ebuy.model.request.CreateOrderRequest;
import com.example.admin.ebuy.model.request.LoginRequest;
import com.example.admin.ebuy.model.request.RegisterRequest;
import com.example.admin.ebuy.model.request.UpdateProfileRequest;
import com.example.admin.ebuy.model.respon.BaseResponse;
import com.example.admin.ebuy.model.respon.ConfigResponse;
import com.example.admin.ebuy.model.respon.CustomerRespose;
import com.example.admin.ebuy.model.respon.FeedBackResponse;
import com.example.admin.ebuy.model.respon.LikeResponse;
import com.example.admin.ebuy.model.respon.OrderDetailResponse;
import com.example.admin.ebuy.model.respon.ProductDetailResponse;
import com.example.admin.ebuy.model.respon.ProductResponse;
import com.example.admin.ebuy.model.respon.RegisterResponse;
import com.example.admin.ebuy.model.respon.StarResponse;
import com.example.admin.ebuy.model.respon.StoreResponse;
import com.example.admin.ebuy.model.respon.TypeLikeResponse;
import com.example.admin.ebuy.model.respon.TypeProductResponse;
import com.example.admin.ebuy.model.respon.TypeResponse;
import com.example.admin.ebuy.model.respon.UpdateProfileResponse;
import com.example.admin.ebuy.model.respon.UserResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by tuan.nguyen on 23/6/18.
 */

public interface EBServices {

    String PATH="/api";
    @GET(PATH+"/configs")
    Observable<ConfigResponse> getConfig();

    @POST(PATH+"/customers/login")
    @Headers("Content-Type: application/json")
    Observable<UserResponse> customerLogin(@Body LoginRequest loginRequest);

    @POST(PATH+"/customers/register")
    @Headers("Content-Type: application/json")
    Observable<RegisterResponse> customerRegister(@Body RegisterRequest registerRequest);



    @Headers("Content-Type: application/json")
    @POST(PATH+"/customers/{id}/update_profile")
    Observable<UpdateProfileResponse> updateProfile(@Body UpdateProfileRequest updateProfileRequest);

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

    @GET(PATH+"/productdetail/getProductDetailOfCustomer")
    Observable<ProductDetailResponse> getProductDetailOfCustomer(@Query("id_customer")int id);

    @GET(PATH+"/customers/{id}/getAllOrderDetail")
    Observable<OrderDetailResponse> getOrderDetail(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST(PATH+"/customers/{id}/add_orderDetail")
    Observable<BaseResponse> addOrderDetail(@Path("id")int id, @Body AddOrderDetailRequest addOrderDetailRequest);

    @Headers("Content-Type: application/json")
    @POST(PATH+"/customers/{id}/create_order")
    Observable<BaseResponse> createOrder(@Path("id")int id, @Body CreateOrderRequest createOrderRequest);

    @Headers("Content-Type: application/json")
    @POST(PATH+"/customers/{id}/addFeedBackExpress")
    Observable<BaseResponse> postLike(@Path("id") int id,@Query("id_product_detail") int id_pro_detail,@Query("express") int stt);


    @GET(PATH+"/customers/{id}/getLike")
    Observable<TypeLikeResponse> getTypeLike(@Path("id") int id, @Query("id_product_detail") int id_pro_detail);

    @GET(PATH+"/customers/getall")
    Observable<StoreResponse> getAllCustomer();

}
