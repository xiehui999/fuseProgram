package com.example.xh.rxjava;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.ui.BaseActivity;
import com.example.xh.uploadfile.FileInfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;

/**
 * Created by xiehui on 2016/11/1.
 */
public class NormalRxActivity extends BaseActivity {
    private TextView tv1;
    private TextView tv2;
    private Button btn, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
    private Button btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29;
    private Button btn30,btn31,btn32;
    String[] strs = {"也许当初忙着微笑和哭泣", "忙着追逐天空中的流星", "人理所当然的忘记", "是谁风里雨里一直默默守护在原地"};
    private String text;
    private String TAG = "RxJava";
    private Serializable serializable;

    @Override
    public int getContentViewId() {
        return R.layout.rxjava_layout1;
    }

    @Override
    public void initViews() {
        serializable = this.getIntent().getSerializableExtra("fileInfo");
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        btn = (Button) findViewById(R.id.button);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btn10 = (Button) findViewById(R.id.button10);
        btn11 = (Button) findViewById(R.id.button11);
        btn12 = (Button) findViewById(R.id.button12);
        btn13 = (Button) findViewById(R.id.button13);
        btn14 = (Button) findViewById(R.id.button14);
        btn15 = (Button) findViewById(R.id.button15);
        btn16 = (Button) findViewById(R.id.button16);
        btn17 = (Button) findViewById(R.id.button17);
        btn18 = (Button) findViewById(R.id.button18);
        btn19 = (Button) findViewById(R.id.button19);
        btn20 = (Button) findViewById(R.id.button20);
        btn21 = (Button) findViewById(R.id.button21);
        btn22 = (Button) findViewById(R.id.button22);
        btn23 = (Button) findViewById(R.id.button23);
        btn24 = (Button) findViewById(R.id.button24);
        btn25 = (Button) findViewById(R.id.button25);
        btn26 = (Button) findViewById(R.id.button26);
        btn27 = (Button) findViewById(R.id.button27);
        btn28 = (Button) findViewById(R.id.button28);
        btn29 = (Button) findViewById(R.id.button29);
        btn30 = (Button) findViewById(R.id.button30);
        btn31 = (Button) findViewById(R.id.button31);
        btn32 = (Button) findViewById(R.id.button32);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn24.setOnClickListener(this);
        btn25.setOnClickListener(this);
        btn26.setOnClickListener(this);
        btn27.setOnClickListener(this);
        btn28.setOnClickListener(this);
        btn29.setOnClickListener(this);
        btn30.setOnClickListener(this);
        btn31.setOnClickListener(this);
        btn32.setOnClickListener(this);
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
                executeRepeatWhen();
                break;
            case R.id.button4:
                executeTimer();
                break;
            case R.id.button5:
                executeDefer();
                break;
            case R.id.button6:
                executeBuffer();
                break;
            case R.id.button7:
                executeWindow();
                break;
            case R.id.button8:
                executeGroupBy();
                break;
            case R.id.button9:
                executeConcat();
                break;
            case R.id.button10:
                executeCombineLastest();
                break;
            case R.id.button11:
                executeJoin();
                break;
            case R.id.button12:
                executeScan();
                break;
            case R.id.button13:
                executeStartWith();
                break;
            case R.id.button14:
                executeSwitchOnNext();
                break;
            case R.id.button15:
                executeCast();
                break;
            case R.id.button16:
                executeDelay();
                break;
            case R.id.button17:
                executeDelaySubscription();
                break;
            case R.id.button18:
                executeDo();
                break;
            case R.id.button19:
                executeTimeInterval();
                break;
            case R.id.button20:
                executeTimeOut();
                break;
            case R.id.button21:
                executetoList();
                break;
            case R.id.button22:
                executeToMap();
                break;
            case R.id.button23:
                executeToMultiMap();
                break;
            case R.id.button24:
                executeAll();
                break;
            case R.id.button25:
                executeAmb();
                break;
            case R.id.button26:
                executeContains();
                break;
            case R.id.button27:
                executeDefaultIfEmpty();
                break;
            case R.id.button28:
                executeSequenceEqual();
                break;
            case R.id.button29:
                executeSkipUntil();
                break;
            case R.id.button30:
                executeSkipWhile();
                break;
            case R.id.button31:
                executeTakeUntil();
                break;
            case R.id.button32:
                executeTakeWhile();
                break;
        }
    }

    private void execute() {
        tv1.setText("");
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
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
        Observable observable1 = Observable.from(strs);
        //或者
        Observable observable2 = Observable.just("也许当初忙着微笑和哭泣", "忙着追逐天空中的流星", "人理所当然的忘记", "是谁风里雨里一直默默守护在原地");
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                tv1.append("执行onCompleted，观察结束...\n");
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                tv1.append("执行了onNext()\n" + s + "\n");
            }
        };
        tv1.append("开始观察.....、\n");
        observable.subscribe(subscriber);
    }

    private void executeRange() {
        //Range(n,m)操作符根据初始值n和数目m发射一系列大于等于n的m个值
        tv1.setText("Range(10,4)");
        Observable.range(10, 4).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv1.append("  " + integer);//输出从10开始的4个数10,11,12,14
            }
        });
    }

    private void executeRepeat() {
        //将一个Observable对象重复发射，我们可以指定其发射的次数，
        String[] strs = {"也许当初忙着微笑和哭泣", "忙着追逐天空中的流星"};
        tv1.setText("repeat(2)");
        Observable.from(strs).repeat(3).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: " + s);
                tv1.append("\n" + s);
            }
        });
    }

    private void executeRepeatWhen() {
        //repeatWhen可以让订阅者多次订阅,onCompleted只执行一次
        tv1.setText("\nrepeatWhen()用法");
        Observable.range(10, 3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return Observable.timer(3, TimeUnit.SECONDS);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer s) {
                tv1.append("\n" + s);
            }
        });


    }

    private void executeTimer() {
        //在指定时间后发射一个数字0，运行在Computation Scheduler
        //timer发生在新线程
        tv1.setText("Timer");
        Observable.timer(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                tv1.append("\n" + aLong);
            }
        });
    }

    private void executeDefer() {
        //与create、just、from等操作符一样，是创建类操作符，不过所有与该操作符相关的数据都是在订阅是才生效的，defer能保证Observable的状态是最新的:
        //defer()中的代码直到被订阅才会执行,对于延迟执行，用。create（）操作符也能达到此效果，手动调用onNext(),onCompleted()
        //如下代码,输出应该是更改后的值(RxComputationScheduler)
        tv1.setText("Defer");
        text = "初始值";
        Observable<String> observable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(text);
            }
        });
        text = "更改后的值";
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tv1.append("\n" + s);
            }
        });
    }

    private void executeBuffer() {
        //
        tv1.setText("Buffer");
        tv1.append("\n" + "输入从10开始的6个数，通过使用buffer(2)将数据两个两个发送（一次订阅两个）");
        //buffer(2)一次订阅2两个，假如参数超过数据长度，按最大长度一次订阅
        Observable.range(10, 6).buffer(2).subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(List<Integer> integers) {
                Log.e(TAG, "onNext: " + integers);
                tv1.append("\n" + integers);
            }
        });

        tv1.append("\n" + "使用buffer(6,1)结果");
        //buffer（n,m）第一次发射前N个数据，之后每次剔除m个数据发射,当m等于数据总长度时效果和buffer(n)一样
        //如buffer(6,2)//第一次接收10,11,12,13,14,15第二次12,13,14,15第三次14,15
        Observable.range(10, 6).buffer(6, 1).subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(List<Integer> integers) {
                Log.e(TAG, "onNext: " + integers);
                tv1.append("\n" + integers);
            }
        });
        tv1.append("\n" + "使用buffer(5,2)结果");
        Observable.range(10, 6).buffer(5, 2).subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(List<Integer> integers) {
                Log.e(TAG, "onNext: " + integers);
                tv1.append("\n" + integers);
            }
        });
    }

    private void executeWindow() {
        //window和函数buffer有点相似，区别是此操作符发射的是Observable而不是列表
        tv1.setText("Window\n输入数据位从10开始的6个数\nwindow(2)");
        Observable.range(10, 6).window(2).subscribe(new Subscriber<Observable<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted1: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError1: ");
            }

            @Override
            public void onNext(Observable<Integer> integerObservable) {
                tv1.append("\n");
                integerObservable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted2: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError2: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext2: " + integer);
                        tv1.append("     " + integer);
                    }
                });
            }
        });

        //理解可参考buffer(n,m),会发送重叠数据
        tv1.append("\nwindow(5,2)");
        // 10,11,   12,13,   14,15；
        //window理解不了了..............先放着改天再看，蒙了///
        // TODO: 2016/12/9
        Observable.range(10, 6).window(5, 2).subscribe(new Subscriber<Observable<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompletedwindow(5,2)1: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onErrorwindow(5,2)1: ");
            }

            @Override
            public void onNext(Observable<Integer> integerObservable) {
                Log.e(TAG, "onNextwindow(5,2)1: ");
                tv1.append("\n");
                integerObservable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompletedwindow(5,2)2: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onErrorwindow(5,2)2: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNextwindow(5,2)2:------------ " + integer);
                        tv1.append("      " + integer);
                    }
                });
            }
        });
    }

    private void executeGroupBy() {
        tv1.setText("GroupBy将1到10的数据按奇偶分组");
        Observable.range(1, 10).groupBy(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).subscribe(new Subscriber<GroupedObservable<Boolean, Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted:1 ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError:1 ");
            }

            @Override
            public void onNext(final GroupedObservable<Boolean, Integer> booleanIntegerGroupedObservable) {
                booleanIntegerGroupedObservable.toList().subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted:2 ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:2 ");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.e(TAG, "onNext:2 " + integers);
                        tv1.append("\n" + "所属分组" + booleanIntegerGroupedObservable.getKey() + "值：" + integers);
                    }
                });
            }
        });
    }

    private void executeConcat() {
        //是将多个Observable 按传入顺序进行输出observableA,observableB...先后输出
        tv1.setText("\nConcat数据源observableA：range(1,5),observableB:range(7,5)");
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                try {
                    subscriber.onNext(100);
                    Thread.sleep(500);
                    subscriber.onNext(200);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("error11"));
                }

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        Observable<Integer> observableA = Observable.range(1, 5);
        Observable<Integer> observableB = Observable.range(7, 5);
        //可以自己将observableA更换为observable，或者更改操作符为merge理解
        Observable.concat(observableA, observableB).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: concat");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: concat");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: concat" + integer);
                tv1.append("\nonNext：" + integer);
            }
        });
    }

    private void executeCombineLastest() {
        // 是将第一个Observable的最新(最后一条)数据与后面的Observable数据项按某种规则合并
        tv1.setText("combineLastest数据源observableA：range(1,4),observableB:range(10,5)");
        Observable<Integer> observableA = Observable.range(1, 4);
        Observable<Integer> observableB = Observable.range(10, 5);
        Observable.combineLatest(observableA, observableB, new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                Log.e(TAG, "call: combineLatest");
                return "observableA:" + integer + "  observableB:" + integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: combineLatest" + s);
                tv1.append("\n" + s);
            }
        });
    }

    private void executeJoin() {
        //在a的生命周期内：b输出的数据项与a输出的数据项每个合并,直到b输出下一项
        //需要使用subscribeOn(Schedulers.newThread())将两个Observable在两个不同的线程发射
        tv1.setText("join数据源observableA：range(1,5),observableB:range(7,5)");
        Observable<Integer> observableA = Observable.range(1, 2).subscribeOn(Schedulers.newThread());
        Observable<Integer> observableB = Observable.range(7, 3).subscribeOn(Schedulers.newThread());
        observableA.join(observableB, new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                Log.e(TAG, "call: A" + integer + "   " + Thread.currentThread().getName());
                return Observable.just(integer).delay(1, TimeUnit.SECONDS);
            }
        }, new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                Log.e(TAG, "call: B" + integer + "   " + Thread.currentThread().getName());
                return Observable.just(integer).delay(1, TimeUnit.SECONDS);
            }
        }, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.e(TAG, "call:AjoinB A: " + integer + " B:" + integer2 + Thread.currentThread().getName());
                return integer + integer2;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer);
                        tv1.append("\n" + integer);
                    }
                });
    }

    private void executeScan() {
        //scan对原始Observable发射的第一项数据应用一个函数，然后将函数计算结果作为第一项再与第二项数据应用函数，并且对后面的数据一直持续这个过程
        //例如计算1+2+3+4.先输出onNext（）输出1，接着scan回调1+2=3作为onNext（）  继续scan回调3+3=6作为onNext() 继续scan6+4=10作为onNext() 然后onCompleted()
        tv1.setText("Scan执行计算阶前n项和");
        //scan(10, new Func2)scan还有一个重载方法可以指定初始值,你可以设置查看执行log。
        Observable.range(1, 20).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.e(TAG, "call: integer:" + integer + "  integer2" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
                tv1.append("\n" + integer);
            }
        });
    }

    private void executeStartWith() {
        //结合操作
        tv1.setText("range(1,5).startWith(11,12)");
        Observable.range(1, 5).startWith(11, 12).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " + integer);
                tv1.append("\n" + integer);
            }
        });
    }

    private void executeSwitchOnNext() {
        tv1.setText("switchOnNext");
        //每隔500毫秒产生一个observable
        Observable<Observable<Long>> observable = Observable.interval(0, 500, TimeUnit.MILLISECONDS).map(new Func1<Long, Observable<Long>>() {
            @Override
            public Observable<Long> call(Long aLong) {
                //每隔200毫秒产生一组数据（0,10,20,30,40)
                Log.e(TAG, "call1: " + aLong);
                return Observable.interval(0, 200, TimeUnit.MILLISECONDS).map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        Log.e(TAG, "call2: " + aLong);
                        return aLong * 10;
                    }
                }).take(5);
            }
        }).take(2);
        Observable.switchOnNext(observable).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: SwitchOnNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: SwitchOnNext");
            }

            @Override
            public void onNext(Long aLong) {
                Log.e(TAG, "onNext: SwitchOnNext  " + aLong);
            }
        });
    }

    private void executeCast() {
        //该操作符比较少用，可以做类型检查之用
        tv1.setText("cast 数据源range(1,3)");
        Observable.just(serializable).cast(FileInfo.class).subscribe(new Subscriber<FileInfo>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(FileInfo fileInfo) {
                Log.e(TAG, "onNext: " + fileInfo.toString());
                tv1.append("\n" + fileInfo.toString());
            }
        });
    }

    private void executeDelay() {
        tv1.setText("delay");
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.e(TAG, "call: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        }).delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tv1.append("\n" + new SimpleDateFormat("yyyy/MM/ddHH:MM:ss").format(new Date()) + "  " + integer);
                        Log.e(TAG, "onNext: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + integer);
                    }
                });
    }

    private void executeDelaySubscription() {
        tv1.setText("delay");
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Log.e(TAG, "call: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        });
        Log.e(TAG, "call11: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
        observable.delaySubscription(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + e.toString());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tv1.append("\n" + new SimpleDateFormat("yyyy/MM/ddHH:MM:ss").format(new Date()) + "  " + integer);
                        Log.e(TAG, "onNext: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + integer);
                    }
                });
    }

    private void executeDo() {
        tv1.setText("Do");
        //doOnEach实现了doOnNext,doOnError,doOncompleted
        Observable.just(1, 2, 3)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "doOnNext: ");
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "doOnError: ");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnCompleted: ");
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnUnsubscribe: ");
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doOnTerminate: ");
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted1: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError1: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext1: " + integer);
                    }
                });
    }

    private void executeTimeInterval() {
        //和Timestamp（）TestRxJavaFragment 409行对比
        tv1.setText("TimeInterval");
        //下面代码并没有接触订阅,实际不可这样应用，需手动接触订阅
        Observable.interval(1, TimeUnit.SECONDS)
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 5;
                    }
                })
                .timeInterval()
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: doOnUnsubscribe");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TimeInterval<Long>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(TimeInterval<Long> longTimeInterval) {
                        Log.e(TAG, "onNext: value:" + longTimeInterval.getValue() + "getIntervalInMilliseconds" + longTimeInterval.getIntervalInMilliseconds());
                    }
                });
    }

    private void executeTimeOut() {
        tv1.setText("TimeOut");
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    subscriber.onNext(1);
                    Thread.sleep(100);
                    subscriber.onNext(2);
                    Thread.sleep(200);
                    subscriber.onNext(3);
                    Thread.sleep(300);
                    subscriber.onNext(4);
                    Thread.sleep(400);
                    subscriber.onNext(5);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    subscriber.onError(new Throwable("Error"));
                    e.printStackTrace();
                }
            }
        })
                //可更改方法为观察效果timeout(250,TimeUnit.MILLISECONDS,Observable.just(10,11))
                //在computation调度器执行
                .timeout(250, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext: " + integer);
                    }
                });
    }

    private void executetoList() {
        tv1.setText("toList");
        Observable.just(1, 2, 3, 4, 5).toList().subscribe(new Subscriber<List<Integer>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(List<Integer> integers) {
                Log.e(TAG, "onNext: " + integers);
            }
        });
    }

    private void executeToMap() {
        tv1.setText("ToMap");
        Observable.just(1, 2, 3, 4)
                .toMap(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "key" + integer;
                    }
                }, new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer + 10;
                    }
                })
                /*//指定map的key，vaule默认是发射数据原始值。
                .toMap(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "key"+integer;
                    }
                })*/
                .subscribe(new Subscriber<Map<String, Integer>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Map<String, Integer> integerIntegerMap) {
                        Log.e(TAG, "onNext: " + integerIntegerMap.toString());

                    }
                });
    }

    private void executeToMultiMap() {
        tv2.setText("toMultiMap");
        Observable.just(1, 2, 3, 4)
                .toMultimap(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "key" + integer;
                    }
                }).subscribe(new Subscriber<Map<String, Collection<Integer>>>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Map<String, Collection<Integer>> integerCollectionMap) {
                Log.e(TAG, "onNext: " + integerCollectionMap.toString());
            }
        });
    }

    private void executeAll() {
        tv2.setText("All数据源1,2,3,4，条件integer<2");
        Observable.just(1, 2, 3, 4).all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                Log.e(TAG, "call: " + integer);
                tv2.append("  call: " + integer + "\n");
                return integer < 2;
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Log.e(TAG, "onNext: " + aBoolean);
                tv2.append("   onNext: " + aBoolean);
            }
        });
    }

    private void executeAmb() {
        tv2.setText("amb");
        Observable observable = Observable.just(1, 2, 3).delay(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        Observable observable1 = Observable.just(4, 5, 6).delay(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        //observable.ambWith(observable1)效果与下相同
        Observable.amb(observable, observable1).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "  onNext: " + integer);
            }
        });
    }

    private void executeContains() {
        tv2.setText("Contains just(1,2,3,4).contains(2)");
        Observable.just(1, 2, 3, 4).contains(2)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e(TAG, "onNext: " + aBoolean);
                    }
                });
        Observable.just(1, 2, 3, 4).isEmpty().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.e(TAG, "call: " + aBoolean);
            }
        });
        Observable.just(1, 2, 3, 4)
                .exists(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 3;
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.e(TAG, "call: exists" + aBoolean);
            }
        });
    }

    private void executeDefaultIfEmpty() {
        tv2.setText("DefaultIfEmpty");
        Observable.empty().defaultIfEmpty(1).subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Object object) {
                Log.e(TAG, "onNext: " + object);
            }
        });
    }

    private void executeSequenceEqual() {
        tv2.setText("SequenceEqual");
        Observable observable = Observable.just(1, 2, 3);
        Observable observable1 = Observable.just(1, 2, 3);
        Observable.sequenceEqual(observable, observable1)
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e(TAG, "onNext: " + aBoolean);
                    }
                });
    }

    private void executeSkipUntil(){
        tv2.setText("SkipUntil");
        Observable observable = Observable.just(1, 2, 3,4,5,6).delay(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        Observable observable1 = Observable.just(20, 21, 22).delay(130, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        observable.skipUntil(observable1)
               .subscribe(new Subscriber() {
                   @Override
                   public void onCompleted() {
                       Log.e(TAG, "onCompleted: " );
                   }

                   @Override
                   public void onError(Throwable e) {
                       Log.e(TAG, "onError: " );
                   }

                   @Override
                   public void onNext(Object o) {
                       Log.e(TAG, "onNext: "+o );
                   }
               });
    }
    private void executeSkipWhile(){
        tv2.setText("SkipWhile");
        Observable.range(1,5).skipWhile(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                Log.e(TAG, "call: "+integer);
                return integer<3;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " +integer);
            }
        });
    }
    private void executeTakeUntil(){
        tv2.setText("TakeUntil");
        Observable observable = Observable.just(1, 2, 3,4,5,6).delay(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        Observable observable1 = Observable.just(20, 21, 22).delay(120, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.newThread());
        observable.takeUntil(observable1)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " );
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e(TAG, "onNext: "+o );
                    }
                });
    }
    private void executeTakeWhile(){
        tv2.setText("TakeWhile");
        Observable.range(1,5).takeWhile(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                Log.e(TAG, "call: "+integer);
                return integer<3;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " +integer);
            }
        });
    }
}
