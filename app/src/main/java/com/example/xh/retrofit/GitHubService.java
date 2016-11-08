package com.example.xh.retrofit;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    //header不能被互相覆盖。所有具有相同名字的header将会被包含到请求中。
    //添加多个请求头

    @GET("/users/{user}/repos")
    Call<ResponseBody> listRepos1(@Path("user") String user);


    //@Header注解动态的更新一个请求的header。必须给@Header提供相应的参数，如果参数的值为空header将会被忽略，否则就调用参数值的toString()方法并使用返回结果
    @GET("user")
    Call<ResponseBody> getUser(@Header("Authorization") String authorization);

    //@FormUrlEncoded注解的时候，将会发送form-encoded数据，每个键-值对都要被含有名字的@Field注解和提供值的对象所标注
    @FormUrlEncoded
    @POST("user/edit")
    Call<ResponseBody> updateUser(@Field("first_name") String first, @Field("last_name") String last);
    //将会发送multipart数据，Parts都使用@Part注解进行声明
    @Multipart
    @PUT("user/photo")
    Call<ResponseBody> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);
}
