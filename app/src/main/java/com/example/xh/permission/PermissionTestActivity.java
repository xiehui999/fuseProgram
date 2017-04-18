package com.example.xh.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.request)
    public void requestDeviceId() {
        if (PermissionUtils.isHasPermissions(permissions)) {
            executeReadDeviceId();
        } else {
            PermissionUtils.requestPermissions(this, requestCode, permissions);
        }

    }

    private void executeReadDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        tv_deviceId.setText("DeviceId:" + deviceId);
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
