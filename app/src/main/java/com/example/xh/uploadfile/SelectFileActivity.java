package com.example.xh.uploadfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xh.R;
import com.example.xh.ui.TestRxJavaFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiehui on 2016/10/13.
 */
public class SelectFileActivity extends Activity {
    private final static String TAG = "SelectFileActivity";
    ListView listView ;
    TextView textView ;
    Button btnParent;
    //记录当前的父类文件夹
    File currentParent;
    //记录当前路径下所有文件的文件数组
    File[] currentFiles;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectfile);

        //获取列表全部文件的ListView
        listView = (ListView) findViewById(R.id.file_list);
        textView = (TextView) findViewById(R.id.file_path);
        btnParent = (Button) findViewById(R.id.btn_parent);
        //获取系统的SD卡目录
        final File root = new File("/mnt/sdcard/");
        //如果SD卡存在
        if(root.exists()){
            currentParent = root ;
            currentFiles = root.listFiles();
            //使用当前目录下的全部文件，文件夹填充ListView
            inflateListView(currentFiles);
            //为listView的列表项的单击事件绑定监听器
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int postion, long id) {
                    //单击文件,返回文件路径
                    if( currentFiles[postion].isFile() ){
                        //TODO
                        String path = currentFiles[postion].getPath();
                        Intent intent = new Intent(SelectFileActivity.this,TestRxJavaFragment.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("testStr", "第一个activity的内容");
                        bundle.putString("current_path",path);
                        intent.putExtras(bundle);
                        setResult( RESULT_OK, intent );
                        finish();
                    } else {
                        //获取点击文件夹下面的所有的文件
                        File[] tep = currentFiles[postion].listFiles();
                        if( tep == null || tep.length == 0){
                            Toast.makeText(SelectFileActivity.this, "该文件夹下没有文件", Toast.LENGTH_SHORT).show();
                        } else {
                            //获取单击的列表的列表项对应的文件夹，设为当前的父类文件夹
                            currentParent = currentFiles[postion];
                            //保存当前的父文件夹内全部的文件和文件夹
                            currentFiles = tep;
                            //再次更新ListView
                            inflateListView(currentFiles);
                        }
                    }
                }
            });
            //获取上一级目录的按键
            btnParent.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    try{
                        if ( !currentParent.getCanonicalPath().equals("/mnt/sdcard") ) {
                            //获取上一层目录
                            currentParent = currentParent.getParentFile();
                            currentFiles = currentParent.listFiles();
                            //再次更新listView
                            inflateListView(currentFiles);
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void inflateListView(File[] files){
        //创建一个List集合，List集合的元素是 Map
        List< Map<String,Object> > listItems = new ArrayList< Map<String,Object> >();
        for (int i=0 ; i<files.length ; i++) {
            Map<String,Object> listItem = new HashMap<String,Object>();
            if (files[i].isDirectory()) {
                //是文件夹
                listItem.put("icon", R.mipmap.dir);
            } else {
                //是文件
                listItem.put("icon", R.mipmap.file);
            }
            listItem.put("fileName", files[i].getName());
            //添加到List 项
            listItems.add(listItem);
        }
        //创建一个 SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.selectfileitem,
                new String[]{"icon", "fileName"},
                new int[]{R.id.icon, R.id.file_name} );
        //为listView设置 Adapter
        listView.setAdapter(simpleAdapter);
        try {
            textView.setText("当前路径为: "+currentParent.getCanonicalPath());
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}