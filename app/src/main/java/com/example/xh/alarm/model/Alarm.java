package com.example.xh.alarm.model;

import android.content.Context;
import android.net.Uri;

import com.example.xh.alarm.ringing.AlarmScheduler;
import com.example.xh.db.DbUtil;
import com.example.xh.utils.MyApplication;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by xiehui on 2017/2/12.
 */
public class Alarm {

    private int mId;
    private String mTitle;
    private int     mTimeHour;
    private int     mTimeMinute;
    private boolean mRepeatingDays[];
    private Uri mAlarmTone;
    private boolean mVibrate;
    private boolean mIsEnabled;
    private int mSignRemindMinutes;


    public Alarm(int id) {
        mId = id;
        mRepeatingDays = new boolean[]{ false, true, true, true, true, true, false };
        mAlarmTone = AlarmUtilities.defaultRingtone();
        mIsEnabled = false;
        mVibrate = true;
        if (id==1){
            mTitle = "签到闹钟";
            mSignRemindMinutes=10;
        }else if(id==2){
            mTitle = "签退闹钟";
            mSignRemindMinutes=30;
        }
        calculateAndAdjustTime();
    }

    public long schedule() {
        Context context = MyApplication.getAppContext();
        if (isEnabled()) {
            AlarmScheduler.cancelAlarm(context, this);
        } else {
            setIsEnabled(true);
        }
        DbUtil.updateAlarm(this);
        return AlarmScheduler.scheduleAlarm(context, this);
    }

    /**
     * 闹铃过一会继续响（五分钟）
     */
    public void snooze() {
        Context context = MyApplication.getAppContext();
        AlarmScheduler.snoozeAlarm(context, this, getAlarmSnoozeDuration());
    }
    public void cancel() {
        Context context = MyApplication.getAppContext();
        setIsEnabled(false);
        AlarmScheduler.cancelAlarm(context, this);
        DbUtil.updateAlarm(this);
    }

    public void onDismiss() {
        Context context = MyApplication.getAppContext();
        if (!isOneShot()) {
            AlarmScheduler.scheduleAlarm(context, this);
        }
    }

    //设置五分钟
    private int getAlarmSnoozeDuration() {
        return (5 * 60) * 1000;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }

    public int getId() {
        return mId;
    }

    public int getTimeHour() {
        return mTimeHour;
    }

    public void setTimeHour(int timeHour) {
        mTimeHour = timeHour;
    }

    public int getTimeMinute() {
        return mTimeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        mTimeMinute = timeMinute;
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        mRepeatingDays[dayOfWeek] = value;
    }

    public Uri getAlarmTone() {
        return mAlarmTone;
    }

    public void setAlarmTone(Uri alarmTone) {
        mAlarmTone = alarmTone;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
        return mRepeatingDays[dayOfWeek];
    }

    public boolean shouldVibrate() {
        return mVibrate;
    }

    public void setVibrate(boolean vibrate){
        mVibrate = vibrate;
    }

    public int getmSignRemindMinutes() {
        return mSignRemindMinutes;
    }

    public void setmSignRemindMinutes(int mSignRemindMinutes) {
        this.mSignRemindMinutes = mSignRemindMinutes;
        calculateAndAdjustTime();
    }

    public boolean[] getmRepeatingDays() {
        return mRepeatingDays;
    }

    public void setmRepeatingDays(boolean[] mRepeatingDays) {
        this.mRepeatingDays = mRepeatingDays;
    }

    /**
     * 判断是否设置了重复
     * @return
     */
    public boolean isOneShot() {
        boolean isOneShot = true;
        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
            if (getRepeatingDay(dayOfWeek - 1)) {
                isOneShot = false;
                break;
            }
        }
        return isOneShot;
    }
    /**
     * 时间调整
     */
    private void calculateAndAdjustTime( ) {
       /* Optsharepre_interface optsharepre_interface=new Optsharepre_interface(MyApp.getAppContext());
        String kqcl = optsharepre_interface.getDataFromPres("KQCL");*/
        int signHour = 0;
        int signMinute = 0;
        int signOutHour = 0;
        int signOutMinute = 0;
        signHour = 8;
        signOutHour = 18;
        signMinute = 30;
        signOutMinute = 0;
        Calendar calendar = Calendar.getInstance();
        if (this.getId()==1){
            calendar.set(Calendar.HOUR_OF_DAY, signHour);
            calendar.set(Calendar.MINUTE, signMinute);
            calendar.add(Calendar.MINUTE,-mSignRemindMinutes);

        }else{
            calendar.set(Calendar.HOUR_OF_DAY, signOutHour);
            calendar.set(Calendar.MINUTE, signOutMinute);
            calendar.add(Calendar.MINUTE,mSignRemindMinutes);
        }
        mTimeHour = calendar.get(Calendar.HOUR_OF_DAY);
        mTimeMinute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mTimeHour=" + mTimeHour +
                ", mTimeMinute=" + mTimeMinute +
                ", mRepeatingDays=" + Arrays.toString(mRepeatingDays) +
                ", mAlarmTone=" + mAlarmTone +
                ", mVibrate=" + mVibrate +
                ", mIsEnabled=" + mIsEnabled +
                ", mSignRemindMinutes=" + mSignRemindMinutes +
                '}';
    }
}
