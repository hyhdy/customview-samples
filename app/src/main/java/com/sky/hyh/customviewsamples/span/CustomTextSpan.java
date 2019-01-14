package com.sky.hyh.customviewsamples.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.text.style.ReplacementSpan;
import android.util.Log;
import com.sky.hyh.customviewsamples.utils.DensityUtil;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// TODO: 2019/1/12 0012 设置不同分段的字体大小与默认TextView字体大小（15sp）差别过大时会有问题，需找时间解决该问题
public class CustomTextSpan extends ReplacementSpan {
    public static String TAG = "CustomTextSpan";
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_TOP = 2;

    @IntDef({ALIGN_BOTTOM,ALIGN_CENTER,ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignType{};

    /**
     * 绘制每一分段文本的画笔
     */
    private Paint mPaint;
    /**
     * 分段文本宽度
     */
    private int mWidth;
    /**
     * 分段文本左边距
     */
    private int mLeftMargin;
    /**
     * 分段文本对齐方式，默认底部对齐
     */
    @AlignType
    private int mAlign;
    public static final int SPACING = DensityUtil.dip2px(2);

    public CustomTextSpan(int textSize){
        this(textSize, Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
    }

    public CustomTextSpan(int textSize, Typeface typeFace) {
        this(textSize,typeFace, Color.WHITE);
    }

    public CustomTextSpan(int textSize, Typeface typeFace, int color) {
        this(textSize,typeFace,color,0);
    }

    public CustomTextSpan(int textSize, Typeface typeFace, int color,int leftMargin) {
        this(textSize,typeFace,color,leftMargin,ALIGN_BOTTOM);
    }

    public CustomTextSpan(int textSize, Typeface typeFace, int color,int leftMargin,int align) {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(typeFace);
        mPaint.setColor(color);
        mLeftMargin = leftMargin;
        mAlign = align;
    }

    /**
     *
     * @param paint: textView的画笔
     * @param text
     * @param start
     * @param end
     * @param fm
     * @return
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) mPaint.measureText(text, start, end)+mLeftMargin;

        if(fm!=null){
            if(start == 0){
                resetFm(fm);
            }
            Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
            Log.d(TAG, "getSize: text="+text+" ,start="+start+" ,end="+end);
            Log.d(TAG, "getSize: fm="+fm.toString());
            Log.d(TAG, "getSize: customTextPaintFm="+customTextPaintFm.toString());

            int top = customTextPaintFm.top-SPACING;
            int ascent = customTextPaintFm.ascent-SPACING;
            int bottom = customTextPaintFm.bottom+SPACING;
            int descent = customTextPaintFm.descent+SPACING;

            //每一分段文本的画笔不一样，字体大小，样式等不同导致不同分段文本高度不一致，
            //所以这里需要调整文本高度为高度最高的分段的文本高度，不然会导致高的文本分段被截断的问题
            if((fm.bottom - fm.top) < (bottom - top)){
                fm.top = top;
                fm.ascent = ascent;
                fm.bottom = bottom;
                fm.descent = descent;
            }
        }
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d(TAG, "draw: text="+text+" ,start="+start+" ,end="+end+" ,x="+x+" ,y="+y+" ,top="+top+" ,bottom="+bottom);
        switch (mAlign){
                case ALIGN_BOTTOM: {
                    canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
                }
                    break;
                case ALIGN_CENTER: {
                    Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
                    Log.d(TAG, "draw: customTextPaintFm=" + customTextPaintFm.toString());
                    //调整分段文本居中显示
                    int textHeight = bottom - top;
                    int segmentTextHeight = customTextPaintFm.bottom - customTextPaintFm.top;
                    y = (textHeight - segmentTextHeight) / 2 - customTextPaintFm.top;
                    canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
                }
                    break;
                case ALIGN_TOP:{
                    Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
                    Log.d(TAG, "draw: customTextPaintFm=" + customTextPaintFm.toString());
                    //调整分段文本居中显示
                    int textHeight = bottom - top;
                    int segmentTextHeight = customTextPaintFm.bottom - customTextPaintFm.top;
                    y -= (textHeight - segmentTextHeight) / 2;
                    canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
                }
                    break;
                default: {
                    canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
                }
                    break;
        }
    }

    private void resetFm(Paint.FontMetricsInt fontMetricsInt){
        fontMetricsInt.top = 0;
        fontMetricsInt.bottom = 0;
        fontMetricsInt.ascent = 0;
        fontMetricsInt.descent = 0;
        fontMetricsInt.leading = 0;
    }
}
