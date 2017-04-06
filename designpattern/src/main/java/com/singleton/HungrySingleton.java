package com.singleton;

import java.io.Serializable;

/**
 * Created by xiehui on 2017/3/10.
 * 饿汉式：4
 * 它是线程安全的（无需用同步关键字修饰），由于没有加锁，执行效率也相对较高，但是也有一些缺点，在类加载时就初始化，会浪费内存
 */
public class HungrySingleton implements Serializable {
    private static  HungrySingleton instance = new HungrySingleton();
/*    //饿汉式变种5
    static {
        instance = new HungrySingleton();
    }*/
    private HungrySingleton() {
        System.out.println("初始化");
    }

    public static HungrySingleton getInstance(){
        return instance;
    }

    /**
     * 如果序列化，需要加入此方法，否则单例模式无效
     * @see java.io.ObjectStreamClass
     * @return
     */

    private Object readResolve() {
        return instance;
    }

}
