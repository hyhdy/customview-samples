package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/2/26 16:44
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TextSizeAdjustHelper {
    /**
     * 目标宽度和理想宽度之间的允许高度误差
     */
    private static final float MIN_WIDTH_GAP = 1f;
    /**
     * 高度允许的误差值
     */
    private static final int HEIGHT_ERROR_VALUE = 10;
    public static final float CONVER_RATE = 0.1f;
    private static List<Paint> sOriPaintList;
    private static List<Paint> sCopyPaintList = new ArrayList<>();

    public static void calculateMatchHeightSize(List<Paint> paintList, int maxHeight){
        if(paintList == null){
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: error: paintList is null");
            return;
        }
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: paintList.size="+paintList.size());
        sOriPaintList = paintList;
        copyPaintList(paintList);
        int totalLineHeight = getTotalLineHeight();
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: totalLineHeight="+totalLineHeight);
        if(totalLineHeight > maxHeight){
            //文本高度大于最大高度则需要等比例缩小各行字体大小，直到文本总高度小于最大高度
            int minHeight =maxHeight - HEIGHT_ERROR_VALUE;
            calculateMatchHeightSizeByRate(0,1,minHeight,maxHeight);
            copyValue();
        }
        reset();
    }

    /**
     * 二分法查找合适的字体大小，字体大小按比例调整
     * @return
     */
    private static void calculateMatchHeightSizeByRate(float lowRate,float highRate,int minHeight,int maxHeight){
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: lowRate="+lowRate+" ,highRate="+highRate+" ,minHeight="+minHeight+" ,maxHeight="+maxHeight);
        float middleRate= (lowRate+highRate)/2;
        scaleFontSizeByRate(middleRate);
        int height = getTotalLineHeight();
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: height="+height);
        if(height > maxHeight){
            //缩小字体后文字高度大于最大值，需要继续缩小字体
            highRate = middleRate;
            calculateMatchHeightSizeByRate(lowRate,highRate,minHeight,maxHeight);
        } else if(height < minHeight){
            //缩小字体后文字高度小于最小值，需要放大字体
            lowRate = middleRate;
            calculateMatchHeightSizeByRate(lowRate,highRate,minHeight,maxHeight);
        }else{
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: height return");
        }
    }

    private static void copyPaintList(@NonNull List<Paint> paintList){
        for(Paint paint: paintList){
            Paint copyPaint = new Paint(paint);
            sCopyPaintList.add(copyPaint);
        }
        Log.d("hyh", "TextSizeAdjustHelper: copyPaintList: sCopyPaintList.size="+sCopyPaintList.size());
    }

    /**
     * 获取单行文本高度
     * @return
     */
    private static int getSingleLineHeight(Paint paint){
        int height = paint.getFontMetricsInt(null);
        //Log.d("hyh", "TextSizeAdjustHelper: getSingleLineHeight: fontSize="+paint.getTextSize()+" ,height="+height);
        return height;
    }

    private static int getTotalLineHeight(){
        int totalLineHeight = 0;
        for(Paint paint: sCopyPaintList){
            int lineHeight = getSingleLineHeight(paint);
            totalLineHeight += lineHeight;
        }
        return totalLineHeight;
    }

    private static void scaleFontSizeByRate(float rate){
        for(int i=0;i<sOriPaintList.size();i++){
            float fontSize = sOriPaintList.get(i).getTextSize() * rate;
            sCopyPaintList.get(i).setTextSize(fontSize);
        }
    }

    private static void copyValue(){
        Log.d("hyh", "TextSizeAdjustHelper: copyValue: sCopyPaintList.size="+sCopyPaintList.size());
        for(int i=0;i<sCopyPaintList.size();i++){
            float fontSize = sCopyPaintList.get(i).getTextSize();
            Log.d("hyh", "TextSizeAdjustHelper: copyValue: fontSize="+fontSize);
            sOriPaintList.get(i).setTextSize(fontSize);
        }
    }

    private static void reset(){
        sOriPaintList.clear();
        sOriPaintList = null;
        sCopyPaintList.clear();
    }

    public static void calculateMatchWidthSize(Paint paint,String text,int maxWidth){
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: width="+width);
        if(width > maxWidth){
            getNarrowFitTextSize(paint,text,maxWidth,1);
        }else if(maxWidth - width > MIN_WIDTH_GAP){
            getZoomFitTextSize(paint,text,maxWidth,1);
        }
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: fontSize="+paint.getTextSize());
    }

    private static void getNarrowFitTextSize(Paint paint,String text,int maxWidth,float rate){
        Log.d("hyh", "TextSizeAdjustHelper: getNarrowFitTextSize: text="+text+" ,maxWidth="+maxWidth+" ,rate="+rate);
        float textSize = paint.getTextSize();
        textSize -= 1 * rate;
        paint.setTextSize(textSize);
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: getNarrowFitTextSize: width="+width);
        //结束条件
        if(width < maxWidth || (maxWidth - width) < MIN_WIDTH_GAP){
            return;
        }

        if(width > maxWidth){
            getNarrowFitTextSize(paint,text,maxWidth,rate);
        }else if(width < maxWidth){
            getZoomFitTextSize(paint, text,maxWidth, CONVER_RATE * rate);
        }
    }

    private static void getZoomFitTextSize(Paint paint,String text,int maxWidth,float rate){
        Log.d("hyh", "TextSizeAdjustHelper: getZoomFitTextSize: text="+text+" ,maxWidth="+maxWidth+" ,rate="+rate);
        float textSize = paint.getTextSize();
        textSize += 1 * rate;
        paint.setTextSize(textSize);
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: getZoomFitTextSize: width="+width);
        //结束条件
        if(width < maxWidth || (maxWidth - width) < MIN_WIDTH_GAP){
            return;
        }

        if(width > maxWidth){
            getNarrowFitTextSize(paint,text,maxWidth,CONVER_RATE * rate);
        }else if(width < maxWidth){
            getZoomFitTextSize(paint, text,maxWidth, rate);
        }
    }

    //private Layout getDefLayout(){
    //    int oriHeight = getLayout().getHeight();
    //    SpannableString spannableString = new SpannableString(getText());
    //    DynamicLayout
    //        dynamicLayout = new DynamicLayout(spannableString,new TextPaint(getPaint()),getLayout().getWidth(),getLayout().getAlignment(),getLayout().getSpacingMultiplier(),getLayout().getSpacingAdd(),getIncludeFontPadding());
    //    Log.d("hyh", "AutomaticEditText: getDefLayout: oriHeigh="+oriHeight+" ,height="+dynamicLayout.getHeight());
    //    return dynamicLayout;
    //}

}
