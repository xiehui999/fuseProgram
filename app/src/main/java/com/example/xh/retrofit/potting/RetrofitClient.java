package com.example.xh.retrofit.potting;

import android.content.Context;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiehui on 2016/11/8.
 */
public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 10;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = "";
    private Context mContext;
    private static Retrofit retrofit;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(Context mContext, String url, Map<String, String> headers) {
        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        /**
         * headers:暂时不考虑
         * setLevel方法设置日志级别
         * NONE没有日志、BASIC基础日志（request 和 response 的信息）、HEADERS请求头信息（除了 request 和 response 的信息外还有各自的headers）、BODY请求体信息
         * （除了 request 和 response 的信息外还有各自的headers以及各自的body）
         */
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .build();
        retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    public static RetrofitClient getInstance(Context context) {
        return getInstance(context, baseUrl);
    }

    public static RetrofitClient getInstance(Context context, String url) {
        return getInstance(context, baseUrl, null);
    }

    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (retrofitClient == null) {
                    retrofitClient = new RetrofitClient(context, url, headers);
                }
            }
        }
        return retrofitClient;
    }

    public RetrofitClient connectioToService(){

        return this;
    }
}
