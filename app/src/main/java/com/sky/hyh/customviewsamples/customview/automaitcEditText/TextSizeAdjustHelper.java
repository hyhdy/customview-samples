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
     * 高度允许的误差值
     */
    private static final int MIN_HEIGHT_GAP_DP = 5;

    private static final float RATE_SCALE_ERROR_VALUE = 0.001f;

    private LayoutHelper mLayoutHelper;
    private int mHeightGap;
    private List<CustomTextSpanData> mCustomTextSpanDataList;
    private List<Float> mOriFontSizePxList;

    public TextSizeAdjustHelper(LayoutHelper layoutHelper) {
        mLayoutHelper = layoutHelper;
        mHeightGap = SizeUtils.dp2px(MIN_HEIGHT_GAP_DP);
    }

    public void calculateMatchHeightSize(String text,List<CustomTextSpanData> customTextSpanDataList, int maxHeight){
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

        int textHeight = getTextHeight(text);
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: textHeight="+textHeight+" ,maxHeight="+maxHeight);
        if(textHeight > maxHeight){
            //文本高度大于最大高度则需要等比例缩小各行字体大小，直到文本总高度小于最大高度
            int minHeight =maxHeight - mHeightGap;
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSize: minHeight="+minHeight+" ,maxHeight="+maxHeight);
            calculateMatchHeightSizeByRate(0,1,minHeight,maxHeight,text);
        }
        reset();
    }

    /**
     * 二分法查找合适的字体大小，字体大小按比例调整
     * @return
     */
    private void calculateMatchHeightSizeByRate(float lowRate,float highRate,int minHeight,int maxHeight,String text){
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: lowRate="+lowRate+" ,highRate="+highRate);
        if(highRate - lowRate <= RATE_SCALE_ERROR_VALUE){
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: rate return");
            return;
        }
        float middleRate= (lowRate+highRate)/2;
        scaleFontSizeByRate(middleRate);
        int height = getTextHeight(text);
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: height="+height);
        if(height > maxHeight){
            //缩小字体后文字高度大于最大值，需要继续缩小字体
            highRate = middleRate;
            calculateMatchHeightSizeByRate(lowRate,highRate,minHeight,maxHeight,text);
        } else if(height < minHeight){
            //缩小字体后文字高度小于最小值，需要放大字体
            lowRate = middleRate;
            calculateMatchHeightSizeByRate(lowRate,highRate,minHeight,maxHeight,text);
        }else{
            Log.d("hyh", "TextSizeAdjustHelper: calculateMatchHeightSizeByRate: height return");
        }
    }

    private int getTextHeight(String text){
        SpannableString spannableString = buildSpannableString(mCustomTextSpanDataList,text);
        return mLayoutHelper.buildFakeLayout(spannableString).getHeight();
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
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: width="+width+" ,maxWidth="+maxWidth);
        if(width > maxWidth){
            textSize = getNarrowFitTextSize(paint,text,maxWidth,1);
        }
        Log.d("hyh", "TextSizeAdjustHelper: calculateMatchWidthSize: fontSize="+paint.getTextSize());
        return textSize;
    }

    private float getNarrowFitTextSize(Paint paint,String text,int maxWidth,float rate){
        float textSize = paint.getTextSize();
        textSize -= 1 * rate;
        paint.setTextSize(textSize);
        float width = paint.measureText(text);
        Log.d("hyh", "TextSizeAdjustHelper: getNarrowFitTextSize: width="+width);
        //结束条件
        if(width <= maxWidth){
            return textSize;
        }else{
            return getNarrowFitTextSize(paint,text,maxWidth,rate);
        }
    }
}
