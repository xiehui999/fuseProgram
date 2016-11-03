package com.example.xh.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xiehui on 2016/11/3.
 */
public interface MovieServiceObservable {
    @GET("top250")
    Observable<Subject> getTopMovie(@Query("start") int start, @Query("count") int count);
}
