package com.adapter;

/**
 * Created by xiehui on 2017/3/10.
 */
public class LowPressureLamp implements ILowPressure{
    @Override
    public void light() {
        System.out.println("LowPressureLamp");
    }
}
