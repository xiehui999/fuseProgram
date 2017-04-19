package com.example.xh.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.example.xh.R;
import com.example.xh.alarm.AlarmMainActivity;
import com.example.xh.alarm.model.Alarm;
import com.example.xh.alarm.ringing.AlarmNotificationManager;
import com.example.xh.alarm.ringing.AlarmScheduler;
import com.example.xh.db.DbUtil;
import com.example.xh.permission.IPermissionRequest;
import com.example.xh.permission.PermissionUtils;
import com.example.xh.utils.LocationService;

/**
 * Created by xiehui on 2016/10/18.
 */

public class BaiduLocationFragment extends Fragment implements View.OnClickListener {

    private TextView tv_location;

    private Button btn_getLocation, btn_batteryOption, btn_alarm;
    private Context context;
    private LocationService locationService;
    private String[] permissions=new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
   private int requestCodeLocation=0x00010;
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_getLocation.setOnClickListener(this);
        btn_batteryOption.setOnClickListener(this);
        btn_alarm.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationService = LocationService.getInstance(getContext());
        locationService.registerListener(mListener);
        openAlarmRing();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.locationfragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        btn_getLocation = (Button) view.findViewById(R.id.btn_getLocation);
        btn_batteryOption = (Button) view.findViewById(R.id.btn_batteryOption);
        btn_alarm = (Button) view.findViewById(R.id.btn_alarm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationService.unRegisterListener(mListener);
        locationService.stop();
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btn_getLocation:
                if(PermissionUtils.isHasPermissions(permissions)){
                    startLocation();
                }else{
                    PermissionUtils.requestPermissions(this,requestCodeLocation,permissions);
                }

                break;
            case R.id.btn_batteryOption:
                isIgnoreBatteryOption();
                break;
            case R.id.btn_alarm:
                startActivity(new Intent(getActivity(), AlarmMainActivity.class));
                break;

        }
    }

    public void startLocation(){
        String text = btn_getLocation.getText().toString();
        tv_location.setText("");
        if (text.equals("开始定位")) {
            btn_getLocation.setText("停止定位");
            locationService.registerListener(mListener);
            locationService.start();
        } else {
            btn_getLocation.setText("开始定位");
            locationService.stop();
            locationService.unRegisterListener(mListener);
        }
    }
    public void openAlarmRing() {
        DbUtil.addAlarm(new Alarm(1));
        DbUtil.addAlarm(new Alarm(2));
        AlarmNotificationManager.get(getActivity()).handleNextAlarmNotificationStatus();
        AlarmScheduler.scheduleAlarms(getActivity());
    }

    public void isIgnoreBatteryOption() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent();
                String packageName = getActivity().getPackageName();
                PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    getActivity().startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(getActivity(), "测试测试", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==requestCodeLocation){
            if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                startLocation();
            }else{
                if (!PermissionUtils.shouldShowRequestPermissionRationale(this,this.permissions)){//选择了永不提醒时回调
                    PermissionUtils.showDialog(getActivity(),"使用此功能需要定位权限！请前往设置",new IPermissionRequest(){
                        @Override
                        public void agree() {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                        @Override
                        public void refuse() {
                            Toast.makeText(getActivity(), "不允许", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getContext(),"请求定位权限失败",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /*****
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                //sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                //sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    //sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                tv_location.setText(sb + "\n定位结束");
                locationService.stop();
            } else {
                tv_location.setText("\n定位失败");
            }
        }

    };
}
