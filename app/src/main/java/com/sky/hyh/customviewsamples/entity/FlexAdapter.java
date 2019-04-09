package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.utils.SizeUtils;

import java.util.List;

import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_1;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_2;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_3;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_4;

/**
 * created by hyh on 2019/4/3
 */
public class FlexAdapter extends RecyclerView.Adapter {
    public static final int ITEM_HEIGHT = SizeUtils.dp2px(354);
    public static final float RATIO_W = 0.5f;
    public static final float RATIO_H = 0.62f;

    public static final int FLEX_COUNT_PER_ITEM = 3;
    private List<FlexModel> mDataList;

    public FlexAdapter(List<FlexModel> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(getLayoutId(viewType), viewGroup, false);
        if(viewType != ITEM_TYPE_4) {
            Pair<Integer, Integer> pair = getWHRatioByType(viewGroup.getWidth(), ITEM_HEIGHT, viewType);
            view.setLayoutParams(new ViewGroup.LayoutParams(pair.first, pair.second));
        }else{
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,SizeUtils.dp2px(50));
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        }
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
        if(position == getItemCount() -1){
            return ITEM_TYPE_4;
        }
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
        int layoutId = R.layout.staggerd_layout_1;

        if(type == ITEM_TYPE_1){
            layoutId = R.layout.staggerd_layout_1;
        }else if(type == ITEM_TYPE_2){
            layoutId = R.layout.staggerd_layout_2;
        }else if(type == ITEM_TYPE_3){
            layoutId = R.layout.staggerd_layout_3;
        }else if(type == ITEM_TYPE_4){
            layoutId = R.layout.stag_item_tail;
        }

        return layoutId;
    }

    public Pair<Integer,Integer> getWHRatioByType(int totalWidth,int totalHeight,int type) {
        int width = totalWidth;
        int height = totalHeight;

        if(type == ITEM_TYPE_1){
            width = (int) (totalWidth * RATIO_W);
            height = totalHeight;
        }else if(type == ITEM_TYPE_2){
            width = totalWidth - (int) (totalWidth * RATIO_W);
            height = (int) (totalHeight * RATIO_H);
        }else if(type == ITEM_TYPE_3){
            width = totalWidth - (int) (totalWidth * RATIO_W);
            height = totalHeight - (int) (totalHeight * RATIO_H);
        }
        Log.d("hyh","FlexAdapter: getWHRatioByType: totalWidth="+totalWidth+" ,totalHeight="+totalHeight+" ,width="+width+" ,height="+height+" ,type="+type);
        return Pair.create(width,height);
    }

    public Pair<Float,Float> getWHRatio(int position) {
        float wRatio = 1;
        float hRatio = 1;

        int i = position / FLEX_COUNT_PER_ITEM;
        int j = position % FLEX_COUNT_PER_ITEM;
        if(i % 2 == 0){
            if(j == 0){
                wRatio = 0.60f;
                hRatio = 1;
            }else if(j == 1){
                wRatio = 0.40f;
                hRatio = 0.62f;
            }else if(j == 2){
                wRatio = 0.40f;
                hRatio = 0.38f;
            }
        }else{
            if(j == 0){
                wRatio = 0.40f;
                hRatio = 0.38f;
            }else if(j == 1){
                wRatio = 0.60f;
                hRatio = 1;
            }else if(j == 2){
                wRatio = 0.40f;
                hRatio = 0.62f;
            }
        }

        return Pair.create(wRatio,hRatio);
    }
}
