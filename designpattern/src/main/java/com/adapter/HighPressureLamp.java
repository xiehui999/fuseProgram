package com.adapter;

/**
 * Created by xiehui on 2017/3/10.
 */
public class HighPressureLamp implements IHighPressure{
    @Override
    public void light() {
        System.out.println("HighPressureLamp");
    }
}
