package com.example.xh.rxjava;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.ui.BaseActivity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by xiehui on 2016/11/1.
 */
public class NormalRxActivity extends BaseActivity {
    private TextView tv1;
    private TextView tv2;
    private Button btn;

    @Override
    public int getContentViewId() {
        return R.layout.rxjava_layout1;
    }

    @Override
    public void initViews() {

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                execute();
                break;
        }
    }

    private void execute() {
        Observable observable=Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("也许当初忙着微笑和哭泣");
                subscriber.onNext("忙着追逐天空中的流星");
                subscriber.onNext("人理所当然的忘记");
                subscriber.onNext("是谁风里雨里一直默默守护在原地");
                subscriber.onNext("未完待续");
                subscriber.onCompleted();
            }
        });
        //或者
        String[] strs={"也许当初忙着微笑和哭泣","忙着追逐天空中的流星","人理所当然的忘记","是谁风里雨里一直默默守护在原地"};
        Observable observable1=Observable.from(strs);
        //或者
        Observable observable2=Observable.just("也许当初忙着微笑和哭泣","忙着追逐天空中的流星","人理所当然的忘记","是谁风里雨里一直默默守护在原地");
        Subscriber subscriber=new Subscriber<String>(){

            @Override
            public void onCompleted() {

                tv1.append("执行onCompleted，观察结束...\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

                tv1.append("执行了onNext()\n"+s+"\n");

            }
        };
        tv1.append("开始观察.....、\n");
        observable.subscribe(subscriber);
    }

}
