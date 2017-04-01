package com.example.xh.alarm.model;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.Window;
import android.view.WindowManager;
import com.example.xh.R;
import com.example.xh.alarm.AlarmSettingFragment;

import java.text.Format;
import java.util.Calendar;

/**
 * Created by xiehui on 2017/2/14.
 */
public class AlarmUtilities {
    public static AlarmSettingFragment getAlarmSettingFragment(FragmentManager fragmentManager) {
        return (AlarmSettingFragment)fragmentManager
                .findFragmentByTag(AlarmSettingFragment.SETTINGS_FRAGMENT_TAG);
    }
    public static boolean areEditingSettings(FragmentManager fragmentManager) {
        return (getAlarmSettingFragment(fragmentManager) != null);
    }


    /**
     * 打开闹铃设置界面
     * @param fragmentManager
     * @param alarmId
     */
    public static void transitionFromAlarmDataToSettings(FragmentManager fragmentManager,
                                                         int alarmId) {
        showFragmentFromRight(fragmentManager,
                AlarmSettingFragment.newInstance(alarmId),
                AlarmSettingFragment.SETTINGS_FRAGMENT_TAG);
    }




    public static Uri defaultRingtone() {
        return Uri.parse("android.resource://" + "com.example.xh" + "/" + R.raw.ringtone);
    }



    public static void showFragment(FragmentManager fragmentManager, Fragment fragment,
                                    String fragmentTag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, fragmentTag);
        transaction.commit();
    }

    public static void showFragmentFromRight(FragmentManager fragmentManager, Fragment fragment,
                                             String fragmentTag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        transaction.replace(R.id.fragment_container, fragment, fragmentTag);
        transaction.commit();
    }

    public static void showFragmentFromLeft(FragmentManager fragmentManager, Fragment fragment,
                                            String fragmentTag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, fragment, fragmentTag);
        transaction.commit();
    }
    public static String getUserTimeString(Context context, int hour, int minute) {
        Format formatter = android.text.format.DateFormat.getTimeFormat(context);
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        return formatter.format(calendar.getTime());
    }

    public static String[] getShortDayNames() {
        String[] dayNames = {"周日","周一","周二","周三","周四","周五","周六"};
        return dayNames;
    }
    public static String getRepeatingDayString( int[] daysOfWeek) {
        String dayName = "";
        String[] dayNames= getShortDayNames();
        for (int i=0 ;i<daysOfWeek.length;i++) {
            if (i==0){
                dayName=dayNames[daysOfWeek[i]-1];
            }else{
                dayName=dayName+","+dayNames[daysOfWeek[i]-1];
            }
        }
        return dayName;
    }

    /**
     * 设置闹钟生效后提示多久之后响铃
     * @param context
     * @param timeUntilAlarm
     * @return
     */
    public static String getTimeUntilAlarmDisplayString(Context context, long timeUntilAlarm) {
        Calendar calendarNow = Calendar.getInstance();
        Calendar calendarAlarm = Calendar.getInstance();
        calendarAlarm.setTimeInMillis(timeUntilAlarm);
        int days = Math.max(0, calendarAlarm.get(Calendar.DATE) - calendarNow.get(Calendar.DATE));
        int hours = Math.max(0, calendarAlarm.get(Calendar.HOUR_OF_DAY) - calendarNow.get(Calendar.HOUR_OF_DAY));
        int minutes = Math.max(0, calendarAlarm.get(Calendar.MINUTE) - calendarNow.get(Calendar.MINUTE));
        String toast = "";

        if (days > 0) {
            if (hours > 0 && minutes > 0) {
                toast = "闹铃将在" + days + "天" + hours + "小时" + minutes + "分钟后响";
            } else if (hours > 0) {
                toast = "闹铃将在" + days + "天" + hours + "小时后响";
            } else if (minutes > 0) {
                toast = "闹铃将在" + days + "天" + minutes + "分钟后响";
            } else {
                toast = "闹铃将在" + days + "天后响";
            }
        } else if (hours > 0) {
            if (minutes > 0) {
                toast = "闹铃将在" + hours + "小时" + minutes + "分钟后响";
            } else {
                toast = "闹铃将在" + hours + "小时后响";
            }
        } else if (minutes > 0) {
            toast = "闹铃将在" + minutes + "分钟后响";
        } else {
            toast = "闹铃将在一分钟内响";
        }
        return toast;
    }

    public static String getDayAndTimeAlarmDisplayString(Context context, long timeUntilAlarm) {
        return DateUtils.formatDateTime(context, timeUntilAlarm, DateUtils.FORMAT_SHOW_TIME |
                DateUtils.FORMAT_SHOW_WEEKDAY);
    }
}
