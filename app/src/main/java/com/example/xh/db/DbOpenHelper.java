package com.example.xh.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xiehui on 2017/3/25.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    private static final String T_ALARM = "CREATE TABLE IF NOT EXISTS T_ALARM( id INTEGER PRIMARY KEY , title,enabled, hour, minute, sign_remind, days, tone, vibrate, new)";
    public DbOpenHelper(Context context) {
        super(context, "test.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(T_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS T_ALARM");
        onCreate(db);
    }
}
