package com.example.xh.alarm.ringing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.db.DbUtil;

import java.util.Calendar;
import java.util.List;

public final class AlarmScheduler {

    public static final String ARGS_ALARM_ID = "alarm_id";

    private AlarmScheduler() {
    }

    /**
     * 设置所有Enabled响铃
     *
     * @param context
     */
    public static boolean scheduleAlarms(Context context) {
        List<Alarm> alarms = DbUtil.getAlarms();
        boolean alarmsScheduled = false;
        for (Alarm alarm : alarms) {
            if (alarm.isEnabled()) {
                cancelAlarm(context, alarm);
                scheduleAlarm(context, alarm);
                alarmsScheduled = true;
            }
        }
        return alarmsScheduled;
    }

    /**
     * 实现对应Alarm响铃
     *
     * @param context
     * @param alarm
     * @return
     */
    public static long scheduleAlarm(Context context, Alarm alarm) {
        PendingIntent pendingIntent = createPendingIntent(context, alarm);
        Calendar calenderNow = Calendar.getInstance();
        long time = getAlarmTime(calenderNow, alarm);
        setAlarm(context, time, pendingIntent);
        return time;
    }

    /**
     * 获取响铃时间（long）
     *
     * @param calendarFrom
     * @param alarm
     * @return
     */
    public static long getAlarmTime(Calendar calendarFrom, Alarm alarm) {
        if (alarm.isOneShot()) {
            return getOneShotAlarmTime(calendarFrom, alarm);
        } else {
            return getRepeatingAlarmTime(calendarFrom, alarm);
        }
    }

    /**
     * 当未设置重复时，获取最近一次响铃时间
     *
     * @param calendarFrom
     * @param alarm
     * @return
     */
    private static long getOneShotAlarmTime(Calendar calendarFrom, Alarm alarm) {
        Calendar calendarAlarm = Calendar.getInstance();
        calendarAlarm.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
        calendarAlarm.set(Calendar.MINUTE, alarm.getTimeMinute());
        calendarAlarm.set(Calendar.SECOND, 0);
        calendarAlarm.set(Calendar.MILLISECOND, 0);

        final int nowHour = calendarFrom.get(Calendar.HOUR_OF_DAY);
        final int nowMinute = calendarFrom.get(Calendar.MINUTE);

        // 设置明天
        if ((alarm.getTimeHour() < nowHour) ||
                (alarm.getTimeHour() == nowHour && alarm.getTimeMinute() <= nowMinute)) {
            calendarAlarm.add(Calendar.DATE, 1);
        }

        return calendarAlarm.getTimeInMillis();
    }

    /**
     * 当设置重复时，获取最近一次响铃时间
     *
     * @param calendarFrom
     * @param alarm
     * @return
     */
    private static long getRepeatingAlarmTime(Calendar calendarFrom, Alarm alarm) {
        Calendar calendarAlarm = Calendar.getInstance();
        calendarAlarm.set(Calendar.HOUR_OF_DAY, alarm.getTimeHour());
        calendarAlarm.set(Calendar.MINUTE, alarm.getTimeMinute());
        calendarAlarm.set(Calendar.SECOND, 0);
        calendarAlarm.set(Calendar.MILLISECOND, 0);
        boolean thisWeek = false;

        final int nowDay = calendarFrom.get(Calendar.DAY_OF_WEEK);
        final int nowHour = calendarFrom.get(Calendar.HOUR_OF_DAY);
        final int nowMinute = calendarFrom.get(Calendar.MINUTE);
        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
            if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                    !(dayOfWeek == nowDay && alarm.getTimeHour() < nowHour) &&
                    !(dayOfWeek == nowDay && alarm.getTimeHour() == nowHour &&
                            alarm.getTimeMinute() <= nowMinute)) {
                if (dayOfWeek > nowDay) {
                    calendarAlarm.add(Calendar.DATE, dayOfWeek - nowDay);
                }
                thisWeek = true;
                break;
            }
        }

        if (!thisWeek) {
            for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay) {
                    calendarAlarm.add(Calendar.DATE, (7 - nowDay) + dayOfWeek);
                    break;
                }
            }
        }

        return calendarAlarm.getTimeInMillis();
    }

    /**
     * 设置休息一会（五分钟）闹铃
     *
     * @param context
     * @param alarm
     * @param snoozePeriod
     * @return
     */
    public static long snoozeAlarm(Context context, Alarm alarm, int snoozePeriod) {
        PendingIntent pendingIntent = createPendingIntent(context, alarm);
        Calendar calendarAlarm = Calendar.getInstance();
        long now = calendarAlarm.getTimeInMillis();
        calendarAlarm.setTimeInMillis(now + snoozePeriod);
        long snoozeTime = calendarAlarm.getTimeInMillis();
        setAlarm(context, snoozeTime, pendingIntent);
        return snoozeTime;
    }

    /**
     * 取消闹铃
     *
     * @param context
     * @param alarm
     */
    public static void cancelAlarm(Context context, Alarm alarm) {
        PendingIntent pIntent = createPendingIntent(context, alarm);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    private static PendingIntent createPendingIntent(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AlarmWakeReceiver.class);
        intent.putExtra(ARGS_ALARM_ID, alarm.getId());
        //requestCode 用于取消时所用
        return PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 通过AlarmManager设置定时
     *
     * @param context
     * @param time
     * @param pendingIntent
     */
    private static void setAlarm(Context context, long time, PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //setAndAllowWhileIdle()或者setExactAndAllowWhileIdle()。
            //和set方法类似，这个闹钟运行在系统处于低电模式时有效
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //在规定的时间精确的执行闹钟，比set方法设置的精度更高
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }
}
