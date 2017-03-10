package com.adapter;

/**
 * Created by xiehui on 2017/3/10.
 */
public class Main {
    public static void main(String[] args) {
        IHighPressure highPressureLamp = new HighPressureLamp();
        highPressureLamp.light();
        ILowPressure iLowPressure = new LowPressureLamp();
        HighSwitchToLowAdapter highSwitchToLowAdapter = new HighSwitchToLowAdapter(iLowPressure);
        highSwitchToLowAdapter.light();
    }
}
