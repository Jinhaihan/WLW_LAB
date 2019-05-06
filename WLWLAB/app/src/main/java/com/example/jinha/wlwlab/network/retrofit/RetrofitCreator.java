package com.example.jinha.wlwlab.network.retrofit;


import android.util.ArrayMap;

import com.example.jinha.wlwlab.app.AppConfigKeys;
import com.example.jinha.wlwlab.app.ApplicationDelegate;
import com.example.jinha.wlwlab.network.retrofit.cookie.SSLSocketClient;
import com.example.jinha.wlwlab.network.retrofit.cookie_interceptor.AddCookiesInterceptor;
import com.example.jinha.wlwlab.network.retrofit.cookie_interceptor.SaveCookiesInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: SportsHealth
 * @Package: cn.ac.ia.iot.healthlibrary.network
 * @ClassName: RetrofitCreator
 * @Author: mebee
 * @CreateDate: 2018/8/21 15:02
 * @Description: 根据需求创建 API Service 的具体实现类型
 */
public class RetrofitCreator {

    private static final OkHttpClient.Builder CLITENT_BUIDLER = new OkHttpClient.Builder();

    /**
     * API Service 集合，避免重复创建API Service 造成性能消耗
     */

    private static final ArrayMap<String, Object> SERVICEMAP = new ArrayMap<>();
    private static final long DEFAULT_TIME_OUT = 30;
    private static OkHttpClient httpClient;
    private static Retrofit retrofitRaw;
    private static Retrofit retrofitRxJava;
    private static Retrofit retrofitGson;

    public enum TYPE {
        CALL,
        GSON,
        RXJAVA
    }


    public static  <T> T create(Class<T> service) {
        return create(service, TYPE.GSON);
    }


    /**
     *
     * 根据业务需要返回不同类型的 Service 接口实现
     *
     * @param service
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> service, TYPE type) {

        if (SERVICEMAP.containsKey(service.getName())) {
            return (T) SERVICEMAP.get(service.getName());
        }

        T proxy;

        switch (type) {
            /*返回Call 对象*/
            case CALL:
                proxy = rawRetrofitBuilder().create(service);
                break;
            // 返回 数据经过Gson对象转换的 Observable对象
            case GSON:
                proxy = gsonRetrofitBuilder().create(service);
                break;
            // 返回 数据未经过对象转换的 Observable对象
            case RXJAVA:
                proxy = rxRetrofitBuilder().create(service);
                break;
            default:
                throw new RuntimeException(RetrofitCreator.class.getName() + ": the type is not available");
        }

        SERVICEMAP.put(service.getName(), proxy);

        return proxy;
    }


    /**
     *
     * 创建 全局的OkHttpClient
     *
     * @return
     */
    public static OkHttpClient getHttpClient() {

        if (httpClient != null) {
            return httpClient;
        }


        httpClient = CLITENT_BUIDLER
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();

        return httpClient;
    }

    private static Retrofit rawRetrofitBuilder() {

        if (retrofitRaw != null) {
            return retrofitRaw;
        }

        retrofitRaw = new Retrofit.Builder()
                .baseUrl((String) ApplicationDelegate.getConfiguration(AppConfigKeys.API_HOST))
                .client(getHttpClient())
                .build();

        return retrofitRaw;
    }


    private static Retrofit rxRetrofitBuilder() {
        if (retrofitRxJava != null) {
            return retrofitRxJava;
        }

        retrofitRxJava = new Retrofit.Builder()
                .baseUrl((String) ApplicationDelegate.getConfiguration(AppConfigKeys.API_HOST))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .build();

        return retrofitRxJava;
    }

    private static Retrofit gsonRetrofitBuilder() {

        if (retrofitGson != null) {
            return retrofitGson;
        }

        retrofitGson = new Retrofit.Builder()
                .baseUrl((String) ApplicationDelegate.getConfiguration(AppConfigKeys.API_HOST))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 非严格的Json->对象的转换
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(getHttpClient())
                .build();

        return retrofitGson;
    }
}
