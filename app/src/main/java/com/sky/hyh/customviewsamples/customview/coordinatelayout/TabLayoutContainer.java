package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.sky.hyh.customviewsamples.R;

/**
 * created by hyh on 2019/4/4
 */
public class TabLayoutContainer extends ScrollView {
    public static final int RES_ID = R.layout.container_tab_layout;

    public TabLayoutContainer(Context context) {
        this(context,null);
    }

    public TabLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,RES_ID,this);
    }
}
