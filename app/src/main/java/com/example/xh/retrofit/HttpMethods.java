package com.example.xh.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiehui on 2016/11/4.
 */
public class HttpMethods {

    private static final String URL ="https://api.douban.com/v2/movie/";
    private Retrofit retrofit;
    private MovieServiceObservable movieServiceObservable;
    private static HttpMethods httpMethods=null;

    public HttpMethods() {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build();
        movieServiceObservable=retrofit.create(MovieServiceObservable.class);
    }

    public static HttpMethods getInstance(){
        if (httpMethods==null){
            synchronized (HttpMethods.class){
                if (httpMethods==null){
                    httpMethods=new HttpMethods();
                }
            }
        }
        return httpMethods;
    }

    public void getTopMovie(Subscriber<Subject> subscriber,int start,int count){
        movieServiceObservable.getTopMovie(start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
