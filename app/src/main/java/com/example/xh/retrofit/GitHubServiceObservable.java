package com.example.xh.retrofit;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by xiehui on 2016/11/7.
 */
public interface GitHubServiceObservable {
    @GET("/users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}
