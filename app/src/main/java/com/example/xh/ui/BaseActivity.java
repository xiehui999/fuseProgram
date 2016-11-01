package com.example.xh.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by xiehui on 2016/8/3.
 */
public abstract class BaseActivity extends AppCompatActivity  implements View.OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initViews();
    }

    /**
     * layoutResID
     */
    public abstract int getContentViewId();
    /**
     * Initialization view
     * */
    public abstract  void initViews();

}
