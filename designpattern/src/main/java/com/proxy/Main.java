package com.proxy;

/**
 * Created by xiehui on 2017/3/9.
 */
public class Main {
    public static void main(String [] args){
        IPrintable iPrintable=new PrinterProxy("Alice");
        System.out.println("现在的名字是"+iPrintable.getPrinterName());
        iPrintable.setPrinterName("Bob");
        System.out.println("现在的名字是"+iPrintable.getPrinterName());
        iPrintable.print("Hello ,world");
    }
}
