package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.graphics.Paint;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;
import com.sky.hyh.customviewsamples.span.spandata.CustomTextSpanData;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
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
    private static final float MIN_WIDTH_GAP_DP = 5;
    /**
     * 高度允许的误差值
     */
    private static final int MIN_HEIGHT_GAP_DP = 5;

    private static final float RATE_SCALE_ERROR_VALUE = 0.001f;
    public static final float CONVER_RATE = 0.1f;

    private int mHeightGap;
    private int mWidthGap;
    private TextView mHost;
    private List<CustomTextSpanData> mCustomTextSpanDataList;
    private List<Float> mOriFontSizePxList;

    public TextSizeAdjustHelper(TextView host) {
        mHost = host;
        mHeightGap = SizeUtils.dp2px(MIN_HEIGHT_GAP_DP);
        mWidthGap = SizeUtils.dp2px(MIN_WIDTH_GAP_DP);
    }

    public void calculateMatchHeightSize(List<CustomTextSpanData> customTextSpanDataList, int maxHeight){
        if(customTextSpanDataList == null){
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: error: customTextSpanDataList is null");
            return;
        }
        mCustomTextSpanDataList = customTextSpanDataList;
        if(mOriFontSizePxList == null){
            mOriFontSizePxList = new ArrayList<>();
        }
        for(CustomTextSpanData customTextSpanData: customTextSpanDataList){
            mOriFontSizePxList.add(customTextSpanData.getTextSizePx());
        }

        int totalLineHeight = getTextHeight();
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: totalLineHeight="+totalLineHeight);
        if(totalLineHeight > maxHeight){
            //文本高度大于最大高度则需要等比例缩小各行字体大小，直到文本总高度小于最大高度
            int minHeight =maxHeight - mHeightGap;
            calculateMatchHeightSizeByRate(0,1,minHeight,maxHeight);
        }
        reset();
    }

    /**
     * 二分法查找合适的字体大小，字体大小按比例调整
     * @return
     */
    private void calculateMatchHeightSizeByRate(float lowRate,float highRate,int minHeight,int maxHeight){
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: lowRate="+lowRate+" ,highRate="+highRate+" ,minHeight="+minHeight+" ,maxHeight="+maxHeight);
        if(highRate - lowRate <= RATE_SCALE_ERROR_VALUE){
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: rate return");
            return;
        }
        float middleRate= (lowRate+highRate)/2;
        scaleFontSizeByRate(middleRate);
        int height = getTextHeight();
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

    private int getTextHeight(){
        Layout layout = buildLayout(mHost.getLayout());
        return layout.getHeight();
    }

    private void scaleFontSizeByRate(float rate){
        Log.d("hyh", "TextSizeAdjustHelper: scaleFontSizeByRate: rate="+rate);
        for(int i=0;i<mOriFontSizePxList.size();i++){
            float fontSize = mOriFontSizePxList.get(i) * rate;
            mCustomTextSpanDataList.get(i).setTextSizePx(fontSize);
        }
    }

    private void reset(){
        mOriFontSizePxList.clear();
        mCustomTextSpanDataList.clear();
        mCustomTextSpanDataList = null;
    }

    private Layout buildLayout(Layout layout){
        SpannableString spannableString = buildSpannableString(mCustomTextSpanDataList,layout.getText());
        DynamicLayout dynamicLayout = new DynamicLayout(spannableString,new TextPaint(layout.getPaint()),layout.getWidth(),layout.getAlignment(),layout.getSpacingMultiplier(),layout.getSpacingAdd(),mHost.getIncludeFontPadding());
        return dynamicLayout;
    }

    private SpannableString buildSpannableString(List<CustomTextSpanData> customTextSpanDataList,CharSequence text){
        SpannableString spannableString = new SpannableString(text);
        for(CustomTextSpanData customTextSpanData: customTextSpanDataList) {
            spannableString.setSpan(customTextSpanData.onCreateSpan(),
                customTextSpanData.getStartIndex(), customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public float calculateMatchWidthSize(Paint paint,String text,int maxWidth){
        float textSize = paint.getTextSize();
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: width="+width+" ,maxWidth="+maxWidth+" ,mWidthGap="+mWidthGap);
        if(width > maxWidth){
            textSize = getNarrowFitTextSize(paint,text,maxWidth,1);
        }
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: fontSize="+paint.getTextSize());
        return textSize;
    }

    private float getNarrowFitTextSize(Paint paint,String text,int maxWidth,float rate){
        Log.d("hyh", "TextSizeAdjustHelper: getNarrowFitTextSize: text="+text+" ,maxWidth="+maxWidth+" ,rate="+rate);
        float textSize = paint.getTextSize();
        textSize -= 1 * rate;
        paint.setTextSize(textSize);
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: getNarrowFitTextSize: width="+width);
        //结束条件
        if(width < maxWidth && (maxWidth - width) < mWidthGap){
            return textSize;
        }

        if(width > maxWidth){
            return getNarrowFitTextSize(paint,text,maxWidth,rate);
        }else if(width < maxWidth){
            return getZoomFitTextSize(paint, text,maxWidth, CONVER_RATE * rate);
        }else{
            return textSize;
        }
    }

    private float getZoomFitTextSize(Paint paint,String text,int maxWidth,float rate){
        Log.d("hyh", "TextSizeAdjustHelper: getZoomFitTextSize: text="+text+" ,maxWidth="+maxWidth+" ,rate="+rate);
        float textSize = paint.getTextSize();
        textSize += 1 * rate;
        paint.setTextSize(textSize);
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: getZoomFitTextSize: width="+width);
        //结束条件
        if(width < maxWidth && (maxWidth - width) < mWidthGap){
            return textSize;
        }

        if(width > maxWidth){
            return getNarrowFitTextSize(paint,text,maxWidth,CONVER_RATE * rate);
        }else if(width < maxWidth){
            return getZoomFitTextSize(paint, text,maxWidth, rate);
        }else{
            return textSize;
        }
    }
}
