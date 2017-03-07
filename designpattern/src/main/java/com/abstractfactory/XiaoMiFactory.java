package com.abstractfactory;

/**
 * Created by xiehui on 2017/3/7.
 */
public class XiaoMiFactory extends  Factory{
    @Override
    public MobilePhone createMobilePhone(String type) {
        return new MobilePhone(type);
    }
    @Override
    public Television createTelevision(String type) {
        return new Television(type);
    }
}
