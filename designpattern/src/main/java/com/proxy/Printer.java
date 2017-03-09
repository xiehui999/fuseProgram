package com.proxy;

/**
 * Created by xiehui on 2017/3/9.
 */
public class Printer  implements IPrintable {
    private String name;

    public Printer() {
        heavyJob("正在生成Printer实例...");
    }

    public Printer(String name) {
        this.name = name;
        heavyJob("正在生成Printer实例("+name+")...");
    }

    @Override
    public void setPrinterName(String name) {
        this.name=name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String string) {
        System.out.println("======== "+name+" ========");
        System.out.println(string);
    }

    private void heavyJob(String msg){
        System.out.print(msg);
        for (int i = 0; i <5 ; i++) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("生成结束");
    }
}
