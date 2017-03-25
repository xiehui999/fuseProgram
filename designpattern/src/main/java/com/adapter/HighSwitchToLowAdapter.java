package com.adapter;

/**a
 *
 * Created by xiehui on 2017/3/10.
 */
public class HighSwitchToLowAdapter implements IHighPressure{
    private ILowPressure iLowPressure;
    public HighSwitchToLowAdapter(ILowPressure iLowPressure) {
        this.iLowPressure=iLowPressure;
    }

    @Override
    public void light() {
        System.out.println("将高压电转换为低压");
        iLowPressure.light();
        System.out.println("高压电已转换，可以亮LowPressureLamp");
    }
}
