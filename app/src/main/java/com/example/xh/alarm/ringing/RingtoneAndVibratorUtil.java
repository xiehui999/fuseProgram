package com.example.xh.alarm.ringing;

import android.content.Context;
import android.os.Build;
import android.os.Vibrator;

/**
 * Created by xiehui on 2017/3/28.
 */
public class RingtoneAndVibratorUtil {
    private boolean mVibrating;
    private Vibrator mVibrator;
    private Context mContext;

    public RingtoneAndVibratorUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void initialize() {
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public void virbrator(){
        long[] pattern= {0, 200, 500};
        if (!mVibrating){
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){

            }else{
               // mVibrator.vibrate(pattern,);
            }
        }
    }
}
