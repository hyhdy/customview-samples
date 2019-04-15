
package com.hyh.base_lib.utils;

import com.hyh.base_lib.MyApplication;

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

    public static int px2sp(final float pxValue) {
        final float fontScale = MyApplication.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
