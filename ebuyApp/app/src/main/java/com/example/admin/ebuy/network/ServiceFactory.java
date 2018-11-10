package com.example.admin.ebuy.network;

import android.annotation.SuppressLint;

import com.example.admin.ebuy.model.CurrentUser;
import com.example.admin.ebuy.util.AppConfig;
import com.example.admin.ebuy.util.ManagementCacheObject;
import com.example.admin.ebuy.util.WriteLog;
import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;


/**
 * Created by tuan.nguyen on 23/06/18.
 */

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private static Retrofit retrofit;

    private ServiceFactory() {

    }

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) serviceFactory = new ServiceFactory();
        return serviceFactory;
    }

    private Interceptor getHttpInterceptor() {
        return new Interceptor() {

            @SuppressLint("DefaultLocale")
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder newRequestBuilder = request.newBuilder().header("Content-Type", "application/json");
                if(ManagementCacheObject.getConfig()!=null){

                        newRequestBuilder.header("Authorization", CurrentUser.getToken());


                }


                request = newRequestBuilder.build();
                Response response;

                AppConfig.getApiEndpoint();
                if (WriteLog.debug) {
                    long t1 = System.nanoTime();
                    WriteLog.i("Interceptor REQ", String.format("%s on %s%n%s",
                            request.url(), chain.connection(), request.headers()));

                    try {
                        RequestBody copy = request.body();
                        Buffer buffer = new Buffer();
                        if (copy != null)
                            copy.writeTo(buffer);

                        WriteLog.i("BODY", buffer.readUtf8());
                    } catch (IOException e) {
                        WriteLog.e("BODY", "null");
                    }

                    response = chain.proceed(request).newBuilder()
                            .build();

                    long t2 = System.nanoTime();
                    WriteLog.i("Interceptor RESP:", String.format("%s in %.1fms%n%s",
                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                    return response;
                }

                return chain.proceed(request).newBuilder()
                        .build();
            }
        };
    }

    public Retrofit getRetrofit(final String endPoint) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addNetworkInterceptor(getHttpInterceptor());
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        clientBuilder.readTimeout(60, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS);
        return new Retrofit.Builder()
                .baseUrl(endPoint)
                .client(clientBuilder.build())
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     *
     * @param clazz    Java interface of the retrofit service
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint) {
        if (retrofit == null)
            retrofit = getInstance().getRetrofit(endPoint);

        T service = retrofit.create(clazz);
        return service;
    }
}