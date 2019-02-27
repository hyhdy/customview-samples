package com.sky.hyh.customviewsamples.utils;

import android.graphics.Paint;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.widget.TextView;
import java.util.List;

/**
 * Created by hyh on 2019/2/26 16:44
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TextSizeAdjustHelper {
    private static final String TAG = "TextSizeAdjustHelper";
    /**
     * 目标高度和理想高度之间的允许高度误差
     */
    private static final float MIN_HEIGHT_GAP = 0.5f;
    /**
     * 探测字体大小时的收敛比率
     */
    private static final float CONVER_RATE = 0.1f;

    /**
     * 获取匹配某个指定高度的合适的字体尺寸，最后的字体尺寸会存入paint中
     * @param layout：textview的layout
     * @param paintList：每一行的画笔
     * @param targetTextHeight：匹配的文本高度，不等于这个高度就要调整字体大小以适应该高度
     * @param rate
     */
    public static void getFitTextSize(Layout layout,float targetTextHeight,float rate,Paint ... paintList){
        int totalHeight = 0;
        for(Paint paint: paintList){
            float height = getSingleLineHeight(layout,paint);
            totalHeight += height;
        }

        if(Math.abs(totalHeight - targetTextHeight) > MIN_HEIGHT_GAP){
            if(totalHeight > targetTextHeight){
                getNarrowFitTextSize(layout,targetTextHeight,rate,paintList);
            }else{
                getZoomFitTextSize(layout,targetTextHeight,rate,paintList);
            }
        }
    }

    private static void getNarrowFitTextSize(Layout layout, float targetTextHeight,float rate,Paint ... paintList){
        int totalHeight = 0;
        for(Paint paint: paintList){
            float textSize = paint.getTextSize();
            textSize -= 1 * rate;
            paint.setTextSize(textSize);

            float height = getSingleLineHeight(layout,paint);
            totalHeight += height;
        }

        if(Math.abs(totalHeight - targetTextHeight)<=MIN_HEIGHT_GAP){
            return;
        }

        if(totalHeight > targetTextHeight){
            //缩小字体后文字高度大于目标高度，需要继续缩小字体
            getNarrowFitTextSize(layout,targetTextHeight,rate,paintList);
        }else if(totalHeight < targetTextHeight){
            //缩小字体后文字高度小于目标高度，需要调整缩放比例，并放大字体
            getZoomFitTextSize(layout, targetTextHeight, CONVER_RATE * rate,paintList);
        }else{
            return;
        }
    }

    private static void getZoomFitTextSize(Layout layout, float targetTextHeight,float rate,Paint ... paintList){
        int totalHeight = 0;
        for(Paint paint: paintList){
            float textSize = paint.getTextSize();
            textSize += 1 * rate;
            paint.setTextSize(textSize);

            float height = getSingleLineHeight(layout,paint);
            totalHeight += height;
        }

        //结束条件
        if(Math.abs(totalHeight - targetTextHeight)<=MIN_HEIGHT_GAP){
            return;
        }

        if(totalHeight > targetTextHeight){
            //放大字体后文字高度大于目标高度，需要调整缩放比例，并缩小字体
            getNarrowFitTextSize(layout,targetTextHeight,CONVER_RATE * rate,paintList);
        }else if(totalHeight < targetTextHeight){
            //放大字体后文字高度小于目标高度，需要继续放大字体
            getZoomFitTextSize(layout,targetTextHeight, rate,paintList);
        }else{
            return;
        }
    }

    /**
     * 获取单行文本高度，包括设置了行间距后增加的高度
     * @param layout
     * @param paint
     * @return
     */
    private static int getSingleLineHeight(Layout layout, Paint paint){
        int height = Math.round(paint.getFontMetricsInt(null) * layout.getSpacingMultiplier() + layout.getSpacingAdd());
        return height;
    }
}
