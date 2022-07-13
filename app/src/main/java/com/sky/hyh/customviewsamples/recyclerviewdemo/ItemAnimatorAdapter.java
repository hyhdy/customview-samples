package com.sky.hyh.customviewsamples.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivy on 2017/3/21.
 * Description：
 */

public class ItemAnimatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context mContext;
    private List<String> mData=new ArrayList<>();

    public ItemAnimatorAdapter(Context context,List<String> data){
        this.mContext=context;
        this.mData=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_item_animator,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).tv.setText(position+":"+mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv);
        }
    }
}