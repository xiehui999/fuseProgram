/*
 *
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license.
 *
 * Project Oxford: http://ProjectOxford.ai
 *
 * Project Oxford Mimicker Alarm Github:
 * https://github.com/Microsoft/ProjectOxford-Apps-MimickerAlarm
 *
 * Copyright (c) Microsoft Corporation
 * All rights reserved.
 *
 * MIT License:
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.example.xh.alarm.ringing;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Vibrator;

/**
 * 闹铃震动管理类
 */
public class AlarmVibrator {
    private boolean mVibrating;
    private Vibrator mVibrator;
    private Context mContext;

    public AlarmVibrator(Context context) {
        mContext = context;
    }

    public void initialize() {
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void cleanup() {
        mVibrator = null;
    }

    public void vibrate() {
        if (!mVibrating) {
            long[] vibrationPattern = {0, 200, 500};//等待时间，震动时间，等待时间....如此循环
            //repeat 震动次数 0一直持续 -1 只会出现一轮
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                mVibrator.vibrate(vibrationPattern, 0,
                        //AudioAttributes 是一个封装音频各种属性的类
                        new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build());
            } else {
                mVibrator.vibrate(vibrationPattern, 0);
            }
            mVibrating = true;
        }
    }

    public void stop() {
        if (mVibrating) {
            mVibrator.cancel();
            mVibrating = false;
        }
    }
}
