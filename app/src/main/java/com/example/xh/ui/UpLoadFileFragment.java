package com.example.xh.ui;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.permission.PermissionUtils;
import com.example.xh.uploadfile.ChunkInfo;
import com.example.xh.uploadfile.FileInfo;
import com.example.xh.uploadfile.Md5Utils;
import com.example.xh.uploadfile.SelectFileActivity;
import com.example.xh.uploadfile.UploadService;

import java.io.File;

/**
 * Created by xiehui on 2016/10/18.
 */

public class UpLoadFileFragment extends Fragment implements View.OnClickListener {
    private TextView tv_fileName;

    private TextView tv_filemd5;
    private TextView tv_progress;
    private Button btn_chooseFile;

    private Button btn_upLoadFile;

    private Button btn_getmd5;
    private Context context;
    private String TAG = "Fragment";
    private static final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int requestCode = 0x0001;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
        btn_chooseFile.setOnClickListener(this);
        btn_upLoadFile.setOnClickListener(this);
        btn_getmd5.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.upoadfilefragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
        tv_fileName = (TextView) view.findViewById(R.id.tv_fileName);
        tv_filemd5 = (TextView) view.findViewById(R.id.tv_filemd5);
        tv_progress = (TextView) view.findViewById(R.id.tv_progress);
        btn_chooseFile = (Button) view.findViewById(R.id.btn_chooseFile);
        btn_getmd5 = (Button) view.findViewById(R.id.btn_getmd5);
        btn_upLoadFile = (Button) view.findViewById(R.id.btn_upLoadFile);
        // 注册广播接收器，接收下载进度信息和结束信息
        IntentFilter filter = new IntentFilter();
        filter.addAction("ACTION_UPDATE");
        filter.addAction("ACTION_FINISH");
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        getActivity().unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ACTION_UPDATE".equals(intent.getAction())) {
                ChunkInfo chunkInfo = (ChunkInfo) intent.getSerializableExtra("chunkIntent");
                tv_progress.setText(chunkInfo.getChunk() + 1 + "/" + chunkInfo.getChunks() + "    " + chunkInfo.getProgress() + "KB");
                //Log.e("chunkInfo:", chunkInfo.toString());
            } else if ("ACTION_FINISH".equals(intent.getAction())) {

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String str = bundle.getString("current_path");
            String testStr = bundle.getString("testStr");
            Log.d("", "获取的 文件路径： " + str);
            Log.d("", "testStr : " + testStr);
            tv_fileName.setText(str);
        } else {
            Toast.makeText(context, "没有选择文件", Toast.LENGTH_LONG).show();
        }

    }

    public void choseFile() {
        Intent intent = new Intent();
        intent.setClass(getContext(), SelectFileActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View v) {
        String path = tv_fileName.getText().toString();
        File file = new File(path);
        switch (v.getId()) {
            case R.id.btn_chooseFile:
                Fragment fragment=getParentFragment();
                if (PermissionUtils.isHasPermissions(permissions)) {
                    choseFile();
                } else {
                    PermissionUtils.requestPermissions(this, requestCode, permissions);
                }
                break;
            case R.id.btn_upLoadFile:
                if (path.equals("")) {
                    Toast.makeText(getActivity(), "请选择文件", Toast.LENGTH_LONG).show();
                } else if (!file.exists()) {
                    Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_LONG).show();
                } else {
                    Intent upIntent = new Intent(getContext(), UploadService.class);
                    upIntent.setAction("START_UPLOAD");

                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFilePath(path);
                    fileInfo.setMd5(tv_filemd5.getText().toString());
                    fileInfo.setGguid("7BF23FB9-6412-DEC1-B591-E1AD971F47F5");
                    fileInfo.setFileLength(file.length());
                    fileInfo.setIsChunk(true);
                    upIntent.putExtra("fileInfo", fileInfo);
                    getContext().startService(upIntent);
                }

                break;

            case R.id.btn_getmd5:
                if (path.equals("")) {
                    Toast.makeText(getContext(), "请选择文件", Toast.LENGTH_LONG).show();
                } else if (!file.exists()) {
                    Toast.makeText(getContext(), "文件不存在", Toast.LENGTH_LONG).show();
                } else {
                    long time1 = System.currentTimeMillis();
                    String md5 = Md5Utils.getFileMd5(file);
/*                    long time2=System.currentTimeMillis();
                    System.out.println("FileInputStream执行时间："+(time2-time1));
                    String md51= Md5Utils.getFileMd52(file);
                    long time3=System.currentTimeMillis();
                    System.out.println("FileChannel执行时间："+(time3-time2));
                    String md52 = Md5Utils.getFileMd53(file);
                    long time4=System.currentTimeMillis();
                    System.out.println("RandomAccessFile执行时间："+(time4-time3));*/
                    tv_filemd5.setText(md5);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "请求权限失败！", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            choseFile();
        }

    }
}
