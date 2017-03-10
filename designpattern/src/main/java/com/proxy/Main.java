package com.proxy;

import com.proxy.dynamic.DynamicHandler;

/**
 * Created by xiehui on 2017/3/9.
 */
public class Main {
    public static void main(String [] args){
        IPrintable iPrintable;
        //静态代理
        //iPrintable=new PrinterProxy("Alice");
        //静态代理反射方式
        //iPrintable=new PrinterProxy("Alice","com.proxy.Printer");//或者
        //动态代理方式
        DynamicHandler dynamicHandler=new DynamicHandler();
        iPrintable = (IPrintable) dynamicHandler.createProxy(new Printer("Alice"));
        System.out.println("现在的名字是"+iPrintable.getPrinterName());
        iPrintable.setPrinterName("Bob");
        System.out.println("现在的名字是"+iPrintable.getPrinterName());
        iPrintable.print("Hello ,world");
    }
}
