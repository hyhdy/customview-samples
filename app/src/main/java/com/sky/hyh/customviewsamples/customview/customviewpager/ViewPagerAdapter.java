package com.sky.hyh.customviewsamples.customview.customviewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mDataList;

    public ViewPagerAdapter(Context context, List<String> dataList){
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
        PagerItemView pagerItemView = new PagerItemView(mContext);
        String color = mDataList.get(position);
        pagerItemView.setBackgroundColor(Color.parseColor(color));
        container.addView(pagerItemView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        return pagerItemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        PagerItemView itemView = (PagerItemView) object;
        container.removeView(itemView);
    }
}
