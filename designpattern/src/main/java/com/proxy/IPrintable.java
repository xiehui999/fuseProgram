package com.proxy;

/**
 * Created by xiehui on 2017/3/9.
 */
public interface IPrintable {
    public abstract void setPrinterName(String name);
    public abstract String getPrinterName();
    public abstract void print(String string);
}
