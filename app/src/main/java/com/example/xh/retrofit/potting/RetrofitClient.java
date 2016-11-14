package com.example.xh.retrofit.potting;

import android.content.Context;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiehui on 2016/11/8.
 */
public class RetrofitClient {
    private static final int DEFAULT_TIMEOUT = 10;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = "https://api.douban.com/";
    private Context mContext;
    private static Retrofit retrofit;
    private ApiService apiService;
    private static RetrofitClient retrofitClient;

    private RetrofitClient(Context mContext, String url) {
        if (TextUtils.isEmpty(url)) {
            //url为空，表示没有传url，则使用默认的url
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
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }
    /**
     * 初始化使用默认url
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        return getInstance(null, baseUrl);
    }
    /**
     * 初始化使用默认url
     *
     * @param context
     * @return
     */
    public static RetrofitClient getInstance(Context context) {
        return getInstance(context, baseUrl);
    }

    /**
     * 使用新的url
     *
     * @param context
     * @param url
     * @return
     */
    public static RetrofitClient getInstance(Context context, String url) {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (retrofitClient == null) {
                    retrofitClient = new RetrofitClient(context, url);
                }
            }
        }
        return retrofitClient;
    }

    public RetrofitClient connectioToService() {

        return this;
    }

    public  RetrofitClient createApi() {
        apiService=create(ApiService.class);
        return this;
    }

    public Subscription getBookList(Subscriber<BookList> subscriber ,String id){
        if(apiService==null)
            throw new RuntimeException("apiService don't initialization");
        return apiService.getBookList(id).compose(this.<BookList>applySchedulers()).subscribe(subscriber);
    }
    public Subscription getBookInfo(Subscriber<BookInfo> subscriber ,String id){
        if(apiService==null)
            throw new RuntimeException("apiService don't initialization");
        return apiService.getBookInfo(id).compose(this.<BookInfo>applySchedulers()).subscribe(subscriber);
    }
    private <T> T create(final Class<T> apiClass) {
        if (apiClass == null) {
            throw new RuntimeException("apiClass interface can not be null");
        }
        return retrofit.create(apiClass);
    }

    <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
