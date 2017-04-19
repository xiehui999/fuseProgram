package com.example.xh.permission;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.xh.utils.MyApplication;

import java.util.ArrayList;

/**
 * Created by xiehui on 2017/3/29.
 */
public class PermissionUtils {

    public PermissionUtils(Activity activity) {
    }

    /**
     * 测试用了。
     *
     * @return true 拥有权限 false 没有该权限
     */
    private static boolean checkPermission(Activity activity, String permission, String remind, int requestCode) {
        if (!isHasPermissions(permission)) {

            /**
             * 华为手机测试 第一次使用返回false
             * 拒绝后返回true
             * 拒绝并点击不在提醒返回false
             * 已经同意过权限，并在设置中拒绝此时返回true
             * 没有同意过权限，在设置中开启并拒绝权限返回false（如果在设置先同意，返回应用在去设置拒绝返回true）
             */
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                Toast.makeText(activity, "true", Toast.LENGTH_LONG).show();
                System.out.println("shouldShowRequestPermissionRationale:true");
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);

            } else {
                Toast.makeText(activity, "false", Toast.LENGTH_LONG).show();
                System.out.println("shouldShowRequestPermissionRationale:false");
                ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
            }
        }
        return false;
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Object object, String... permissions) {
        for (String permission : permissions) {
            if (object instanceof Activity) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, permission)) {
                    return true;
                }
            } else if (object instanceof Fragment) {
                if(((Fragment) object).shouldShowRequestPermissionRationale(permission)){
                    return true;
                }
            } else {
                throw new RuntimeException("the object must be Activity or Fragment");
            }


        }
        return false;
    }

    /**
     * 二次申请权限时，弹出自定义提示对话框
     *
     * @param activity
     * @param message
     * @param iPermissionRequest
     * @see com.example.xh.ui.BaiduLocationFragment 可以查看该类onRequestPermissionsResult方法当选择永不提醒时的处理办法。
     */
    public static void showDialog(Activity activity, String message, final IPermissionRequest iPermissionRequest) {
        new AlertDialog.Builder(activity)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        iPermissionRequest.agree();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        iPermissionRequest.refuse();
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }

    /**
     * 请求权限,经测试发现TabActivity管理Activity时，在Activity中请求权限时需要传入父Activity对象，即TabActivity对象
     * 并在TabActivity管理Activity中重写onRequestPermissionsResult并分发到子Activity
     *
     * @param object      Activity or Fragment
     * @param requestCode 请求码
     * @param permissions 请求权限
     */
    public static void requestPermissions(Object object, int requestCode, String... permissions) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String permission : permissions) {
            if (!isHasPermissions(permission)) {
                arrayList.add(permission);
            }
        }
        if (arrayList.size() > 0) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
                Activity activity1 = activity.getParent() != null && activity.getParent() instanceof TabActivity ? activity.getParent() : activity;
                ActivityCompat.requestPermissions(activity1, arrayList.toArray(new String[]{}), requestCode);
            } else if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                Fragment fragment1 = fragment.getParentFragment() != null ? fragment.getParentFragment() : fragment;
                fragment1.requestPermissions(arrayList.toArray(new String[]{}), requestCode);
            } else {
                throw new RuntimeException("the object must be Activity or Fragment");
            }
        }
    }

    /**
     * 判断是否具备所有权限
     *
     * @param permissions 所有权限
     * @return true 具有所有权限  false没有具有所有权限，此时包含未授予的权限
     */
    public static boolean isHasPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        for (String permission : permissions) {
            if (!isHasPermission(permission))
                return false;
        }
        return true;
    }

    /**
     * 判断该权限是否已经被授予
     *
     * @param permission
     * @return true 已经授予该权限 ，false未授予该权限
     */
    private static boolean isHasPermission(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        return ContextCompat.checkSelfPermission(MyApplication.getAppContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }


}
