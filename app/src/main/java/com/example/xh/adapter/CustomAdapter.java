package com.example.xh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xh.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by xiehui on 2017/2/21.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomeViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    private Context context;
    private List<String> list;

    public CustomAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CustomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleview_item, parent, false);
        return new CustomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomeViewHolder holder, int position) {
        holder.item_textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemDelete(int positon) {
        list.remove(positon);
        notifyItemRemoved(positon);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(list,fromPosition,toPosition);//交换数据
        notifyItemMoved(fromPosition,toPosition);
    }

    public class CustomeViewHolder extends RecyclerView.ViewHolder {


        TextView item_textView;

        public CustomeViewHolder(View itemView) {
            super(itemView);
            item_textView = (TextView) itemView.findViewById(R.id.item_textView);
        }

    }
}
