package com.templatemethod;

public class CharDisplay extends AbstractDisplay {
    private char ch;                                // 需要显示的字符
    public CharDisplay(char ch) {                   // 构造函数中接收的字符被
        this.ch = ch;
    }
    public void open() {
        System.out.print("<<");                     // 显示开始字符"<<"
    }
    public void print() {
        System.out.print(ch);
    }
    public void close() {
        System.out.println(">>");
    }
}
