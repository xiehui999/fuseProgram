package com.example.xh.rxjava;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiehui on 2016/10/31.
 */
public class RxJavaTest {
    //被观察者
    Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>(){
        @Override
        public void call(Subscriber<? super String> subscriber) {


            subscriber.onNext("hi RxJAVA");
            subscriber.onCompleted();

        }
    });

    private String TAG="RXJAVA";
    //subscriber观察者,去处理observable发射过来的数据
    Subscriber<String> subscriber=new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e(TAG,"onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG,e.getMessage());
        }

        @Override
        public void onNext(String s) {
            Log.e(TAG,s);
        }
    };
}
