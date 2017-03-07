package com.abstractfactory;

/**
 * Created by xiehui on 2017/3/7.
 */
public class MobilePhone extends IMobilePhone {
    private String name;
    public MobilePhone(String name) {
        this.name=name;
        System.out.println("制作手机"+name);
    }

    @Override
    public void dial() {
        System.out.println("使用"+name+"打电话");
    }
}
