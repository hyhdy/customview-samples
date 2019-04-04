package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sky.hyh.customviewsamples.customview.customviewpager.PagerItemView;

import java.util.List;

/**
 * created by hyh on 2019/4/4
 */
public class CoordinatePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<List<Integer>> mDataList;

    public CoordinatePagerAdapter(Context context, List<List<Integer>> dataList){
        mContext=context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        if(mDataList == null){
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerPagerItem recyclerPagerItem = new RecyclerPagerItem(mContext);
        recyclerPagerItem.setList(mDataList.get(position));
        container.addView(recyclerPagerItem,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return recyclerPagerItem;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        RecyclerPagerItem itemView = (RecyclerPagerItem) object;
        container.removeView(itemView);
    }
}
