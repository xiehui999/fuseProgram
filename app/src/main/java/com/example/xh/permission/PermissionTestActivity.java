package com.example.xh.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.activity.AnimationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xiehui on 2017/4/18.
 */
public class PermissionTestActivity extends AppCompatActivity {
    @BindView(R.id.tv_deviceId)
    TextView tv_deviceId;
    private static final String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE};
    private static final int requestCode = 0x0001;
    private static final int requestCodeWriteSetting = 0x0002;
    private static final int requestCodeAlertWindow = 0x0003;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);
    }

    /**
     * 测试请求读取手机卡权限
     */
    @OnClick(R.id.request)
    public void requestDeviceId() {
        if (PermissionUtils.isHasPermissions(permissions)) {
            executeReadDeviceId();
        } else {
            PermissionUtils.requestPermissions(this, requestCode, permissions);
        }

    }

    /**
     * 测试请求WRITE_SETTINGS权限
     */
    @OnClick(R.id.request_write_setting)
    @TargetApi(android.os.Build.VERSION_CODES.N)
    public void requestWriteSetting() {
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, requestCodeWriteSetting);
        } else {
            Toast.makeText(PermissionTestActivity.this, "WRITE_SETTINGS 已经被授权", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 测试请求SYSTEM_ALERT_WINDOW权限
     */
    @OnClick(R.id.request_alert_window)
    @TargetApi(android.os.Build.VERSION_CODES.N)
    public void requestAlertWindow() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, requestCodeAlertWindow);
        } else {
            Toast.makeText(PermissionTestActivity.this, "SYSTEM_ALERT_WINDOW 已经被授权", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开应用权限设置界面
     */
    @OnClick(R.id.open_permission_setting)
    public void requestOpenPermissionSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        // Uri uri = Uri.fromParts("package", getPackageName(), null);
        Uri uri1=Uri.parse("package:" + getPackageName());
        intent.setData(uri1);
        startActivity(intent);
    }
    @OnClick(R.id.open_animation)
    public void startAnimationPage() {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }
    private void executeReadDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        tv_deviceId.setText("DeviceId:" + deviceId);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCodeWriteSetting) {
            showToast();
        } else if (requestCode == requestCodeAlertWindow) {
            showToastAlerterWindow();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showToast() {
        if (Settings.System.canWrite(this)) {
            //检查返回结果
            Toast.makeText(PermissionTestActivity.this, "WRITE_SETTINGS 被授权", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PermissionTestActivity.this, "WRITE_SETTINGS 没有被授权", Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showToastAlerterWindow() {
        if (Settings.System.canWrite(this)) {
            //检查返回结果
            Toast.makeText(PermissionTestActivity.this, "SYSTEM_ALERT_WINDOW 被授权", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PermissionTestActivity.this, "SYSTEM_ALERT_WINDOW 没有被授权", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(PermissionTestActivity.this, "请求权限失败！", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            executeReadDeviceId();
        }

    }
}
