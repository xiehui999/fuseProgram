package com.abstractfactory;

/**
 * Created by xiehui on 2017/3/7.
 */
public class Television extends ITelevision {
    private String name;
    public Television(String name) {
        this.name=name;
        System.out.println("制作电视"+name);
    }

    @Override
    public void watchTV() {
        System.out.println("通过"+name+"看电视");
    }
}
