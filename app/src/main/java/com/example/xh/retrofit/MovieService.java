package com.example.xh.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xiehui on 2016/11/3.
 */
public interface MovieService {
    //@Path：所有在网址中的参数（URL的问号前面）
    // @GET("top250/{id}")  @Path("id")  top250/2
    //@Query注解  参数加在url问号之后 发送时url拼接 top250?start=1&count=100
    //@QueryMap  Map<String, String> map 就是多个@Query
    //@Field：用于POST请求，提交单个数据使用时@Field时记得添加@FormUrlEncoded
    //@Body ：相当于多个@Field，以对象的形式提交
    //@Url 重新定义接口地址
    @GET("top250")
    Call<Subject> getTopMovie(@Query("start") int start,@Query("count") int count);
}
