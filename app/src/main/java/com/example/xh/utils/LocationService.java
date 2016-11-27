package com.example.xh.utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by xiehui on 2016/11/25.
 */
public class LocationService {
    private static LocationClient locationClient = null;
    private LocationClientOption locationClientOption;
    private static LocationService locationService;

    /**
     * 获取LocationService实例
     *
     * @param context
     * @return
     */
    public static LocationService getInstance(Context context) {
        if (locationClient == null) {
            synchronized (LocationService.class) {
                locationService= new LocationService(context);
            }
        }
        return locationService;
    }

    private LocationService(Context context) {
        if (locationClient == null) {
            locationClient = new LocationClient(context);
            locationClient.setLocOption(getDefaultLocationClientOption());
        }
    }

    /***
     * 配置参数
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (locationClientOption == null) {
            locationClientOption = new LocationClientOption();
            locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            locationClientOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            locationClientOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            locationClientOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            locationClientOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            locationClientOption.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
            locationClientOption.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            locationClientOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            locationClientOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            locationClientOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            locationClientOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集

            locationClientOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return locationClientOption;
    }

    /**
     * 注册
     *
     * @param listener
     * @return
     */
    public boolean registerListener(BDLocationListener listener) {
        if (listener != null) {
            locationClient.registerLocationListener(listener);
            return true;
        }
        return false;
    }

    /**
     * 取消注册
     *
     * @param listener
     * @return
     */
    public boolean unRegisterListener(BDLocationListener listener) {
        if (listener != null) {
            locationClient.unRegisterLocationListener(listener);
            return true;
        }
        return false;
    }

    /**
     * 开启定位
     */
    public void start() {
        if (locationClient != null && !locationClient.isStarted()) {
            locationClient.start();
        }else if (locationClient != null &&locationClient.isStarted()){
            locationClient.requestLocation();
        }
    }

    /**
     * 关闭定位
     */
    public void stop() {
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
        }
    }
}
