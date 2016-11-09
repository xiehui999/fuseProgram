package com.example.xh.retrofit.potting;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xiehui on 2016/11/8.
 */
public interface ApiService {
    @GET("city")
    Observable<Response> getData(@Query("city") String city);
}
