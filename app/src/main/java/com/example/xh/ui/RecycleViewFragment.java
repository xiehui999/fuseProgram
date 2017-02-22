package com.example.xh.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xh.R;
import com.example.xh.adapter.CustomAdapter;

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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleview = (RecyclerView) view.findViewById(R.id.recycleview);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> strings=new ArrayList<>();
        for (int i=0;i<10;i++){
            strings.add("测试"+i);
        }

        recycleview.setAdapter(new CustomAdapter(getActivity(),strings));
        ItemTouchHelper.Callback callback=new ItemTouchHelper.Callback() {
            /**
             * 设置滑动类型标记
             *
             * @param recyclerView
             * @param viewHolder
             * @return
             *          返回一个整数类型的标识，用于判断Item那种移动行为是允许的
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                Log.e(TAG, "getMovementFlags: " );
                //
                return makeMovementFlags(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START | ItemTouchHelper.END);
            }
            /**
             * Item是否支持长按拖动
             *
             * @return
             *          true  支持长按操作
             *          false 不支持长按操作
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }
            /**
             * Item是否支持滑动
             *
             * @return
             *          true  支持滑动操作
             *          false 不支持滑动操作
             */
            @Override
            public boolean isItemViewSwipeEnabled() {
                return super.isItemViewSwipeEnabled();
            }
            /**
             * 拖拽切换Item的回调
             *
             * @param recyclerView
             * @param viewHolder
             * @param target
             * @return
             *          如果Item切换了位置，返回true；反之，返回false
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Log.e(TAG, "onMove: " );
                return false;
            }
            /**
             * 滑动Item
             *
             * @param viewHolder
             * @param direction
             *           Item滑动的方向
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.e(TAG, "onSwiped: ");
            }
            /**
             * Item被选中时候回调
             *
             * @param viewHolder
             * @param actionState
             *          当前Item的状态
             *          ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
             *          ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
             *          ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }
            /**
             * 移动过程中绘制Item
             *
             * @param c
             * @param recyclerView
             * @param viewHolder
             * @param dX
             *          X轴移动的距离
             * @param dY
             *          Y轴移动的距离
             * @param actionState
             *          当前Item的状态
             * @param isCurrentlyActive
             *          如果当前被用户操作为true，反之为false
             */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
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
