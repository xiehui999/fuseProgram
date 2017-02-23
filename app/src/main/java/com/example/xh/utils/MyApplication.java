package com.example.xh.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by xiehui on 2017/2/23.
 */
public class MyApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context=getApplicationContext();
    }
    public static Context getAppContext() {
        return MyApplication.context;
    }
}
