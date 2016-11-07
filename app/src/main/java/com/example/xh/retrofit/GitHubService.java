package com.example.xh.retrofit;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by xiehui on 2016/11/7.
 */
public interface GitHubService {
    @Headers({"Accept:application/vnd.github.v3.full+json",
            "User-Agent:Retrofit-Sample-App"})
    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
    //添加请求头
    // @Headers("Cache-control:max-age=6400000")
    //添加多个请求头

    @GET("/users/{user}/repos")
    Call<ResponseBody> listRepos1(@Path("user") String user);
    @GET("user")
    Call<ResponseBody> getUser(@Header("Authorization") String authorization);
}
