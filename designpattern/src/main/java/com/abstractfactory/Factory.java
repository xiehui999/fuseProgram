package com.abstractfactory;

/**
 * Created by xiehui on 2017/3/7.
 */
public abstract class Factory {
    public static Factory getFactory(String classname) {
        Factory factory = null;
        try {
            factory = (Factory)Class.forName(classname).newInstance();
        } catch (ClassNotFoundException e) {
            System.err.println("没有找到 " + classname + "类。");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }
    public abstract MobilePhone createMobilePhone(String type);
    public abstract Television createTelevision(String type);
}
