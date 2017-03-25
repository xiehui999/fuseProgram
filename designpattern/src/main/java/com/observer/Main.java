package com.observer;

/**
 * Publish-Subscribe（发布-订阅）模式,定义了一种一对多的依赖关系
 * MVC框架,消息推送，事件监听
 */
public class Main {
    public static void main(String[] args) {
        NumberGenerator generator=new RandomNumberGenerator();
        Observer observer1=new DigitObserver();
        Observer observer2=new GraphObserver();
        generator.addObserver(observer1);
        generator.addObserver(observer2);
        generator.execute();
    }
}
