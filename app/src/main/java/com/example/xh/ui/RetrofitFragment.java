package com.example.xh.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.retrofit.HttpMethods;
import com.example.xh.retrofit.MovieService;
import com.example.xh.retrofit.MovieServiceObservable;
import com.example.xh.retrofit.Subject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiehui on 2016/11/3.
 */
public class RetrofitFragment extends Fragment implements View.OnClickListener {

    Button click, click1, click2;
    TextView result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.retrofitfragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = (TextView) view.findViewById(R.id.result);
        click = (Button) view.findViewById(R.id.click);
        click1 = (Button) view.findViewById(R.id.click1);
        click2 = (Button) view.findViewById(R.id.click2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        click.setOnClickListener(this);
        click1.setOnClickListener(this);
        click2.setOnClickListener(this);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                getMovie();
                break;
            case R.id.click1:
                getMovieRxJava();
                break;
            case R.id.click2:
                getMovieHttp();
                break;
        }
    }

    private void getMovieHttp() {
        Subscriber subscriber = new Subscriber<Subject>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                result.setText(e.getMessage());
            }

            @Override
            public void onNext(Subject o) {
                result.setText(o.toString());
            }
        };
        HttpMethods.getInstance().getTopMovie(subscriber,0,10);
    }

    private void getMovieRxJava() {
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        MovieServiceObservable movieServiceObservable = retrofit.create(MovieServiceObservable.class);
        movieServiceObservable.getTopMovie(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Subject>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getContext(), "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                        result.setText(e.getMessage());
                    }

                    @Override
                    public void onNext(Subject subject) {

                        result.setText(subject.toString());
                    }
                });

    }

    /**
     * 测试retrofit
     */
    public void testRetrofit(){
        OkHttpClient okHttpClient=new OkHttpClient();
        //okHttpClient.interceptors().add()
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }
    /**
     * 原生使用
     */
    public void getMovie() {

        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<Subject> call = movieService.getTopMovie(0, 10);
        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                result.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                result.setText("加载错误：" + t.getMessage());
            }
        });
    }
}
