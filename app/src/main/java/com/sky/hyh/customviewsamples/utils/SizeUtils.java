
package com.sky.hyh.customviewsamples.utils;

import android.content.Context;

import com.sky.hyh.customviewsamples.MyApplication;

public class SizeUtils {

    public static int dp2px(float dpValue) {
        final float scale = MyApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = MyApplication.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(final float spValue) {
        final float fontScale =MyApplication.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
