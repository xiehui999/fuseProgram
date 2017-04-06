package com.bridge;

/**
 * 将抽象部分与它的实现部分分离，使它们都可以独立地变化。它是一种对象结构型模式。
 *
 * Bridge模式基于类的最小设计原则，通过使用封装，聚合以及继承等行为来让不同的类
 * 承担不同的功能。它的主要特点是把抽象（abstraction）与行为实现（implementation）分离开来，
 * 从而可以保持各部分的独立性以及应对它们的功能扩展。
 * 作用是将“类的功能层次结构”和“类的实现层次结构”之间搭建桥梁(两个维度)
 * 1，扩展父类的功能，2，模板方法，父类抽象方法，子类去实现
 *
 * 适用场景
 1. 你不希望抽象和实现有固定的关系，希望可以在运行时修改实现的方式。
 2. 抽象和实现部分都可以独立的扩展，而不相互影响。
 适用场景

 在以下情况下可以考虑使用桥接模式：

 (1)如果一个系统需要在抽象化和具体化之间增加更多的灵活性，避免在两个层次之间建立静态的继承关系，通过桥接模式可以使它们在抽象层建立一个关联关系。

 (2)“抽象部分”和“实现部分”可以以继承的方式独立扩展而互不影响，在程序运行时可以动态将一个抽象化子类的对象和一个实现化子类的对象进行组合，即系统需要对抽象化角色和实现化角色进行动态耦合。

 (3)一个类存在两个（或多个）独立变化的维度，且这两个（或多个）维度都需要独立进行扩展。

 (4)对于那些不希望使用继承或因为多层继承导致系统类的个数急剧增加的系统，桥接模式尤为适用
 */
public class Main {
    public static void main(String[] args) {
        RandomCountDisplay d = new RandomCountDisplay(new com.bridge.StringDisplayImpl("Hello, China."));
        d.randomDisplay(10);

        Display d1 = new Display(new com.bridge.StringDisplayImpl("Hello, China."));
        Display d2 = new com.bridge.CountDisplay(new com.bridge.StringDisplayImpl("Hello, World."));
        com.bridge.CountDisplay d3 = new com.bridge.CountDisplay(new com.bridge.StringDisplayImpl("Hello, Universe."));
        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
    }
}
