package com.singleton;

/**
 * Created by xiehui on 2017/3/10.
 */
public class LazySingleton{
    //可见性，有序性
    private static volatile LazySingleton instance;

    private LazySingleton() {
        System.out.println("初始化");
    }

    /**
     * 1,非线程安全
     * @return
     */
    public static LazySingleton getInstance() {
        //多线程同时访问可能会出现多个实例
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * 2,线程安全
     * synchronized会导致同一时刻只能一个线程访问，其他需要等待，会导致效率很低
     * 真正需要同步的是我们第一次初始化的时候
     * @return
     */
    public static synchronized  LazySingleton getInstance1() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    /**
     * 3,双重检测机制
     * 由于instance = new LazySingleton();初始化过程分为三步
     * 1,分配内存2,执行构造方法初始化3,将对象指向分配的内存空间
     * 由于java编译器为了尽可能减少内存操作速度远慢于CPU运行速度所带来的CPU空置的影响，
     * 虚拟机会按照自己的一些规则(这规则后面再叙述)将程序编写顺序打乱——即写在后面的代码在时间顺序上可能会先执行，
     * 而写在前面的代码会后执行——以尽可能充分地利用CPU就会出现指令重排序(happen-before)，从而导致上面的三个步骤执行顺序发生改变。
     * 正常情况下是123，但是如果指令重排后执行为1,3,2那么久会导致instance 为空，进而导致程序出现问题。
     * volatile有防止指令重排序功能，所以为了防止指令重排序可以使用volatile修饰instance
     * @return
     */
    public static  LazySingleton getInstance2() {
        if(instance==null){
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
