package com.sky.hyh.customviewsamples.customview.customviewpager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomViewPager extends ViewPager {
    public CustomViewPager(Context context) {
        this(context,null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        //重写setCurrentItem使得点击tab切换viewpager时不会出现滑动切换的效果
        super.setCurrentItem(item,false);
    }
}
