package com.singleton;

/**
 * Created by xiehui on 2017/3/10.
 * 默认枚举实例的创建是线程安全的7
 */
public enum EnumSinglton {
    INSTANCE;

    private EnumSinglton() {
        System.out.println("构造方法");
    }
    public void  doSomething() {
        System.out.println("调用单例方法");
    }

}
