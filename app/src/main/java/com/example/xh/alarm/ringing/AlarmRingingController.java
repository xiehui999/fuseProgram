package com.example.xh.alarm.ringing;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.xh.alarm.AlarmRingingActivity;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.db.DbUtil;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * add       增加一个元索               如果队列已满，则抛出一个IIIegaISlabEepeplian异常
 * remove    移除并返回队列头部的元素     如果队列为空，则抛出一个NoSuchElementException异常
 * element   返回队列头部的元素            如果队列为空，则抛出一个NoSuchElementException异常
 * offer     添加一个元素并返回true     如果队列已满，则返回false
 * poll      移除并返问队列头部的元素    如果队列为空，则返回null
 * peek      返回队列头部的元素             如果队列为空，则返回null
 * put       添加一个元素                      如果队列满，则阻塞
 * take      移除并返回队列头部的元素     如果队列为空，则阻塞
 */
public final class AlarmRingingController {
    private Context mContext;
    private AlarmRingtonePlayer mRingtonePlayer;
    private AlarmVibrator mVibrator;
    private Alarm mCurrentAlarm;

    Queue<Intent> mAlarmIntentQueue;

    public AlarmRingingController(Context context) {
        mContext = context;
        mRingtonePlayer = new AlarmRingtonePlayer(mContext);
        mVibrator = new AlarmVibrator(mContext);
        mAlarmIntentQueue = new LinkedList<>();
    }

    public static AlarmRingingController newInstance(Context context) {
        return new AlarmRingingController(context);
    }

    protected void registerAlarm(Intent intent) {
        /**
         * offer，add区别：
         *一些队列有大小限制，因此如果想在一个满的队列中加入一个新项，多出的项就会被拒绝。
         *这时新的 offer 方法就可以起作用了。它不是对调用 add() 方法抛出一个 unchecked 异常，
         *而只是得到由 offer() 返回的 false。
         */
        if (mAlarmIntentQueue.offer(intent) &&
                mAlarmIntentQueue.size() == 1) {
            beforeDispatchFirstAlarmRingingSession();
            dispatchAlarmRingingSession(mAlarmIntentQueue.peek());
        }
    }

    public void beforeDispatchFirstAlarmRingingSession() {
        mRingtonePlayer.initialize();
        mVibrator.initialize();
        SharedWakeLock.get(mContext).acquireFullWakeLock();
    }


    protected void alarmRingingSessionCompleted() {
        silenceAlarmRinging();
        mCurrentAlarm = null;
        /**
         * poll，remove区别：
         * remove() 和 poll() 方法都是从队列中删除第一个元素。remove() 的行为与 Collection 接口的版本相似，
         * 但是新的 poll() 方法在用空集合调用时不是抛出异常，只是返回 null。因此新的方法更适合容易出现异常条件的情况。
         */
        if (mAlarmIntentQueue.poll() != null) {
            /**
             * peek，element区别：
             * element() 和 peek() 用于在队列的头部查询元素。与 remove() 方法类似，在队列为空时， element() 抛出一个异常，而 peek() 返回 null
             */
            dispatchAlarmRingingSession(mAlarmIntentQueue.peek());
        }
        if (mAlarmIntentQueue.isEmpty()) {
            allAlarmRingingSessionsComplete();
        }
    }

    public void allAlarmRingingSessionsComplete() {
        mVibrator.cleanup();
        mRingtonePlayer.cleanup();

        SharedWakeLock.get(mContext).releaseFullWakeLock();
        AlarmNotificationManager.get(mContext).handleNextAlarmNotificationStatus();
    }

    public void dispatchAlarmRingingSession(Intent intent) {
        if (intent != null) {
            int alarmId = intent.getExtras().getInt(AlarmScheduler.ARGS_ALARM_ID);
            mCurrentAlarm = DbUtil.getAlarm(alarmId);
            Calendar calendar = Calendar.getInstance();
            Calendar alarmCalendar = Calendar.getInstance();
            alarmCalendar.set(Calendar.HOUR_OF_DAY, mCurrentAlarm.getTimeHour());
            alarmCalendar.set(Calendar.MINUTE, mCurrentAlarm.getTimeMinute());
            //在华为手机上测试会提前响，若提前5s到15分钟内激活闹铃，则取消闹铃重新设置闹铃，调整闹铃准确度
            if ((alarmCalendar.getTimeInMillis() - calendar.getTimeInMillis()) > 5 * 1000 && (alarmCalendar.getTimeInMillis() - calendar.getTimeInMillis()) < 15 * 60 * 1000) {
                mCurrentAlarm.schedule();
            } else {
                startAlarmRinging();
                launchRingingUserExperience(alarmId);
                AlarmNotificationManager.get(mContext).handleAlarmRunningNotificationStatus(alarmId);
            }

        }
    }

    public void silenceAlarmRinging() {
        mVibrator.stop();
        mRingtonePlayer.stop();
    }

    public void startAlarmRinging() {
        if (mCurrentAlarm.shouldVibrate()) {
            mVibrator.vibrate();
        }
        Uri ringtone = mCurrentAlarm.getAlarmTone();
        if (ringtone != null) {
            mRingtonePlayer.play(ringtone);
        }
    }

    private void launchRingingUserExperience(int alarmId) {
        Intent ringingIntent = new Intent(mContext, AlarmRingingActivity.class);
        ringingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        ringingIntent.putExtra(AlarmRingingService.ALARM_ID, alarmId);
        mContext.startActivity(ringingIntent);
    }
}
