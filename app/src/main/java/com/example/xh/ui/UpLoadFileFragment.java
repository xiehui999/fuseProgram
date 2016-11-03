package com.example.xh.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.xh.uploadfile.FileInfo;
import com.example.xh.uploadfile.Md5Utils;
import com.example.xh.uploadfile.SelectFileActivity;
import com.example.xh.uploadfile.UploadService;

import java.io.File;

/**
 * Created by xiehui on 2016/10/18.
 */

public class UpLoadFileFragment extends Fragment implements View.OnClickListener{
    private TextView tv_fileName;

    private TextView tv_filemd5;

    private Button btn_chooseFile;

    private Button btn_upLoadFile;

    private Button btn_getmd5;
    private Context context;
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btn_chooseFile.setOnClickListener(this);
        btn_upLoadFile.setOnClickListener(this);
        btn_getmd5.setOnClickListener(this);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context=activity;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upoadfilefragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_fileName=(TextView)view.findViewById(R.id.tv_fileName);
        tv_filemd5=(TextView)view.findViewById(R.id.tv_filemd5);
        btn_chooseFile=(Button)view.findViewById(R.id.btn_chooseFile);
        btn_getmd5=(Button)view.findViewById(R.id.btn_getmd5);
        btn_upLoadFile=(Button)view.findViewById(R.id.btn_upLoadFile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode== Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            String str = bundle.getString("current_path");
            String testStr = bundle.getString("testStr");
            Log.d("", "获取的 文件路径： "+str);
            Log.d("", "testStr : "+testStr);
            tv_fileName.setText(str);
        }else{
            Toast.makeText(context,"没有选择文件",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        String path = tv_fileName.getText().toString();
        File file = new File(path);
        switch (v.getId()) {
            case R.id.btn_chooseFile:
                Intent intent = new Intent();
                intent.setClass(getContext(), SelectFileActivity.class);
                startActivityForResult(intent, 1);
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
                    String md5 = Md5Utils.getFileMd5(file);
                    int i=1;
                    if(i==2){
                        md5 = Md5Utils.getFileMd51(file);
                    }
                    tv_filemd5.setText(md5);
                }
                break;
        }
    }
}
