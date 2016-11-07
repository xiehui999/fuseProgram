package com.example.xh.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xiehui on 2016/11/7.
 */
public interface GitHubService {
    @GET("/users/{user}/repos")
    Call<List<Repo>>  listRepos(@Path("user") String user);
}
