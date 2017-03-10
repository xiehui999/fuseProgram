package com.proxy;

/**
 * Created by xiehui on 2017/3/9.
 */
public class PrinterProxy implements IPrintable {
    private String name;
    private Printer real;
    private IPrintable real1;
    private String className;

    public PrinterProxy() {
    }

    public PrinterProxy(String name) {
        this.name = name;
    }

    //运用反射，可以让代理不用知道Printer类，不用反射调用
    public PrinterProxy(String name, String className) {
        this.name = name;
        this.className = className;
    }

    @Override
    public synchronized void setPrinterName(String name) {
        if (real != null) {
            real.setPrinterName(name);
        }
        this.name = name;
    }

    @Override
    public String getPrinterName() {
        return name;
    }

    @Override
    public void print(String string) {
        realize();
        if (real1==null){
            real.print(string);
        }else{
            real1.print(string);
        }
    }

    private synchronized void realize() {
        if (real == null) {
            if (className != null) {
                try {
                    real1 = (IPrintable) Class.forName(className).newInstance();
                    real1.setPrinterName(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                real = new Printer(name);
            }
        }
    }
}
