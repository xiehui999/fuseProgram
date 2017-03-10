package com.templatemethod;

public class StringDisplay extends AbstractDisplay {    // StringDisplay也是AbstractDisplay的子类
    private String string;                              // 需要显示的字符串
    private int width;                                  // 以字节为单位计算出的字符串长度
    public StringDisplay(String string) {
        this.string = string;
        this.width = string.getBytes().length;
    }
    public void open() {
        printLine();
    }
    public void print() {
        System.out.println("|" + string + "|");
    }
    public void close() {
        printLine();
    }
    private void printLine() {
        System.out.print("+");
        for (int i = 0; i < width; i++) {               // 显示width个"-"
            System.out.print("-");                      // 组成方框的边框
        }
        System.out.println("+");                        // /显示表示方框的角的"+"
    }
}
