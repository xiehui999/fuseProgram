package com.example.xh.rxjava;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.ui.BaseActivity;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

/**
 * Created by xiehui on 2016/11/1.
 */
public class RxConnetActivity extends BaseActivity {
    private TextView mText, mEdit;
    private Button mBtn;
    private Button btnNormal;
    private Subscription mSubscription;
    private String [] strs={"也许当初忙着微笑和哭泣","忙着追逐天空中的流星","人理所当然的忘记","是谁风里雨里一直默默守护在原地"};

    @Override
    public int getContentViewId() {
        return R.layout.rxjava_layout2;
    }

    @Override
    public void initViews() {
        mText = (TextView) findViewById(R.id.text1);
        mEdit = (TextView) findViewById(R.id.edit1);
        mBtn = (Button) findViewById(R.id.button);
        btnNormal = (Button) findViewById(R.id.button_cancal);
        mBtn.setText("正常情况下");
        btnNormal.setText("connect情况下");
        mEdit.setText("Observable发送事件1-6，两个观察者同时观察这个Observable \n要求：每发出一个事件，观察者A和观察者都会收到，而不是先把所有的时间发送A,然后再发送给B  \n");
        mBtn.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                mText.setText("");
                normal();
                break;
            case R.id.button_cancal:
                connect();
                break;
        }
    }

    private void connect() {
        mText.append("\n");
        //获得一个可连接的Observable对象，需调用publish()方法
        ConnectableObservable connectableObservable=Observable.from(strs).publish();
        Action1 action1=new Action1<String>(){
            @Override
            public void call(String s) {
                mText.append("观察者A  收到:  "+s+"\n");
            }
        };
        Action1 action11=new Action1<String>(){
            @Override
            public void call(String s) {
                mText.append("观察者B  收到:  "+s+"\n");
            }
        };
        connectableObservable.subscribe(action1);
        connectableObservable.subscribe(action11);
        connectableObservable.connect();
    }

    private void normal() {
        mText.append("\n");
        Observable observable=Observable.from(strs);
        Action1 action1=new Action1<String>() {
            @Override
            public void call(String s) {
                mText.append("观察者A 收到："+s+"\n");
            }
        };
        Action1 action11=new Action1<String>() {
            @Override
            public void call(String s) {
                mText.append("观察者B 收到："+s+"\n");
            }
        };
        observable.subscribe(action1);
        observable.subscribe(action11);
    }
}
