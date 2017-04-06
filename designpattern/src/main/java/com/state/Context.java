package com.state;

/**
 * 环境类
 * 它是拥有多种状态的对象。由于环境类的状态存在多样性且在不同状态下对象的行为有所不同，
 * 因此将状态独立出去形成单独的状态类。在环境类中维护一个抽象状态类State的实例，
 * 、这个实例定义当前状态，在具体实现时，它是一个State子类的对象。
 */
public interface Context {

    public abstract void setClock(int hour);                // 设置时间
    public abstract void changeState(State state);          // 改变状态
    public abstract void callSecurityCenter(String msg);    // 联系警报中心
    public abstract void recordLog(String msg);             // 在警报中心留下记录
}
