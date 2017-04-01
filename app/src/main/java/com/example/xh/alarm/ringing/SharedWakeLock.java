package com.example.xh.alarm.ringing;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class SharedWakeLock {
    private static final String TAG = "SharedWakeLock";
    private static SharedWakeLock sWakeLock;

    private PowerManager.WakeLock mFullWakeLock;

    @SuppressWarnings("deprecation")
    private SharedWakeLock(Context context) {
        Context appContext = context.getApplicationContext();
        PowerManager pm = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
        /**
         *  FULL_WAKE_LOCK ：保持CPU 运转，保持屏幕高亮显示，键盘灯也保持亮度
         *  SCREEN_BRIGHT_WAKE_LOCK ：保持CPU 运转，保持屏幕高亮显示，关闭键盘灯
         *  ACQUIRE_CAUSES_WAKEUP: 一旦有请求锁时，强制打开Screen和keyboard light
         *  ON_AFTER_RELEASE: 在释放锁时reset activity timer
         *如果申请了partial wakelock,那么即使按Power键,系统也不会进Sleep,如Music播放时
         *
         */
        mFullWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK |
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE), TAG);
    }

    // Not threadsafe
    public static SharedWakeLock get(Context context) {
        if (sWakeLock == null) {
            sWakeLock = new SharedWakeLock(context);
            Log.d(TAG, "Initialized shared WAKE_LOCKs!");
        }
        return sWakeLock;
    }

    public void acquireFullWakeLock() {
        if (!mFullWakeLock.isHeld()) {
            mFullWakeLock.acquire();
            Log.d(TAG, "Acquired Full WAKE_LOCK!");
        }
    }

    public void releaseFullWakeLock() {
        if (mFullWakeLock.isHeld()) {
            mFullWakeLock.release();
            Log.d(TAG, "Released Full WAKE_LOCK!");
        }
    }
}
