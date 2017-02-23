package com.example.xh.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.xh.R;
import com.example.xh.utils.MyApplication;

/**
 * Created by xiehui on 2017/2/23.
 */
public class RecycleItemTouchHelper extends ItemTouchHelper.Callback{
    private static final String TAG ="RecycleItemTouchHelper" ;
    private final ItemTouchHelperCallback helperCallback;
    private  boolean mCanDelete;

    public RecycleItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }

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
        helperCallback.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
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
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
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
        if (actionState==ItemTouchHelper.ACTION_STATE_SWIPE){
            //dX大于0时向右滑动，小于0向左滑动
            View itemView=viewHolder.itemView;
            Resources resources= MyApplication.getAppContext().getResources();
            Bitmap bitmap= BitmapFactory.decodeResource(resources, R.drawable.delete);
            int padding =10;
            int maxDrawWidth=2*padding+bitmap.getWidth();
            Paint paint=new Paint();
            paint.setColor(resources.getColor(R.color.btninvalid));
            int x=Math.round(Math.abs(dX));
            if (x==0){
                mCanDelete=false;
            }else if((x>maxDrawWidth)&&isCurrentlyActive){//背景图片画完整并且被操作，表示该item允许被删除
                mCanDelete=true;
            }

            int drawWidth=Math.min(x,maxDrawWidth);
            int itemTop=itemView.getBottom()-itemView.getHeight();
            if(dX>0){//向左滑动
              //先画一个背景
                c.drawRect(itemView.getLeft(),itemTop,drawWidth,itemView.getBottom(),paint);
                if (x>padding){
                    Rect rect=new Rect();//画图的位置
                    rect.left=itemView.getLeft()+padding;
                    rect.top=itemTop+(itemView.getBottom()-itemTop-bitmap.getHeight())/2;//图片居中
                    int maxRight=rect.left+bitmap.getWidth();
                    rect.right=Math.min(x,maxRight);
                    rect.bottom=rect.top+bitmap.getHeight();
                    Rect rect1=null;
                    if (x<maxRight){
                        rect1=new Rect();//不能再外面初始化，否则dx大于画图区域时，删除图片不显示
                        rect1.left=0;
                        rect1.top = 0;
                        rect1.bottom=bitmap.getHeight();
                        rect1.right=x-padding;
                    }
                    c.drawBitmap(bitmap,rect1,rect,paint);
                }
                float alpha = 1.0f - Math.abs(dX) / (float) itemView.getWidth();
                itemView.setAlpha(alpha);
                itemView.setTranslationX(dX);
            }else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }


    public interface ItemTouchHelperCallback{
        void onItemDelete(int positon);
        void onMove(int fromPosition,int toPosition);
    }
}
