package com.example.xh.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xh.R;
import com.example.xh.adapter.CustomAdapter;
import com.example.xh.adapter.RecycleItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiehui on 2017/2/21.
 * Fragment生命周期 onAttach()，onCreate(),onCreateView(),onActivityCreate()
 * onStart(),onResume(),onPause(),onStop(),onDestoryView(),onDestory(),onDetach()
 */
public class RecycleViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RecycleViewFragment";
    private RecyclerView recycleview;
    private CustomAdapter adapter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleview = (RecyclerView) view.findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> strings=new ArrayList<>();
        for (int i=0;i<10;i++){
            strings.add("测试"+i);
        }
        adapter=new CustomAdapter(getActivity(),strings);
        recycleview.setAdapter(adapter);
        ItemTouchHelper.Callback callback=new RecycleItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recycleview);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycle_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onClick(View v) {
    }
}
