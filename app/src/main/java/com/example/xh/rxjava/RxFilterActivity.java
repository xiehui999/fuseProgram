package com.example.xh.rxjava;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xh.R;
import com.example.xh.ui.BaseActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by xiehui on 2016/11/1.
 */
public class RxFilterActivity extends BaseActivity{
    private TextView tv1,tv2;
    private Button btn;
    @Override
    public int getContentViewId() {
        return R.layout.rxjava_layout1;
    }

    @Override
    public void initViews() {
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
        tv1.setText("输入1-10,过滤掉能被2整除的数");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                execute();
                break;
        }
    }

    private void execute() {
        Integer []ints={1,2,3,4,5,6,7,8,9};
        Observable observable=Observable.from(ints).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer%2!=0;//返回true，就不会过滤掉，过滤掉返回false的值
            }
        });
        Action1 action1=new Action1<Integer>(){
            @Override
            public void call(Integer i) {
                tv1.append(i.toString()+",");
            }
        };
        observable.subscribe(action1);
    }
}
