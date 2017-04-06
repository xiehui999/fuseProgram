package com.visitor;

/**
 * 定义：表示一个作用于其 对象结构 中的  各元素的操作 ，它使你可以在 不改变各元素类 的前提下定义作用于这些元素的新操作。
 * 使用访问者模式的前提是数据结构相对稳定，元素的添加会破坏开闭原则，访问者的添加又符合开闭原则
 * 适用场景：
 * 1 一个对象结构包含多个类型的对象，希望对这些对象实施一些依赖其具体类型的操作。在访问者中针对每一种具体的类型都提供了一个访问操作，不同类型的对象可以有不同的访问操作。
 * 2 需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作“污染”这些对象的类，也不希望在增加新操作时修改这些类。
 * 访问者模式使得我们可以将相关的访问操作集中起来定义在访问者类中，对象结构可以被多个不同的访问者类所使用，将对象本身与对象的访问操作分离。+
 * 3 对象结构中对象对应的类很少改变，但经常需要在此对象结构上定义新的操作。
 * 访问者模式的优点：
 * 1、使得数据结构和作用于结构上的操作解耦，使得操作集合可以独立变化。
 * 2、添加新的操作或者说访问者会非常容易。
 * 3、将对各个元素的一组操作集中在一个访问者类当中。
 * 访问者模式的缺点：
 * 1、增加新的元素会非常困难。
 * 2、实现起来比较复杂，会增加系统的复杂性。
 * 3、破坏封装，如果将访问行为放在各个元素中，则可以不暴露元素的内部结构和状态，但使用访问者模式的时候，为了让访问者能获取到所关心的信息，
 * 元素类不得不暴露出一些内部的状态和结构，就像收入和支出类必须提供访问金额和单子的项目的方法一样。
 *
 * 双重分派机制？
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Making root entries...");
            Directory rootdir = new Directory("root");
            Directory bindir = new Directory("bin");
            Directory tmpdir = new Directory("tmp");
            Directory usrdir = new Directory("usr");
            rootdir.add(bindir);
            rootdir.add(tmpdir);
            rootdir.add(usrdir);
            bindir.add(new File("vi", 10000));
            bindir.add(new File("latex", 20000));

            rootdir.accept(new ListVisitor());

            System.out.println("");
            System.out.println("Making user entries...");
            Directory yuki = new Directory("yuki");
            Directory hanako = new Directory("hanako");
            Directory tomura = new Directory("tomura");
            usrdir.add(yuki);
            usrdir.add(hanako);
            usrdir.add(tomura);
            yuki.add(new File("diary.html", 100));
            yuki.add(new File("Composite.java", 200));
            hanako.add(new File("memo.tex", 300));
            tomura.add(new File("game.doc", 400));
            tomura.add(new File("junk.mail", 500));
            rootdir.accept(new ListVisitor());
        } catch (FileTreatmentException e) {
            e.printStackTrace();
        }
    }
}
