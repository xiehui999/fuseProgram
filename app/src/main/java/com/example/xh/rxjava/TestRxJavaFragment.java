package com.example.xh.rxjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xh.R;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xiehui on 2016/10/31.
 */
public class TestRxJavaFragment extends Fragment implements View.OnClickListener {

    private String TAG = "RXJAVA";
    private Button btn1, btn2, btn3, btn4;

    //a library for composing asynchronous and event-based programs using observable sequences for the Java VM"
    // （一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库）
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rxjavafragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn1 = (Button) view.findViewById(R.id.btn1);
        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
        btn4 = (Button) view.findViewById(R.id.btn4);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {


                subscriber.onNext("hi RxJAVA");
                subscriber.onCompleted();

            }
        });

        //subscriber观察者,去处理observable发射过来的数据
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }
        };

        observable.subscribe(subscriber);
        Observable.just("hi RXJAVA2").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, s);
            }
        });
        String[] strs = {"1", "2", "3", "4"};
        Observable.from(Arrays.asList(strs))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, s);
                    }
                });

        Observable.just("hi Rxjava3")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer s) {
                Log.e(TAG, s + "");
            }
        });

        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                String[] strs = {"1", "2", "3", "4", "5", "6", "7"};
                subscriber.onNext(Arrays.asList(strs));
            }
        }).flatMap(new Func1<List<String>, Observable<?>>() {
            @Override
            public Observable<?> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).filter(new Func1<Object, Boolean>() {//filter 操作符，去掉“1”;
            @Override
            public Boolean call(Object o) {
                if (o.toString().equals("1")) return false;
                return true;
            }
        })
                .take(2)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.e(TAG, o.toString());
                    }
                });

        //灯和开关的例子，灯观察者，开关被观察者
        //创建被观察者
        Observable switcher = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("On");
                subscriber.onNext("Off");
                subscriber.onNext("On");
                subscriber.onNext("On");
                subscriber.onCompleted();
            }
        });
        //上面创建也可写为
        Observable switcher1 = Observable.just("On", "Off", "On", "On");
        //或者
        String[] strs1 = {"On", "Off", "On", "On"};
        Observable switcher2 = Observable.from(strs1);
        //创建观察者，灯
        Subscriber light = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "灯结束观察");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "出现错误");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "传递过来的信息" + s);
            }
        };

        switcher.subscribe(light);


        Observable.just("ON1", "OFF1", "ON1", null)
                //指定了被观察者执行的线程环境
                .subscribeOn(Schedulers.newThread())
                //过滤空值
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s != null;
                    }
                })
                //实现订阅
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, s + "");
                    }
                });
        //subscribeOn（）它指示Observable在一个指定的调度器上创建（只作用于被观察者创建阶段）。只能指定一次，如果指定多次则以第一次为准
        //observeOn（）指定在事件传递（加工变换）和最终被处理（观察者）的发生在哪一个调度器。可指定多次，每次指定完都在下一步生效。


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.btn1:
                intent.setClass(getContext(), NormalRxActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
        }
    }
}
