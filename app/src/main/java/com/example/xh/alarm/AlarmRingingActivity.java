package com.example.xh.alarm;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.example.xh.R;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.ringing.AlarmRingingService;
import com.example.xh.alarm.ringing.AlarmScheduler;
import com.example.xh.alarm.ringing.SharedWakeLock;
import com.example.xh.db.DbUtil;

import java.util.Calendar;

public class AlarmRingingActivity extends AppCompatActivity {


    public final String TAG = this.getClass().getSimpleName();
    private Alarm mAlarm;
    private Handler mHandler;
    private AlarmRingingService mRingingService;
    private boolean mIsServiceBound;
    private AudioManager mAudioManager;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mRingingService = ((AlarmRingingService.LocalBinder) service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            mRingingService = null;
        }
    };

    private BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                notifyControllerSilenceAlarmRinging();

                // We release and reacquire the wakelock so that we can turn the screen back on
                SharedWakeLock.get(getApplicationContext()).releaseFullWakeLock();
                SharedWakeLock.get(getApplicationContext()).acquireFullWakeLock();
                notifyControllerStartAlarmRinging();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int alarmId = getIntent().getExtras().getInt(AlarmScheduler.ARGS_ALARM_ID);
        mAlarm = DbUtil.getAlarm(alarmId);
        setLockScreenFlags(getWindow());
        setContentView(R.layout.alarm_ringing);
        TextView titleField = (TextView) findViewById(R.id.alarm_ringing_title);
        TextView signTime = (TextView) findViewById(R.id.alarm_ringing_time);
        String signTimeStr = "";
        String workTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mAlarm.getTimeHour());
        calendar.set(Calendar.MINUTE, mAlarm.getTimeMinute());
        calendar.add(Calendar.MINUTE, alarmId == 1 ? mAlarm.getmSignRemindMinutes() : -mAlarm.getmSignRemindMinutes());
        workTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + (calendar.get(Calendar.MINUTE) == 0 ? calendar.get(Calendar.MINUTE) + "0" : calendar.get(Calendar.MINUTE));
        if (alarmId == 1) {
            signTimeStr = "上班时间： " + workTime;
        } else if (alarmId == 2) {
            signTimeStr = "下班时间： " + workTime;

        }
        signTime.setText(signTimeStr);
        titleField.setText(mAlarm.getTitle());
        ImageView dismissButton = (ImageView) findViewById(R.id.alarm_ringing_dismiss);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRingingDismiss();
            }
        });
        ImageView snoozeButton = (ImageView) findViewById(R.id.alarm_ringing_snooze);
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRingingSnooze();
            }
        });
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mHandler = new Handler();
        registerReceiver(mScreenReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

        bindRingingService();
    }
    /**
     * 闹钟响时打开屏幕
     * @param window
     */
    public static void setLockScreenFlags(Window window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);//打开屏幕
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);//显示
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);//解锁
    }

    /**
     * 点击关闭按钮处理逻辑
     */
    public void onRingingDismiss() {
        mAlarm.onDismiss();
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "请先点击右侧按钮关闭闹钟！", Toast.LENGTH_LONG).show();
    }

    /**
     * 点击休息一会处理逻辑
     */
    public void onRingingSnooze() {
        mAlarm.snooze();
        finishActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenReceiver);
        unbindRingingService();
    }

    private void finishActivity() {
        // We only want to report that ringing completed as a result of correct user action
        notifyControllerRingingCompleted();
        finish();
    }

    private void bindRingingService() {
        bindService(new Intent(AlarmRingingActivity.this,
                AlarmRingingService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        mIsServiceBound = true;
    }

    private void unbindRingingService() {
        if (mIsServiceBound) {
            // Detach our existing connection.
            unbindService(mServiceConnection);
            mIsServiceBound = false;
        }
    }

    private void notifyControllerRingingCompleted() {
        if (mRingingService != null) {
            mRingingService.reportAlarmCompleted();
        }
    }

    private void notifyControllerSilenceAlarmRinging() {
        if (mRingingService != null) {
            mRingingService.silenceAlarmRinging();
        }
    }

    private void notifyControllerStartAlarmRinging() {
        if (mRingingService != null) {
            mRingingService.startAlarmRinging();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //ADJUST_RAISE 升高音量
        //ADJUST_LOWER 降低音量

        //FLAG_SHOW_UI更改音量时展示音量UI
        //FLAG_PLAY_SOUND 调整音量时播放声音

        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
                    AudioManager.ADJUST_LOWER,
                    AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mAudioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,
                    AudioManager.ADJUST_RAISE,
                    AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
        } else {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }
}
