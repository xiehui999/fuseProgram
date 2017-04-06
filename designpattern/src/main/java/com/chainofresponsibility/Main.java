package com.chainofresponsibility;

/**
 * 责任链模式是一种对象的行为模式。有多个对象，每个对象持有对下一个对象的引用，这样就会形成一条链，请求在这条链上传递，
 * 直到某一对象决定处理该请求。但是发 出者并不清楚到底最终那个对象会处理该请求，所以，责任链模式可以实现，
 * 在隐瞒客户端的情况下，对系统进行动态的调整
 *
 * 将一个事件处理流程分派到一组执行对象上去，这一组执行对象形成一个链式结构，事件处理请求在这一组执行对象上进行传递
 * 角色：事件处理请求对象，执行对象
 *switch()case 0:结构
 * 场景：请假差旅申请
 * JavaWeb中的过滤器链和Struts2中的拦截器,点击事件
 *
 */
public class Main {
    public static void main(String[] args) {
        Support alice   = new NoSupport("Alice");
        Support bob     = new LimitSupport("Bob", 100);
        Support charlie = new SpecialSupport("Charlie", 429);
        Support diana   = new LimitSupport("Diana", 200);
        Support elmo    = new OddSupport("Elmo");
        Support fred    = new LimitSupport("Fred", 300);
        // 形成职责链
        alice.setNext(bob).setNext(charlie).setNext(diana).setNext(elmo).setNext(fred);
        // 制造各种问题
        for (int i = 0; i < 500; i += 33) {
            alice.support(new com.chainofresponsibility.Trouble(i));
        }
    }
}
