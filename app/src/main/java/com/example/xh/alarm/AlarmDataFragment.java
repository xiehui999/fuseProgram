package com.example.xh.alarm;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.xh.R;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.model.AlarmUtilities;
import com.example.xh.db.DbUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by xiehui on 2017/2/12.
 */
public class AlarmDataFragment extends Fragment implements View.OnClickListener {
    public static final String ALARM_DATA_FRAGMENT_TAG = "alarm_data_fragment";

    private TextView mSignTitleTextView;
    private TextView mSignTimeTextView;
    private SwitchCompat mSignEnabled;
    private LinearLayout mSignLinear;
    private TextView mSignOutTitleTextView;
    private TextView mSignOutTimeTextView;
    private SwitchCompat mSignOutEnabled;
    private LinearLayout mSignOutLinear;

    private TextView appbar_title;
    private LinearLayout appbar_left_layout;
    private Alarm signAlarm;
    private Alarm signOutAlarm;
    private AlarmDataListener mCallbacks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (AlarmDataListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.alarm_main_fragment, container, false);
        mSignTitleTextView = (TextView) view.findViewById(R.id.tv_alarm_sign_title);
        mSignTimeTextView = (TextView) view.findViewById(R.id.tv_alarm_sign_time);
        mSignOutTimeTextView = (TextView) view.findViewById(R.id.tv_alarm_signout_time);
        mSignOutTitleTextView = (TextView) view.findViewById(R.id.tv_alarm_signout_title);
        mSignEnabled = (SwitchCompat) view.findViewById(R.id.alarm_sign_enabled_switch);
        mSignOutEnabled = (SwitchCompat) view.findViewById(R.id.alarm_signout_enabled_switch);
        mSignLinear = (LinearLayout) view.findViewById(R.id.sign_linear);
        mSignOutLinear = (LinearLayout) view.findViewById(R.id.signout_linear);
        appbar_title = (TextView) view.findViewById(R.id.appbar_title);
        appbar_title.setText("考勤闹钟");
        appbar_left_layout = (LinearLayout) view.findViewById(R.id.appbar_left_layout);
        appbar_left_layout.setOnClickListener(this);
        mSignEnabled.setOnClickListener(this);
        mSignOutEnabled.setOnClickListener(this);
        mSignLinear.setOnClickListener(this);
        mSignOutLinear.setOnClickListener(this);
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void updateUI() {
        signAlarm = DbUtil.getAlarm(1);
        signOutAlarm = DbUtil.getAlarm(2);
        if (signAlarm != null) {
            String title = getTitle(signAlarm);
            if (title == null || title.isEmpty()) {
                mSignTitleTextView.setVisibility(View.GONE);
            } else {
                mSignTitleTextView.setVisibility(View.VISIBLE);
                mSignTitleTextView.setText(title);
            }
            mSignTimeTextView.setText(AlarmUtilities.getUserTimeString(getContext(), signAlarm.getTimeHour(), signAlarm.getTimeMinute()));
            mSignEnabled.setChecked(signAlarm.isEnabled());

        } else {
            mSignEnabled.setChecked(false);
        }
        if (signOutAlarm != null) {
            String title = getTitle(signOutAlarm);
            if (title == null || title.isEmpty()) {
                mSignOutTitleTextView.setVisibility(View.GONE);
            } else {
                mSignOutTitleTextView.setVisibility(View.VISIBLE);
                mSignOutTitleTextView.setText(title);
            }
            mSignOutTimeTextView.setText(AlarmUtilities.getUserTimeString(getContext(), signOutAlarm.getTimeHour(), signOutAlarm.getTimeMinute()));
            mSignOutEnabled.setChecked(signOutAlarm.isEnabled());
        } else {
            mSignOutEnabled.setChecked(false);
        }

    }

    private String getTitle(Alarm alarm) {
        String alarmTitle = alarm.getTitle();
        if (alarm.isOneShot()) {
            return alarmTitle;
        } else {
            String summary = getRepeatingDaySummary(alarm);
            if (alarmTitle == null || alarmTitle.isEmpty()) {
                return summary;
            } else {
                return alarmTitle + ", " + summary;
            }
        }
    }

    private String getRepeatingDaySummary(Alarm alarm) {
        List<Integer> days = new ArrayList<>();
        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; dayOfWeek++) {
            if (alarm.getRepeatingDay(dayOfWeek - 1)) {
                days.add(dayOfWeek);
            }
        }
        int[] daysOfWeek = new int[days.size()];
        for (int i = 0; i < days.size(); i++) {
            daysOfWeek[i] = days.get(i);
        }
        return AlarmUtilities.getRepeatingDayString(daysOfWeek);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appbar_left_layout:
                getActivity().onBackPressed();
                break;
            case R.id.sign_linear:
                mCallbacks.onAlarmSelected(signAlarm);
                break;
            case R.id.signout_linear:
                mCallbacks.onAlarmSelected(signOutAlarm);
                break;
            case R.id.alarm_sign_enabled_switch:
                if (mSignEnabled.isChecked()) {
                    long alarmTime = signAlarm.schedule();
                    Toast.makeText(getActivity(),
                            AlarmUtilities.getTimeUntilAlarmDisplayString(getActivity(), alarmTime),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    signAlarm.cancel();
                }
                mCallbacks.onAlarmChanged();
                break;
            case R.id.alarm_signout_enabled_switch:
                if (mSignOutEnabled.isChecked()) {
                    long alarmTime = signOutAlarm.schedule();
                    Toast.makeText(getActivity(),
                            AlarmUtilities.getTimeUntilAlarmDisplayString(getActivity(), alarmTime),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    signOutAlarm.cancel();
                }
                mCallbacks.onAlarmChanged();
                break;
        }
    }

    public interface AlarmDataListener {
        void onAlarmSelected(Alarm alarm);

        void onAlarmChanged();
    }
}
