package com.templatemethod;

public abstract class AbstractDisplay { // 抽象类AbstractDisplay
    public abstract void open();
    public abstract void print();
    public abstract void close();
    public final void display() {
        open();                         // 首先打开…
        for (int i = 0; i < 5; i++) {   // 循环调用5次print
            print();                    
        }
        close();                        // …最后关闭。这就是display方法所实现的功能
    }
}
