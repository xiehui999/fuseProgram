package com.singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xiehui on 2017/3/10.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        //测试序列化对单例模式的影响
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new ObjectOutputStream(bos).writeObject(LazySingleton.getInstance());
        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
        LazySingleton singleton = (LazySingleton) new ObjectInputStream(bin).readObject();
        System.out.println(singleton == LazySingleton.getInstance());//true


        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
    }
}
