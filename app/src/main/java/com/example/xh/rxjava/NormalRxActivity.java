package com.example.xh.rxjava;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.ui.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by xiehui on 2016/11/1.
 */
public class NormalRxActivity extends BaseActivity {
    private TextView tv1;
    private TextView tv2;
    private Button btn,btn1,btn2,btn3,btn4;
    String[] strs={"也许当初忙着微笑和哭泣","忙着追逐天空中的流星","人理所当然的忘记","是谁风里雨里一直默默守护在原地"};

    @Override
    public int getContentViewId() {
        return R.layout.rxjava_layout1;
    }

    @Override
    public void initViews() {

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn = (Button) findViewById(R.id.button);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                execute();
                break;
            case R.id.button1:
                executeRange();
                break;
            case R.id.button2:
                executeRepeat();
                break;
            case R.id.button3:
                executeTimer();
                break;
            case R.id.button4:
                execute();
                break;

        }
    }

    private void execute() {
        tv1.setText("");
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
    private void executeRange() {
        //Range(n,m)操作符根据初始值n和数目m发射一系列大于等于n的m个值
        tv1.setText("Range(10,4)");
        Observable.range(10,4).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv1.append("  "+integer);//输出从10开始的4个数10,11,12,14
            }
        });
    }
    private void executeRepeat() {
        //将一个Observable对象重复发射，我们可以指定其发射的次数
        tv1.setText("repeat(2)");
        Observable.from(strs).repeat(2).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
               tv1.append("\n"+s);
            }
        });
    }
    private void executeTimer() {
        //在指定时间后发射一个数字0，运行在Computation Scheduler
        tv1.setText("Timer");
        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                tv1.append("\n"+aLong);
            }
        });
    }
}
