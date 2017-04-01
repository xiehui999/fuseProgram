package com.example.xh.alarm.ringing;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class AlarmRingingService extends Service {

    public static final String ACTION_START_FOREGROUND =
            "com.example.xh.alarm.ringing.AlarmRingingService.START_FOREGROUND";
    public static final String ACTION_STOP_FOREGROUND =
            "com.example.xh.alarm.ringing.AlarmRingingService.STOP_FOREGROUND";
    public static final String ACTION_DISPATCH_ALARM =
            "com.example.xh.alarm.ringing.AlarmRingingService.DISPATCH_ALARM";
    public static final String ALARM_ID = "alarm_id";
    private static final String ALARM_TIME = "alarm_time";
    private static final String NOTIFICATION_TYPE = "notification_type";
    public final String TAG = this.getClass().getSimpleName();

    private final IBinder mBinder = new LocalBinder();
    AlarmRingingController mController;

    public static void startForegroundService(Context context,
                                              int alarmId,
                                              long alarmTime,
                                              String notificationType) {
        Intent serviceIntent = new Intent(AlarmRingingService.ACTION_START_FOREGROUND);
        serviceIntent.setClass(context, AlarmRingingService.class);
        serviceIntent.putExtra(ALARM_ID, alarmId);
        serviceIntent.putExtra(ALARM_TIME, alarmTime);
        serviceIntent.putExtra(NOTIFICATION_TYPE, notificationType);
        context.startService(serviceIntent);
    }

    public static void stopForegroundService(Context context) {
        Intent serviceIntent = new Intent(AlarmRingingService.ACTION_STOP_FOREGROUND);
        serviceIntent.setClass(context, AlarmRingingService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "服务创建onCreate");

        mController = AlarmRingingController.newInstance(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand执行");
        if (intent != null) {
            if (ACTION_DISPATCH_ALARM.equals(intent.getAction())) {
                Log.d(TAG, "开启播放闹钟声音服务");
                mController.registerAlarm(intent);
                AlarmWakeReceiver.completeWakefulIntent(intent);
            } else if (ACTION_START_FOREGROUND.equals(intent.getAction())) {
                Log.d(TAG, "显示活动通知");
                enableForegroundService(intent);
            } else if (ACTION_STOP_FOREGROUND.equals(intent.getAction())) {
                Log.d(TAG, "删除活动的通知");
                disableForegroundService();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "服务销毁");
    }

    private void enableForegroundService(Intent intent) {
        int alarmId =  intent.getIntExtra(ALARM_ID,0);
        String notificationType = intent.getStringExtra(NOTIFICATION_TYPE);
        if (notificationType.equalsIgnoreCase(AlarmNotificationManager.NOTIFICATION_NEXT_ALARM)) {
            long alarmTime = intent.getLongExtra(ALARM_TIME, 0);
            startForeground(AlarmNotificationManager.NOTIFICATION_ID,
                    AlarmNotificationManager.createNextAlarmNotification(this, alarmId, alarmTime));
        } else if (notificationType.equalsIgnoreCase(AlarmNotificationManager.NOTIFICATION_ALARM_RUNNING)) {
            startForeground(AlarmNotificationManager.NOTIFICATION_ID,
                    AlarmNotificationManager.createAlarmRunningNotification(this, alarmId));
        }
    }

    private void disableForegroundService() {
        stopForeground(true);
    }


    public void reportAlarmCompleted() {
        Log.d(TAG, "播放完成");
        mController.alarmRingingSessionCompleted();
    }



    public void silenceAlarmRinging() {
        Log.d(TAG, "静音");
        mController.silenceAlarmRinging();
    }

    public void startAlarmRinging() {
        Log.d(TAG, "开启闹铃声音及震动");
        mController.startAlarmRinging();
    }

    public class LocalBinder extends Binder {
        public AlarmRingingService getService() {
            return AlarmRingingService.this;
        }
    }
}
