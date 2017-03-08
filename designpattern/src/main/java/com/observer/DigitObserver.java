package com.observer;

/**
 * Created by xiehui on 2017/3/8.
 */
public class DigitObserver implements Observer{
    @Override
    public void update(NumberGenerator generator) {
        System.out.println("DigitObserver:"+generator.getNumber());
    }
}
