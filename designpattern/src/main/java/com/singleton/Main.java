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
        new ObjectOutputStream(bos).writeObject(HungrySingleton.getInstance());
        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
        HungrySingleton singleton = (HungrySingleton) new ObjectInputStream(bin).readObject();


        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        new ObjectOutputStream(bos1).writeObject(EnumSinglton.INSTANCE);
        ByteArrayInputStream bin1 = new ByteArrayInputStream(bos1.toByteArray());
        EnumSinglton singleton1 = (EnumSinglton) new ObjectInputStream(bin1).readObject();
        System.out.println(singleton == HungrySingleton.getInstance());//false,经过处理后返回false
        System.out.println(singleton1 == EnumSinglton.INSTANCE);//true


        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
        EnumSinglton.INSTANCE.doSomething();
    }
}
