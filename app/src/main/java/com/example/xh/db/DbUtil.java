package com.example.xh.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.xh.alarm.model.Alarm;
import com.example.xh.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiehui on 2017/3/25.
 */
public class DbUtil {

    public static SQLiteDatabase getDataBase(){
    return new DbOpenHelper(MyApplication.getAppContext()).getWritableDatabase();
}
    /**
     * 添加Alarm
     * @param alarm
     */
    public static void addAlarm(Alarm alarm) {
        ContentValues contentValues = alarmContentValues(alarm);
        SQLiteDatabase db=getDataBase();
        try {
            db.insert("T_ALARM", null, contentValues);
        }catch (Exception e){
            e.printStackTrace();
        }

        db.close();
    }

    /**
     * 更新Alarm
     * @param alarm
     */
    public static void updateAlarm(Alarm alarm) {
        ContentValues values = alarmContentValues(alarm);
        SQLiteDatabase db=getDataBase();
        db.update("T_ALARM", values,
                "id" + " = ?",
                new String[]{alarm.getId() + ""});
        db.close();
    }

    /**
     * 根据id查询Alarm
     * @param id
     * @return
     */
    public static Alarm getAlarm(int id) {
        SQLiteDatabase db=getDataBase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM T_ALARM WHERE id=?", new String[]{id + ""});
        while (cursor.moveToNext()) {
            return getAlarmFromCursor(cursor);
        }
        return null;
    }

    /**
     * 更加游标解析 Alarm
     * @param cursor
     * @return
     */
    public static Alarm getAlarmFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        boolean isEnabled = (cursor.getInt(cursor.getColumnIndex("enabled")) != 0);
        int timeHour = cursor.getInt(cursor.getColumnIndex("hour"));
        int timeMinute = cursor.getInt(cursor.getColumnIndex("minute"));
        int signRemindMinutes = cursor.getInt(cursor.getColumnIndex("sign_remind"));
        String alarmToneString = cursor.getString(cursor.getColumnIndex("tone"));
        Uri alarmTone = null;
        if (!alarmToneString.isEmpty()) {
            alarmTone = Uri.parse(alarmToneString);
        }
        String[] repeatingDays = cursor.getString(cursor.getColumnIndex("days")).split(",");
        boolean vibrate = (cursor.getInt(cursor.getColumnIndex("vibrate")) != 0);
        Alarm alarm = new Alarm(id);
        alarm.setTitle(title);
        alarm.setIsEnabled(isEnabled);
        alarm.setTimeHour(timeHour);
        alarm.setTimeMinute(timeMinute);
        alarm.setmSignRemindMinutes(signRemindMinutes);
        alarm.setAlarmTone(alarmTone);
        for (int i = 0; i < repeatingDays.length; i++) {
            alarm.setRepeatingDay(i, !repeatingDays[i].equals("false"));
        }
        alarm.setVibrate(vibrate);
        return alarm;
    }

    /**
     * 从数据库获取所有闹钟
     * @return
     */
    public static List<Alarm> getAlarms() {
        List<Alarm> alarms = new ArrayList<>();
        SQLiteDatabase db=getDataBase();
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT * FROM T_ALARM", null);
            while (cursor.moveToNext()) {
                alarms.add(getAlarmFromCursor(cursor));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alarms;
    }

    private static ContentValues alarmContentValues(Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put("id", alarm.getId());
        values.put("title", alarm.getTitle());
        values.put("enabled", alarm.isEnabled() ? 1 : 0);
        values.put("hour", alarm.getTimeHour());
        values.put("minute", alarm.getTimeMinute());
        values.put("sign_remind", alarm.getmSignRemindMinutes());
        values.put("tone", alarm.getAlarmTone() != null ? alarm.getAlarmTone().toString() : "");

        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
            repeatingDays += alarm.getRepeatingDay(i) + ",";
        }
        values.put("days", repeatingDays);
        values.put("vibrate", alarm.shouldVibrate());
        return values;
    }
}
