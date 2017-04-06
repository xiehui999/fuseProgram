package com.chainofresponsibility;

public class NoSupport extends Support {
    public NoSupport(String name) {
        super(name);
    }
    protected boolean resolve(com.chainofresponsibility.Trouble trouble) {     // 解决问题的方法
        return false; // 自己什么也不处理
    }
}
