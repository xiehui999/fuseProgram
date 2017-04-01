package com.example.xh.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.baidu.mobstat.StatService;
import com.example.xh.R;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.model.AlarmUtilities;
import com.example.xh.alarm.ringing.AlarmNotificationManager;

/**
 * Created by xiehui on 2017/2/12.
 */
public class AlarmMainActivity extends AppCompatActivity implements AlarmDataFragment.AlarmDataListener,AlarmSettingFragment.AlarmSettingListener{
    private SharedPreferences mPreferences = null;
    private AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_mainactivity);
        String packageName = getApplicationContext().getPackageName();
        mPreferences = getSharedPreferences(packageName, MODE_PRIVATE);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPause(this);
        if (!AlarmUtilities.areEditingSettings(getSupportFragmentManager())) {
            AlarmUtilities.showFragment(getSupportFragmentManager(),
                    new AlarmDataFragment(),
                    AlarmDataFragment.ALARM_DATA_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (AlarmUtilities.areEditingSettings(getSupportFragmentManager())) {
            AlarmUtilities.getAlarmSettingFragment(getSupportFragmentManager()).onCancel();
        } else {
            super.onBackPressed();
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

    @Override
    public void onAlarmSelected(Alarm alarm) {
        AlarmUtilities.transitionFromAlarmDataToSettings(getSupportFragmentManager(), alarm.getId());
    }

    @Override
    public void onAlarmChanged() {
        AlarmNotificationManager.get(this).handleNextAlarmNotificationStatus();
    }

    @Override
    public void onSettingSave() {
        AlarmUtilities.showFragmentFromLeft(getSupportFragmentManager(),
                new AlarmDataFragment(),
                AlarmDataFragment.ALARM_DATA_FRAGMENT_TAG);
        onAlarmChanged();
    }

    @Override
    public void onSettingCancel() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_left,android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_container, new AlarmDataFragment());
        transaction.commit();
    }
}
