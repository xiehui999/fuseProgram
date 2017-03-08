package com.observer;

import java.util.Random;

/**
 * Created by xiehui on 2017/3/8.
 */
public class RandomNumberGenerator extends NumberGenerator {
    private Random random = new Random();
    private int number;

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void execute() {
        for (int i = 0; i < 5; i++){
            number=random.nextInt(30);
            notifyObservers();
        }
    }
}
