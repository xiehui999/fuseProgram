
package com.example.xh.alarm.ringing;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.xh.MainActivity;
import com.example.xh.R;
import com.example.xh.alarm.AlarmRingingActivity;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.model.AlarmUtilities;
import com.example.xh.db.DbUtil;

import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * 通知管理类
 */
public class AlarmNotificationManager {
    public final static int NOTIFICATION_ID = 60653426;
    public static final String NOTIFICATION_NEXT_ALARM = "next_alarm";
    public static final String NOTIFICATION_ALARM_RUNNING = "alarm_running";

    private static final String TAG = "AlarmNotificationMgr";
    private static AlarmNotificationManager sManager;

    private Context mContext;
    private int mCurrentAlarmId;
    private long mCurrentAlarmTime;
    private boolean mNotificationsActive;

    private AlarmNotificationManager(Context context) {
        mContext = context;
        resetState();
    }

    public static AlarmNotificationManager get(Context context) {
        if (sManager == null) {
            sManager = new AlarmNotificationManager(context);
            Log.d(TAG, "初始化");
        }
        return sManager;
    }

    /**
     * 创建下一次响铃通知
     * @param context
     * @param alarmId
     * @param alarmTime
     * @return
     */
    public static Notification createNextAlarmNotification(Context context, int alarmId, long alarmTime) {
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
         String remind="";
        if (alarmId==1){
            remind="签到闹钟";
        }else{
            remind="签退闹钟";
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("下次响铃时间")
                .setSubText(remind)
                .setContentText(AlarmUtilities.getDayAndTimeAlarmDisplayString(context, alarmTime))
                .setPriority(Notification.PRIORITY_LOW)
                .setVisibility(Notification.VISIBILITY_PRIVATE);

        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                alarmId, startIntent, 0);
        builder.setContentIntent(contentIntent);
        return builder.build();
    }

    /**
     * 创建正在响铃通知
     * @param context
     * @param alarmId
     * @return
     */
    public static Notification createAlarmRunningNotification(Context context, int alarmId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(icon);
        builder.setContentTitle("正在响铃");
        String title = "";
        if (alarmId==1){
            title="签到闹钟";
        }else{
            title="签退闹钟";
        }
        builder.setContentText(title);
        Intent ringingIntent = new Intent(context, AlarmRingingActivity.class);
        ringingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        ringingIntent.putExtra(AlarmRingingService.ALARM_ID, alarmId);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                alarmId, ringingIntent, 0);
        builder.setContentIntent(contentIntent);
        return builder.build();
    }

    /**
     * 发送通知到通知栏
     */
    public void handleNextAlarmNotificationStatus() {
        List<Alarm> alarms = DbUtil.getAlarms();
        Calendar now = Calendar.getInstance();
        SortedMap<Long, Integer> alarmValues = new TreeMap<>();
        for (Alarm alarm : alarms) {
            if (alarm.isEnabled()) {
                alarmValues.put(AlarmScheduler.getAlarmTime(now, alarm), alarm.getId());
            }
        }
        if (!alarmValues.isEmpty()) {
            Long alarmTime = alarmValues.firstKey();
            int alarmId = alarmValues.get(alarmTime);
            if (!doesCurrentStateMatchAlarmDetails(alarmId, alarmTime)) {
                updateStateWithAlarmDetails(alarmId, alarmTime);
                AlarmRingingService.startForegroundService(mContext,
                        mCurrentAlarmId,
                        mCurrentAlarmTime,
                        NOTIFICATION_NEXT_ALARM);
            }
        } else {
            disableNotifications();
        }
    }

    public void handleAlarmRunningNotificationStatus(int alarmId) {
        updateStateWithAlarmDetails(alarmId, 0);
        AlarmRingingService.startForegroundService(mContext,
                alarmId,
                0,
                NOTIFICATION_ALARM_RUNNING);

    }

    /**
     * 如果有通知则关闭通知，并重置数据
     */
    public void disableNotifications() {
        if (mNotificationsActive) {
            AlarmRingingService.stopForegroundService(mContext);
            resetState();
        }
    }

    private void updateStateWithAlarmDetails(int alarmId, long alarmTime) {
        mNotificationsActive = true;
        mCurrentAlarmId = alarmId;
        mCurrentAlarmTime = alarmTime;
    }

    /**
     * 判断是否发送过该通知
     * @param alarmId
     * @param alarmTime
     * @return
     */
    private boolean doesCurrentStateMatchAlarmDetails(int alarmId, long alarmTime) {
        return (mCurrentAlarmTime == alarmTime &&
                mCurrentAlarmId==alarmId);
    }

    private void resetState() {
        mNotificationsActive = false;
        mCurrentAlarmId = 0;
        mCurrentAlarmTime = 0;
    }

}
