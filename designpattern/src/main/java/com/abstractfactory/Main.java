package com.abstractfactory;
public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory("com.abstractfactory.XiaoMiFactory");
        IMobilePhone mobilePhone1=factory.createMobilePhone("小米2");
        IMobilePhone mobilePhone2=factory.createMobilePhone("小米5");

        ITelevision television1=factory.createTelevision("小米电视2");
        ITelevision television2=factory.createTelevision("小米电视3");

        mobilePhone1.dial();
        mobilePhone2.dial();

        television1.watchTV();
        television2.watchTV();

    }
}
