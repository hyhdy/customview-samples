package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * created by curdyhuang on 2021/11/22
 * 平分子视图
 */
public class DivideEqualLayout extends FrameLayout {

    public DivideEqualLayout(Context context) {
        this(context,null);
    }

    public DivideEqualLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d("hyh","DivideEqualLayout: onSizeChanged: ");
        if (oldw!=w||oldh!=h){
            //不加post的话setLayoutParams没有效果
            post(new Runnable() {
                @Override
                public void run() {
                    divideLayout(w,h);
                }
            });
        }
    }

    private void divideLayout(int width, int height){
        Log.d("hyh","DivideEqualLayout: divideLayout: width="+width+" ,height="+height);
        int count = getChildCount();
        int childW = (int) (width*1f/count);
        for (int i=0;i<count;i++){
            View child = getChildAt(i);
            if(child!=null) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.width = childW;
                    layoutParams.height = height;
                    child.setLayoutParams(layoutParams);
                }
            }
        }
    }
}
