package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.hyh.customviewsamples.R;

import java.util.List;

import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_1;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_2;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_3;

/**
 * created by hyh on 2019/4/3
 */
public class FlexAdapter extends RecyclerView.Adapter {
    public static final int FLEX_COUNT_PER_ITEM = 3;
    private List<FlexModel> mDataList;

    public FlexAdapter(List<FlexModel> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(getLayoutId(i), viewGroup, false);
        return new FlexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((FlexViewHolder)viewHolder).setData(mDataList.get(i),getItemViewType(i));
    }

    @Override
    public int getItemCount() {
        if(mDataList == null){
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_TYPE_1;

        int i = position / FLEX_COUNT_PER_ITEM;
        int j = position % FLEX_COUNT_PER_ITEM;
        if(i % 2 == 0){
            if(j == 0){
                type = ITEM_TYPE_1;
            }else if(j == 1){
                type = ITEM_TYPE_2;
            }else if(j == 2){
                type = ITEM_TYPE_3;
            }
        }else{
            if(j == 0){
                type = ITEM_TYPE_3;
            }else if(j == 1){
                type = ITEM_TYPE_1;
            }else if(j == 2){
                type = ITEM_TYPE_2;
            }
        }

        return type;
    }

    private int getLayoutId(int type){
        int layoutId = R.layout.flex_layout_1;

        if(type == ITEM_TYPE_1){
            layoutId = R.layout.flex_layout_1;
        }else if(type == ITEM_TYPE_2){
            layoutId = R.layout.flex_layout_2;
        }else if(type == ITEM_TYPE_3){
            layoutId = R.layout.flex_layout_3;
        }

        return layoutId;
    }
}
