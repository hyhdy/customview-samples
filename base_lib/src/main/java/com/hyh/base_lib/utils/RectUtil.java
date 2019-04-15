package com.hyh.base_lib.utils;

import android.graphics.Rect;

public class RectUtil {
    /***
     * 判断某点是否在矩形区域
     * @param x
     * @param y
     * @param targetRect
     * @return
     */
    public static boolean isOverLay(Rect targetRect,int x,int y){
        return targetRect.contains(x, y);
    }
}
