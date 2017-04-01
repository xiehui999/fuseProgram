package com.example.xh.alarm.ringing;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
public class AlarmWakeReceiver extends WakefulBroadcastReceiver {

    public final String TAG = this.getClass().getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(AlarmRingingService.ACTION_DISPATCH_ALARM);
        serviceIntent.setClass(context, AlarmRingingService.class);
        serviceIntent.putExtras(intent);
        startWakefulService(context, serviceIntent);
    }
}
