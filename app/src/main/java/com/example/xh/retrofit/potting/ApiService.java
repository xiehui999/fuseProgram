package com.example.xh.retrofit.potting;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xiehui on 2016/11/8.
 */
public interface ApiService {
    //https://api.douban.com/
    @GET("v2/book/series/{id}/books")//查询id丛书书目信息
    Observable<BookList> getBookList(@Path("id") String id);
    @GET("/v2/book/{id}")//查询id图书信息详情
    Observable<BookInfo> getBookInfo(@Path("id") String id);
    @GET("/v2/book/search")//根据关键字查询图书信息，还可以增加start，count指定查询其实条数和总条数
    Observable<BookList> searchBooks(@Query("q") String id);

}
