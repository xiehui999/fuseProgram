package com.example.xh.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiehui on 2016/11/3.
 */
public interface MovieService {
    @GET("top250")
    Call<Subject> getTopMovie(@Query("start") int start,@Query("count") int count);
}
