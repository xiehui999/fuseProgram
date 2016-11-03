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

import com.example.xh.R;
import com.example.xh.retrofit.MovieService;
import com.example.xh.retrofit.Subject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiehui on 2016/11/3.
 */
public class RetrofitFragment extends Fragment implements View.OnClickListener{

    Button click;
    TextView result;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.retrofitfragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result=(TextView)view.findViewById(R.id.result);
        click=(Button)view.findViewById(R.id.click);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        click.setOnClickListener(this);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.click:
                    getMovie();
                    break;
            }
    }

    public void getMovie() {

        String baseUrl="https://api.douban.com/v2/movie/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService=retrofit.create(MovieService.class);
        Call<Subject> call=movieService.getTopMovie(0,10);
        call.enqueue(new Callback<Subject>() {
            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                result.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                result.setText("加载错误："+t.getMessage());
            }
        });
    }
}
