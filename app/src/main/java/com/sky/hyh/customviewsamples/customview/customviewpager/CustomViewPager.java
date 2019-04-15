package com.sky.hyh.customviewsamples.customview.customviewpager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean mIsPagingEnabled = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mIsPagingEnabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mIsPagingEnabled && super.onTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        mIsPagingEnabled = b;
    }

    @Override
    public void setCurrentItem(int item) {
        //重写setCurrentItem使得点击tab切换viewpager时不会出现滑动切换的效果
        super.setCurrentItem(item,false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("hyh","CustomViewPager: onMeasure: pre height="+height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("hyh","CustomViewPager: onMeasure: after height="+getMeasuredHeight());
    }
}
