package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import com.sky.hyh.customviewsamples.utils.TextSizeAdjustHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/2/26 15:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 自动排版输入框
 */
public class AutomaticEditText extends AppCompatEditText {
    public static final float DEF_FONT_SIZE = 16;
    private int mLineCount;
    /**
     * 最大文本高度
     */
    private int mMaxTextHeight;
    /**
     * 最大文本宽度，该值不是恒定不变的，会随着输入的文本动态改变
     */
    private int mMaxTextWidth;
    private float mMinFontSize;
    private float mMaxFontSize;
    /**
     * 单行最多显示多少个中文
     */
    private int mMaxChineseNumOneLine;

    public AutomaticEditText(Context context) {
        this(context,null);
    }

    public AutomaticEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setTextSize(DEF_FONT_SIZE);
        mMinFontSize = SizeUtils.sp2px(DEF_FONT_SIZE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w != oldw || h != oldh){
            mMaxTextHeight = h - getPaddingTop() - getPaddingBottom();
            mMaxTextWidth = w - getPaddingLeft() - getPaddingRight();
            Log.d("hyh", "AutomaticEditText: onSizeChanged: mMaxTextHeight="+ mMaxTextHeight+ " ,mMaxTextWidth="+mMaxTextWidth);
            calculateOneLineMaxChineseNum(mMaxTextWidth);
        }
    }

    private void calculateOneLineMaxChineseNum(int lineWidth){
        Log.d("hyh", "AutomaticEditText: calculateOneLineMaxChineseNum: lineWidth ="+lineWidth);
        int num = 0;
        Paint paint = getPaint();
        StringBuilder testChinese = new StringBuilder("中");
        float width = paint.measureText(testChinese.toString());
        Log.d("hyh", "AutomaticEditText: calculateOneLineMaxChineseNum: width="+width);
        while (width < lineWidth){
             num++;
             testChinese.append("中");
             width = paint.measureText(testChinese.toString());
             Log.d("hyh", "AutomaticEditText: calculateOneLineMaxChineseNum: width="+width);
        }
        mMaxChineseNumOneLine = num;
    }

    @Override
    public boolean onPreDraw() {
        boolean result = super.onPreDraw();
        detectFontSize();
        return result;
    }

    /**
     * 动态调整字体大小
     */
    private void detectFontSize() {
        Layout layout = getLayout();
        if(layout != null){
            int lineCount = layout.getLineCount();
            Log.d("hyh", "AutomaticEditText: detectFontSize: lineCount="+lineCount);
            if (lineCount != mLineCount) {
                //发生换行操作

            }else{

            }
            mLineCount = lineCount;
        }else{
           Log.d("hyh", "AutomaticEditText: detectFontSize: layout is null");
        }
    }

    private float calculateMaxFontSize(int lineCount,float defFontSize,int maxHeight){
        List<Paint> paintList = new ArrayList();
        if(lineCount > 1){
            //获取其他行的paint

        }
        TextPaint textPaint = new TextPaint(getPaint());
        textPaint.setTextSize(defFontSize);
        paintList.add(textPaint);

        TextSizeAdjustHelper.getFitTextSize(getLayout(),maxHeight,1,new Paint[]{textPaint});
        mMaxFontSize = paintList.get(paintList.size()-1).getTextSize();
        return mMaxFontSize;
    }
}
