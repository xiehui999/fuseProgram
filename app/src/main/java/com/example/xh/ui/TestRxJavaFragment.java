package com.example.xh.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.rxjava.NormalRxActivity;
import com.example.xh.uploadfile.FileInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.ConnectableObservable;
import rx.observables.MathObservable;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

/**
 * Created by xiehui on 2016/10/31.
 */
public class TestRxJavaFragment extends Fragment implements View.OnClickListener {

    private String TAG = "RXJAVA";
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15;
    private Button btn16, btn17, btn18, btn19, btn20, btn21, btn22, btn23, btn24, btn25, btn26, btn27, btn28, btn29, btn30;
    private LinearLayout layout;
    private TextView tv;
    private StringBuffer stringBuffer;
    private Subscription subscription;

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
        btn5 = (Button) view.findViewById(R.id.btn5);
        btn6 = (Button) view.findViewById(R.id.btn6);
        btn7 = (Button) view.findViewById(R.id.btn7);
        btn8 = (Button) view.findViewById(R.id.btn8);
        btn9 = (Button) view.findViewById(R.id.btn9);
        btn10 = (Button) view.findViewById(R.id.btn10);
        btn11 = (Button) view.findViewById(R.id.btn11);
        btn12 = (Button) view.findViewById(R.id.btn12);
        btn13 = (Button) view.findViewById(R.id.btn13);
        btn14 = (Button) view.findViewById(R.id.btn14);
        btn15 = (Button) view.findViewById(R.id.btn15);
        btn16 = (Button) view.findViewById(R.id.btn16);
        btn17 = (Button) view.findViewById(R.id.btn17);
        btn18 = (Button) view.findViewById(R.id.btn18);
        btn19 = (Button) view.findViewById(R.id.btn19);
        btn20 = (Button) view.findViewById(R.id.btn20);
        btn21 = (Button) view.findViewById(R.id.btn21);
        btn22 = (Button) view.findViewById(R.id.btn22);
        btn23 = (Button) view.findViewById(R.id.btn23);
        btn24 = (Button) view.findViewById(R.id.btn24);
        btn25 = (Button) view.findViewById(R.id.btn25);
        btn26 = (Button) view.findViewById(R.id.btn26);
        btn27 = (Button) view.findViewById(R.id.btn27);
        btn28 = (Button) view.findViewById(R.id.btn28);
        btn29 = (Button) view.findViewById(R.id.btn29);
        btn30 = (Button) view.findViewById(R.id.btn30);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        tv = (TextView) view.findViewById(R.id.tv);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            // FuncX 包装的是有返回值的方法,ActionX 无
            //Action0中call无参数，Action1有参数
            //变换实质针对事件序列的处理和再发送
            //lift() 是针对事件项和事件序列的，而 compose() 是针对 Observable 自身进行变换
            case R.id.btn1:
                intent.setClass(getContext(), NormalRxActivity.class);
                FileInfo fileInfo = new FileInfo();
                fileInfo.setGguid("1234567");
                fileInfo.setMd5("A563899AC90A9C5");
                fileInfo.setIsChunk(true);
                fileInfo.setFileLength(1000l);
                fileInfo.setFilePath("mnt/sdcard/aaa.jpg");
                intent.putExtra("fileInfo", fileInfo);
                startActivity(intent);
                break;
            case R.id.btn2:
                connect();
                break;
            case R.id.btn3:
                executeFilter();
                break;
            case R.id.btn4:
                //所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列
                //map() 是一对一的转化
                executeMap();
                break;
            case R.id.btn5:
                //可以一对多
                // flatMap() 中返回的是个 Observable 对象，并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中
                executeFlatMap();
                break;
            case R.id.btn6:
                executeSort();
                break;
            case R.id.btn7:
                executeTake();
                break;
            case R.id.btn8:
                executeMerge();
                break;
            case R.id.btn9:
                executeSchedulers();
                break;
            case R.id.btn10:
                executeInterval();
                break;
            case R.id.btn11:
                executeUnsubscribe();
                break;
            case R.id.btn12:
                executeTimestamp();
                break;
            case R.id.btn13:
                executeZip();
                break;
            case R.id.btn14:
                executeConcatMap();
                break;
            case R.id.btn15:
                executeSwitchMap();
                break;
            case R.id.btn16:
                executeDebounce();
                break;
            case R.id.btn17:
                executeDistinct();
                break;
            case R.id.btn18:
                executeElementAt();
                break;
            case R.id.btn19:
                executeFirst();
                break;
            case R.id.btn20:
                executeLast();
                break;
            case R.id.btn21:
                executeSkip();
                break;
            case R.id.btn22:
                executeIgnore();
                break;
            case R.id.btn23:
                executeSample();
                break;
            case R.id.btn24:
                executeSingle();
                break;
            case R.id.btn25:
                executeOfType();
                break;
            case R.id.btn26:
                executetakeFirst();
                break;
            case R.id.btn27:
                executeOfType();
                break;
            case R.id.btn28:
                executeAverage();
                break;
            case R.id.btn29:
                executeMin();
                break;
            case R.id.btn30:
                executeCount();
                break;

        }
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
        //create     RxJava 最基本的创造事件序列的方法
        //在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe()，就在哪个线程生产事件；在哪个线程生产事件，
        // 就在哪个线程消费事件。如果需要切换线程，就需要用到 Scheduler调度器
        //Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler
        //Schedulers.newThread(): 总是启用新线程，并在新线程执行操作
        //Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于
        // io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io()
        // 中，可以避免创建不必要的线程

    }

    private void executeTimestamp() {
        Integer[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        tv.setText("输入数据：1, 2, 3, 4, 5, 6, 7, 8, 9, 10\n");
        Observable.just(1,2,3,4).timestamp().subscribe(new Action1<Timestamped<Integer>>() {
            @Override
            public void call(Timestamped<Integer> integerTimestamped) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                tv.append("value: " + integerTimestamped.getValue() + "       time:   ");
                tv.append(sdf.format(new Date(integerTimestamped.getTimestampMillis())) + "\n");
                Log.e(TAG, "value: " + integerTimestamped.getValue() + "       time:   "+sdf.format(new Date(integerTimestamped.getTimestampMillis())) );
            }
        });
    }

    private void executeUnsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void executeInterval() {
        //Interval运行新的线程中
        tv.setText("定时器，每一秒发送打印一个数字\ninterval(1, TimeUnit.SECONDS)\n");
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        tv.append(" " + aLong + "   ");
                    }
                });


    }

    private void executeSchedulers() {
        //subsribeOn( )可以改变Observable应该在哪个调度器上执行任务。
        //subscribeOn()主要改变的是订阅的线程，即call()执行的线程;observeOn()主要改变的是发送的线程，即onNext()执行的线程。
        //subscribeOn则是一次性的，无论在什么地方调用，总是从改变最原始的observable开始影响整个observable的处理
        //observeOn( )操作符可以改变Observable将事件发送到得线程，也就是Subscriber执行的线程,可多次执行
        //默认情况下，链上的操作符将会在调用.subsribeOn( )的那个线程上执行任务
        stringBuffer = new StringBuffer();
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                //不能执行耗时操作，及更新ui
                stringBuffer.append("\n" + "开始发送事件" + Thread.currentThread().getName() + "\n");
                Drawable drawable = getResources().getDrawable(R.mipmap.dir);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                //指定创建Observable在io中
                .subscribeOn(Schedulers.io())
                //由于map中做耗时操作，通过Observable指定发射数据在新的线程
                .observeOn(Schedulers.newThread())
                .map(new Func1<Drawable, ImageView>() {
                    @Override
                    public ImageView call(Drawable drawable) {
                        ImageView imageView = new ImageView(getActivity());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        imageView.setLayoutParams(params);
                        imageView.setImageDrawable(drawable);

                        return imageView;
                    }
                })
                //操作UI，需要指定在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ImageView>() {
                    @Override
                    public void call(ImageView imageView) {
                        tv.append(stringBuffer.toString() + "接收信息事件" + Thread.currentThread().getName());
                        layout.addView(imageView);
                    }
                });
    }

    private void executeZip() {
        //zip和merage区别是zip是讲两个数据对应项同时输出，而merge是将对应项数据按顺序输出
        //若zip中的两个数据源数据长度不一样时，输出数据取最小值，如下面会打印10项数据而不是11项）
        tv.setText("zip使用");
        List<String> names = new ArrayList<>();
        List<Integer> ages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            names.add("张三" + i);
            ages.add(20 + i);
        }
        ages.add(15);
        Observable observable1 = Observable.from(names).subscribeOn(Schedulers.io());
        Observable observable2 = Observable.from(ages).subscribeOn(Schedulers.io());
        //Func2第三个参数是返回值类型
        Observable.zip(observable1, observable2, new Func2<String, Integer, String>() {
            @Override
            public String call(String name, Integer age) {
                return name + ": " + age;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(String o) {
                Log.e(TAG, "onNext: " + o);
                tv.append("\n" + o);
            }
        });


    }

    private void executeMerge() {
        //任一出错，都会打断合并，若不想中断可以是用mergeDelayError（）
        // 它能从一个Observable中继续发射数据即便是其中有一个抛出了错误。当所有的Observables都完成时，mergeDelayError()将会发射onError()
        tv.setText("并发执行任务开始\n");
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {

                try {
                    subscriber.onNext(100);
                    Thread.sleep(500);
                    subscriber.onError(new Throwable("error"));
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    subscriber.onError(new Throwable("error11"));
                }

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        Observable observable1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(500);
                    subscriber.onNext(100);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread());
        Observable observable2 = Observable.just(1, 2, 3, 4).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        Observable observable3 = Observable.just(6, 7, 8, 9).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        Observable.merge(observable, observable2, observable3)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        tv.append("两个任务都处理完毕！！\n");
                        tv.append("更新数据：\n");
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.e(TAG, "onNext: " + s);
                        tv.append("得到一个数据：" + s + "\n");
                    }
                });
    }

    private void executeTake() {
        tv.setText("输出[1,2,3,4,5,6,7,8,9,10]中第三个和第四个奇数，\ntake(i) 取前i个事件 \ntakeLast(i) 取后i个事件 \ndoOnNext(Action1) 每次观察者中的onNext调用之前调用\n");
        Integer[] number = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Observable.from(number).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 != 0;//过滤掉偶数
            }
        })
                //取前四个值
                .take(4)
                ////取前四个中的后两个,每次调用是作用在前面的基础上的数据
                .takeLast(2)
                //doOnNext作用是啥?允许我们在每次输出一个元素之前做一些额外的事情
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        tv.append("before onNext（）" + integer + "\n");
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv.append("onNext()--->" + integer + "\n");
            }
        });
    }

    private void executeSort() {
        tv.setText("toSortedList 输入参数： 2, 3, 6, 4, 9,2, 8");
        Integer[] integers = {2, 3, 6, 4, 9,2, 8};
        Observable.from(integers)
                .toSortedList()
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(List<Integer> integer) {
                        Log.e(TAG, "call: "+integer.toString() );
                        return Observable.from(integer);
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
                Log.e(TAG, "onNext: "+integer);
                tv.append("\n" + integer);
            }
        });
    }

    private void executeFlatMap() {
        tv.setText("输入参数： 1,2, 3将数据增大100并拼接字符FlatMap，没有保证数据的顺序性");
        Integer[] integers = {1, 2, 3};
        Observable.from(integers).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.e(TAG, "call: FlatMap " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(200);
                            subscriber.onNext(integer + 100 + " FlatMap");
                            subscriber.onCompleted();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: FlatMap");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: FlatMap");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: FlatMap" + s);
                        tv.append("\n 转换后的内容：" + s + "\n");
                    }
                });
    }

    private void executeConcatMap() {
        //concatMap保证结果的顺序性，顺序与输入一致
        //输出是按（call: ConcatMap，onNext: ConcatMap)n  ，最后 onCompleted: ConcatMap，保证了数据源的顺序性
        //而FlatMap是先所有call: ConcatMap，再所有onNext: ConcatMap，最后onCompleted: ConcatMap，是没有保证数据源的顺序性
        tv.setText("输入参数： 1,2, 3将数据增大100并拼接字符ConcatMap,保证了数据源的顺序性");
        Integer[] integers = {1, 2, 3};
        Observable.from(integers).concatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        Log.e(TAG, "call:2 ConcatMap " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(200);
                            subscriber.onNext(integer + 100 + " ConcatMap");
                            subscriber.onCompleted();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ConcatMap");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ConcatMap");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: ConcatMap " + s);
                        tv.append("\n 转换后的内容：" + s + "\n");
                    }
                });
    }

    private void executeSwitchMap() {
        //switch()和flatMap()很像，除了一点:当源Observable发射一个新的数据项时，
        // 如果旧数据项订阅还未完成，就取消旧订阅数据和停止监视那个数据项产生的Observable,开始监视新的数据项.
        tv.setText("输入参数： 2,3,6,4,2,8,2,1,9将数据增大100并拼接字符SwitchMap，通过subscribeOn指定在新线程中模拟并发");
        Integer[] integers = {2, 3, 6, 2, 3, 2, 3,};
        Observable.from(integers).switchMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                Log.e(TAG, "call: SwitchMap" + Thread.currentThread().getName());
                //如果不通过subscribeOn(Schedulers.newThread())在在子线程模拟并发操作，所有数据源依然会全部输出
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just((integer + 100) + "SwitchMap").subscribeOn(Schedulers.newThread());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: SwitchMap");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: SwitchMap");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: SwitchMap " + s);
                tv.append("\n 转换后的内容：" + s + "\n");
            }
        });

    }

    private void connect() {
        String[] strs = {"也许当初忙着微笑和哭泣", "忙着追逐天空中的流星", "人理所当然的忘记", "是谁风里雨里一直默默守护在原地"};

        tv.append("\n");
        Observable observable = Observable.from(strs);
        Action1 action0 = new Action1<String>() {
            @Override
            public void call(String s) {
                tv.append("观察者A 收到：" + s + "\n");
            }
        };
        Action1 action10 = new Action1<String>() {
            @Override
            public void call(String s) {
                tv.append("观察者B 收到：" + s + "\n");
            }
        };
        observable.subscribe(action0);
        observable.subscribe(action10);


        tv.append("\n");
        //获得一个可连接的Observable对象，需调用publish()方法
        ConnectableObservable connectableObservable = Observable.from(strs).publish();
        Action1 action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                tv.append("观察者A  收到:  " + s + "\n");
            }
        };
        Action1 action11 = new Action1<String>() {
            @Override
            public void call(String s) {
                tv.append("观察者B  收到:  " + s + "\n");
            }
        };
        connectableObservable.subscribe(action1);
        connectableObservable.subscribe(action11);
        connectableObservable.connect();
    }

    private void executeFilter() {
        tv.setText("输入1-10,过滤掉能被2整除的数");
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Observable observable = Observable.from(ints).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 != 0;//返回true，就不会过滤掉，过滤掉返回false的值
            }
        });
        Action1 action1 = new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                Log.e(TAG, "call: " + i);
                tv.append(i.toString() + ",");
            }
        };
        observable.subscribe(action1);
    }

    private void executeDebounce() {//debounce英文意思消抖
        //debounce操作符是对源Observable间隔期产生的结果进行过滤，如果在这个规定的间隔期内没有别的结果产生，
        // 则将这个结果提交给订阅者，否则忽略该结果，原理有点像光学防抖.
        tv.setText("输入1-10,过滤掉数据源发送间隔期小于1s的数据（sleep(200*integer)integer为1到10的数据）");
        //这样的话在前一秒内产生了多个数据，从200*5=1000ms既1s开始间隔达到1s，则从第5个数据开始输出
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Observable<String> observable = Observable.from(ints).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                try {
                    Thread.currentThread().sleep(200 * integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Observable.just(integer + "");
            }
        });
        observable.subscribeOn(Schedulers.newThread())
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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
                        tv.append("\n" + s);
                    }
                });
    }

    private void executeDistinct() {
        tv.setText("Distinct去重数据源0, 0, 6, 4, 2, 8, 2, 1, 9, 0");
        Observable.just(0, 0, 6, 4, 2, 8, 2, 1, 9, 0)
                .distinct()
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted:Distinct ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:Distinct ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext:Distinct " + integer);
                        tv.append("\n" + integer);
                    }
                });

    }

    private void executeElementAt() {
        //i不能越界
        tv.setText("ElementAt（i）取第i个值，此例i为4，数据源0, 0, 6, 4, 2, 8, 2, 1, 9, 0");
        Observable.just(0, 0, 6, 4, 2, 8, 2, 1, 9, 0)
                .elementAt(4)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted:ElementAt ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:ElementAt ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext:ElementAt " + integer);
                        tv.append("\n" + integer);
                    }
                });

    }

    private void executeOfType() {
        //过滤某类型的数据,可与filter合用，过滤更复杂的数据
        tv.setText("ofType（type）过滤某类型的数据，此例传的Integer.class，数据源0, \"one\", 6, 4, \"two\", 8, \"three\", 1, \"four\", 0");
        Observable.just(0, "one", 6, 4, "two", 8, "three", 1, "four", 0)
                .ofType(Integer.class)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted:ofType ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:ofType ");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext:ofType " + integer);
                        tv.append("\n" + integer);
                    }
                });

    }

    private void executetakeFirst() {
        tv.setText("takeFirst");
        //first变体，若没有发射任何满足条件的数据，不会抛出异常，不执行onNext()但是会调用onCompleted
        //而对于first，若没有发射任何满足条件的数据，first会抛出一个NoSuchElementException
        Observable.just(10, 11).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 20;
            }
        }).first().subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: " + o.toString());
            }
        });


        Observable.just(10, 11).takeFirst(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                Log.e(TAG, "call: takeFirst");
                return integer > 30;
            }
        }).subscribe(new Subscriber<Object>() {

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: " + o.toString());
            }
        });
    }

    private void executeFirst() {
        //与ElementAt(0)作用相同
        tv.setText("first过滤获得第一个数据此例数据源10,11,12,13");
        Observable.just(10, 11, 12, 13).first().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv.append("\n" + integer);
            }
        });
        tv.append("\nfist（Func1）传过滤参数条件大于12");
        Observable.just(10, 11, 12, 13).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 12;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv.append("\n" + integer);
            }
        });

        tv.append("\nempty().firstOrDefault(10)");
        Observable.empty().firstOrDefault(10).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e(TAG, "firstOrDefault(10)" + o.toString());
                tv.append("\n" + o.toString());
            }
        });
        tv.append("\nempty().firstOrDefault(10)1111111111111");
        Observable.just(11, 11).firstOrDefault(10).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                Log.e(TAG, "firstOrDefault(101)" + o.toString());
                tv.append("\n" + o.toString());
            }
        });
        tv.append("\njust(10).firstOrDefault(10, new Func1) integer>20");
        Observable.just(10).firstOrDefault(15, new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 20;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "firstOrDefault(10)2 " + integer);
                tv.append("\n" + integer);
            }
        });

    }

    private void executeLast() {
        //与ElementAt(0)作用相同
        tv.setText("last过滤获得最后一个数据。此例数据源10,11,12,13");
        Observable.just(10, 11, 12, 13).last().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv.append("\n" + integer);
            }
        });
        tv.append("\nlast（Func1）传过滤参数条件小于12");
        Observable.just(10, 11, 12, 13).last(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 12;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
                tv.append("\n" + integer);
            }
        });
    }

    private void executeSingle() {
        tv.setText("Single检测是不是只有一条数据，否则执行onError()此例数据源10,11,12,13");
/*        Observable.just(10, 11, 12, 13).single().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                tv.append("\nonCompleted");
            }

            @Override
            public void onError(Throwable e) {
                tv.append("\nonError"+e.toString());
            }

            @Override
            public void onNext(Integer integer) {
                tv.append("\n" + integer);
            }
        });*/
        Observable.empty().single().subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                tv.append("\nonCompleted1");
            }

            @Override
            public void onError(Throwable e) {
                tv.append("\nonError1" + e.toString());
            }

            @Override
            public void onNext(Object integer) {
                tv.append("\n1111" + integer);
            }
        });
        tv.append("\nSingle（Func1）传过滤参数条件大于12");
        Observable.just(10, 11, 12, 13).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 12;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                tv.append("\nonCompleted");
            }

            @Override
            public void onError(Throwable e) {
                tv.append("\nonError");
            }

            @Override
            public void onNext(Integer integer) {
                tv.append("\n" + integer);
            }
        });
    }

    private void executeIgnore() {
        tv.setText("ignoreElements不会执行onNext()数据源1,2,3");
        Observable.just(1, 2, 3).ignoreElements().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

                tv.append("\nonCompleted");
            }

            @Override
            public void onError(Throwable e) {
                tv.append("\nonError");
            }

            @Override
            public void onNext(Integer integer) {
                tv.append("\nonNext");
            }
        });
    }

    private void executeSample() {
        //定期扫描源Observable产生的结果，在指定的间隔周期内进行采样
        //sample在线程中执行的，会默认开启一个新线程
        tv.setText("Sample对数据进行采样,本例按1s时间对数据采样，数据源1,2,3.....500ms数据增加1");
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .sample(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: Sample");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: Sample");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext: Sample");
                        tv.append("\n" + aLong);
                        if (aLong > 10) {
                            this.unsubscribe();
                        }

                    }
                });
    }

    private void executeSkip() {
        tv.setText("Skip数据源range(1,10)\n执行Skip(6)");
        Observable.range(1, 10).skip(6).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                tv.append("\n" + integer);
                Log.e(TAG, "call: " + integer);
            }
        });
        //skip(long time,TimeUnit) 是跳过多少秒 然后才开始将后面产生的数据提交给订阅者
        tv.append("\n下面是interval(500,TimeUnit.MILLISECONDS).skip(2,TimeUnit.SECONDS)执行结果");
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .skip(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv.append("\n" + aLong);
                        Log.e(TAG, "onNext: " + aLong);
                        if (aLong > 10) {
                            this.unsubscribe();
                        }
                    }
                });
        //skipLast 正好和skip 相反，忽略最后产生的n个数据项
        tv.append("\nskipLast(6),数据源range(1,10)");
        Observable.range(1, 10).skipLast(6).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
                tv.append("\n" + integer);
            }
        });

    }

    private void executeMap() {
        //可以参考下面的executeMap1方法，学习稍微复杂的例子
        tv.setText("输入参数： 0,0,6,4,2,8,2,1,9,0,23大于5的数据用true表示");
        Integer[] integers = {0, 0, 6, 4, 2, 8, 2, 1, 9, 0, 23};
        Observable.from(integers).map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                Log.e(TAG, "call: " + integer);
                return (integer > 5);
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
                tv.append("\n观察到输出结果：");
                tv.append(aBoolean.toString() + "\n");
            }
        });
    }

    private void executeMap1() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "aaa.jpg";
        Observable.just(path)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {

                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = BitmapFactory.decodeFile(s);
                        Log.e(TAG, "call: Bitmap" + bitmap);
                        return bitmap;
                    }
                }).map(new Func1<Bitmap, ImageView>() {
            @Override
            public ImageView call(Bitmap bitmap) {
                Log.e(TAG, "call: ImageView");
                ImageView imageView = new ImageView(getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                return imageView;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ImageView>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(ImageView imageView) {
                        Log.e(TAG, "onNext: ");
                        tv.append(stringBuffer.toString() + "接收信息事件" + Thread.currentThread().getName());
                        layout.addView(imageView);
                    }
                });
    }

    private void executeAverage(){
        tv.setText("Average");
        Observable observable=Observable.range(1,5);
        MathObservable.averageInteger(observable).subscribe(new Subscriber<Integer>() {

            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+e.toString() );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " +integer);
            }
        });
    }

    private void executeMin(){
        tv.setText("Min/Max  range(1,5)" );
        Observable<Integer> observable=Observable.range(1,5);
        MathObservable.min(observable).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: " );
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: "+e.toString() );
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext: " +integer);
            }
        });
    }
    private void executeCount(){
        tv.setText("Count/Sum range(1,5)" );
        Observable observable=Observable.range(1,5);
        observable.count().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: "+integer );
            }
        });

        MathObservable.sumInteger(observable).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: sum:" +integer);
            }
        });

    }
}
