package com.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xiehui on 2017/3/9.
 * 动态代理的Handler
 */
public class DynamicHandler implements InvocationHandler {
    // 目标对象
    private Object targetObject;

    //创建代理对象 这段也可以不在此类，也可以放在客户端里面
    public Object createProxy(Object targetOjbect) {
        this.targetObject = targetOjbect;
        /**
         * 创建代理对象
         * Proxy.newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
         * loader：代理类的类加载器
         * interfaces：指定代理类所实现的接口
         * h：动态代理对象在调用方法的时候，关联的InvocationHandler对象
         */
        return Proxy.newProxyInstance(targetOjbect.getClass().getClassLoader(),
                targetOjbect.getClass().getInterfaces(), this);
    }

    /**
     * InvocationHandler接口所定义的唯一的一个方法，该方法负责集中处理动态代理类上的所有方法的调用。
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上执行
     * @param proxy  代理类的实例
     * @param method  代理类被调用的方法
     * @param args   调用方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //触发真实对象之前或者之后可以做一些额外操作
        Object result = null;
        System.out.println("method:"+method.getName()+"proxy:"+proxy.getClass().getName());
        System.out.println("Before invoke ...");
        result = method.invoke(this.targetObject, args);//通过反射执行某个类的某方法
        System.out.println("After invoke ...");
        return result;
    }
}
