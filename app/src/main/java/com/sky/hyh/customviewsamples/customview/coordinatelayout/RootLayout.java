package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sky.hyh.customviewsamples.R;

/**
 * created by hyh on 2019/4/4
 */
public class RootLayout extends FrameLayout {
    private boolean mHaveList = true;

    public RootLayout(@NonNull Context context) {
        this(context,null);
    }

    public RootLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        updateView(mHaveList);
    }

    public void updateView(boolean haveList){
        Log.d("hyh","RootLayout: updateView");
        mHaveList = haveList;
        removeAllViews();
        if(haveList){
            CustomCoordinateLayout customCoordinateLayout = new CustomCoordinateLayout(getContext());
            addView(customCoordinateLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }else{
            TabLayoutContainer tabLayoutContainer = new TabLayoutContainer(getContext());
            addView(tabLayoutContainer,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public boolean isHaveList() {
        return mHaveList;
    }
}
