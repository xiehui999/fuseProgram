package com.example.xh.alarm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.model.AlarmUtilities;
import com.example.xh.alarm.setting.RepeatingDaysPreference;
import com.example.xh.alarm.setting.RingtonePreference;
import com.example.xh.alarm.setting.VibratePreference;
import com.example.xh.db.DbUtil;

import java.util.Calendar;

/**
 * Created by xiehui on 2017/2/12.
 */
public class AlarmSettingFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String SETTINGS_FRAGMENT_TAG = "setting_fragment";
    private static final String ARGS_ALARM_ID = "alarm_id";

    AlarmSettingListener mCallback;
    private Alarm mAlarm;
    private RepeatingDaysPreference mRepeatingDaysPreference;
    private RingtonePreference mRingtonePreference;
    private VibratePreference mVibratePreference;
    private ListPreference mSignRemindMinutes;
    private int remindMinutes=0;
    private boolean hasChangeSignRemind=false;
    private int mSignVaule;
    private TextView time;
    private TextView appbar_title;
    private LinearLayout appbar_left_layout;

    public static AlarmSettingFragment newInstance(int alarmId) {
        AlarmSettingFragment fragment = new AlarmSettingFragment();
        Bundle bundle = new Bundle(1);
        bundle.putInt(ARGS_ALARM_ID, alarmId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (AlarmSettingListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, final String s) {
        addPreferencesFromResource(R.xml.pref_alarm);

        Bundle args = getArguments();
        int alarmId = args.getInt(ARGS_ALARM_ID);
        mAlarm = DbUtil.getAlarm(alarmId);
        init();
    }

    private void init() {
        mVibratePreference = (VibratePreference) findPreference(getString(R.string.pref_vibrate_key));
        mVibratePreference.setInitialValue(mAlarm.shouldVibrate());
        mRingtonePreference = (RingtonePreference) findPreference(getString(R.string.pref_ringtone_key));
        mRingtonePreference.setRingtone(mAlarm.getAlarmTone());
        mRingtonePreference.setParent(this);
        mRepeatingDaysPreference = (RepeatingDaysPreference) findPreference(getString(R.string.pref_repeating_days_key));
        for (int i = 0; i < 7; ++i) {
            if (mAlarm.getRepeatingDay(i)) {
                mRepeatingDaysPreference.setRepeatingDay(i, true);
            }
        }
        remindMinutes=mAlarm.getmSignRemindMinutes();
        mSignRemindMinutes=(ListPreference)findPreference(getString(R.string.pref_sign_remind_key));
        if (mAlarm.getId()==1){
            mSignRemindMinutes.setTitle("签到提醒");
            mSignRemindMinutes.setSummary(remindMinutes+" 分钟前提醒");
        }else{
            mSignRemindMinutes.setTitle("签退提醒");
            mSignRemindMinutes.setSummary(remindMinutes+" 分钟后提醒");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }



    @Override
    public RecyclerView onCreateRecyclerView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        LinearLayout rootLayout = (LinearLayout) parent.getParent();
        LinearLayout appBarLayout= (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.alarm_setting_head, rootLayout, false);
        rootLayout.addView(appBarLayout, 0);
        appbar_title = (TextView) appBarLayout.findViewById(R.id.appbar_title);
        time = (TextView) appBarLayout.findViewById(R.id.time);
        appbar_title.setText(mAlarm.getTitle());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, mAlarm.getTimeHour());
        calendar.set(Calendar.MINUTE, mAlarm.getTimeMinute());
        calendar.add(Calendar.MINUTE, mAlarm.getId() == 1 ? mAlarm.getmSignRemindMinutes() : -mAlarm.getmSignRemindMinutes());
        String workTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + (calendar.get(Calendar.MINUTE) == 0 ? calendar.get(Calendar.MINUTE) + "0" : calendar.get(Calendar.MINUTE));
        if (mAlarm.getId()==1){
            time.setText("上班时间："+workTime);
        }else{
            time.setText("下班时间："+workTime);
        }
        appbar_left_layout = (LinearLayout) appBarLayout.findViewById(R.id.appbar_left_layout);
        appbar_left_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
        return super.onCreateRecyclerView(inflater, parent, savedInstanceState);
    }


    public void onCancel() {
        if (haveSettingsChanged()) {
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.pref_dialog_save_changes_message)
                    .setPositiveButton(R.string.pref_dialog_save_changes_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveSettingsAndExit();
                        }
                    })
                    .setNegativeButton(R.string.pref_dialog_save_changes_negative_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            discardSettingsAndExit();
                        }
                    })
                    .show();
        } else {
            discardSettingsAndExit();
        }
    }

    private void saveSettingsAndExit() {

        populateUpdatedSettings();

        long alarmTime = mAlarm.schedule();

        Toast.makeText(getActivity(),
                AlarmUtilities.getTimeUntilAlarmDisplayString(getActivity(), alarmTime),
                Toast.LENGTH_LONG)
                .show();

        mCallback.onSettingSave();
    }
    private void discardSettingsAndExit() {
        mCallback.onSettingCancel();
    }

    private boolean haveSettingsChanged() {
        return  mRepeatingDaysPreference.hasChanged() ||
                mRingtonePreference.hasChanged() ||
                mVibratePreference.hasChanged()||
                hasChangeSignRemind;
    }

    private void populateUpdatedSettings() {
        updateRepeatingDaysSetting();
        updateRingtoneSetting();
        updateVibrateSetting();
        updateSignSetting();
    }
    private void updateSignSetting() {
        if (hasChangeSignRemind) {
            mAlarm.setmSignRemindMinutes(mSignVaule);
        }
    }
    private void updateVibrateSetting() {
        if (mVibratePreference.hasChanged()) {
            mAlarm.setVibrate(mVibratePreference.isChecked());
        }
    }

    private void updateRingtoneSetting() {
        if (mRingtonePreference.hasChanged()) {
            mAlarm.setAlarmTone(mRingtonePreference.getRingtone());
        }
    }

    private void updateRepeatingDaysSetting() {
        if (mRepeatingDaysPreference.hasChanged()) {
            boolean[] repeatingDays = mRepeatingDaysPreference.getRepeatingDays();
            for (int i = 0; i < repeatingDays.length; i++) {
                mAlarm.setRepeatingDay(i, repeatingDays[i]);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RingtonePreference.RINGTONE_PICKER_REQUEST) {
                mRingtonePreference.handleRingtonePickerResult(data);
            }
        }
    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sign_remind_key))) {
            try {
                mSignVaule= Integer.parseInt(mSignRemindMinutes.getValue());
            }catch (Exception e){
                e.printStackTrace();
                mSignVaule=10;
            }

            hasChangeSignRemind=mSignVaule!=remindMinutes;
            if (mAlarm.getId()==1){
                mSignRemindMinutes.setSummary(mSignVaule+" 分钟前提醒");
            }else{
                mSignRemindMinutes.setSummary(mSignVaule+" 分钟后提醒");
            }
        }
    }

    public interface AlarmSettingListener {
        void onSettingSave();
        void onSettingCancel();
    }
}

