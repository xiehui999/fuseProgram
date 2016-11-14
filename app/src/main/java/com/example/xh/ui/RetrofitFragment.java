package com.example.xh.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.retrofit.GitHubService;
import com.example.xh.retrofit.GitHubServiceObservable;
import com.example.xh.retrofit.HttpMethods;
import com.example.xh.retrofit.MovieService;
import com.example.xh.retrofit.MovieServiceObservable;
import com.example.xh.retrofit.Repo;
import com.example.xh.retrofit.Subject;
import com.example.xh.retrofit.potting.BookInfo;
import com.example.xh.retrofit.potting.BookList;
import com.example.xh.retrofit.potting.RetrofitClient;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiehui on 2016/11/3.
 * Retrifit a type-safe HTTP client for Android and Java;
 * Retrofit 1.x中没有直接取消正在进行中任务的方法
 */
public class RetrofitFragment extends Fragment implements View.OnClickListener {

    Button click, click1, click2, click3,click4, click5,click6;
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
        click3 = (Button) view.findViewById(R.id.click3);
        click4 = (Button) view.findViewById(R.id.click4);
        click5 = (Button) view.findViewById(R.id.click5);
        click6 = (Button) view.findViewById(R.id.click6);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        click.setOnClickListener(this);
        click1.setOnClickListener(this);
        click2.setOnClickListener(this);
        click3.setOnClickListener(this);
        click4.setOnClickListener(this);
        click5.setOnClickListener(this);
        click6.setOnClickListener(this);
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
            case R.id.click3:
                getGitHubRepo();
                break;
            case R.id.click4:
                getGitHubRepo();
                break;
            case R.id.click5:
                getBookList();
                break;
            case R.id.click6:
                getBookInfo();
                break;
        }
    }

    private void getBookInfo() {
        result.setText("");
        RetrofitClient.getInstance().createApi().getBookInfo(new Subscriber<BookInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BookInfo bookInfo) {
                result.setText(bookInfo.toString());
            }
        },"1049180");
    }

    private void getBookList() {
        result.setText("");
       Subscription subscription= RetrofitClient.getInstance().createApi().getBookList(new Subscriber<BookList>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BookList bookList) {
                result.setText(bookList.toString());
            }
        },"6");
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    private void getGitHubRepoObservable() {
        //个别博客建议 Base URL: 总是以/结尾；- @Url: 不要以/开头
        result.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        GitHubServiceObservable gitHubServiceObservable = retrofit.create(GitHubServiceObservable.class);
        gitHubServiceObservable.listRepos("xiehui")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<List<Repo>, Observable<Repo>>() {
                    @Override
                    public Observable<Repo> call(List<Repo> repos) {
                        return Observable.from(repos);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Repo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Repo repos) {
                        result.append(repos.toString());
                    }
                });

    }

    private void getGitHubRepo() {
        result.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<List<Repo>> repoCall = gitHubService.listRepos("xiehui999");
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();
                for (Repo repo : repos) {
                    result.append(repo.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
        Call<ResponseBody> repoCall1 = gitHubService.listRepos1("xiehui999");
        repoCall1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("Response",response.message().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

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
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
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
    public void testRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient();
        //okHttpClient.interceptors().add()
        Retrofit retrofit = new Retrofit.Builder()
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
