package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.util.AttributeSet;

import com.sky.hyh.customviewsamples.R;

/**
 * created by hyh on 2019/4/4
 */
public class MyAppBarLayout extends BaseAppBarLayout {
    private static final int RES_ID = R.layout.layout_appbarlayout_container;

    public MyAppBarLayout(Context context) {
        super(context);
    }

    public MyAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    int getResId() {
        return RES_ID;
    }
}
