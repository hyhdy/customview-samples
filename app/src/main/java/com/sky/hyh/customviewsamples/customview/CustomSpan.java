package com.sky.hyh.customviewsamples.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import android.util.Log;

import com.sky.hyh.customviewsamples.utils.DensityUtil;

public class CustomSpan extends ReplacementSpan {
    public static String TAG = "CustomSpan";
    /**
     * 绘制每一分段文本的画笔
     */
    private Paint mPaint;
    private int mWidth;
    private int mLeftMargin;

    public CustomSpan(int textSize, Typeface typeFace, int color,int leftMargin) {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(typeFace);
        mPaint.setColor(color);
        mLeftMargin = leftMargin;
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
        mWidth = (int) paint.measureText(text, start, end)+mLeftMargin;

        if(fm!=null){
            Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
            Log.d(TAG, "getSize: text="+text+" ,start="+start+" ,end="+end);
            Log.d(TAG, "getSize: fm="+fm.toString());
            Log.d(TAG, "getSize: customTextPaintFm="+customTextPaintFm.toString());

            //每一分段文本的画笔不一样，字体大小，样式等不同导致不同分段文本高度不一致，
            //所以这里需要调整文本高度为高度最高的分段的文本高度，不然会导致高的文本分段被截断的问题
            if(customTextPaintFm.top < fm.top){
                fm.top = customTextPaintFm.top;
            }
            if(customTextPaintFm.ascent < fm.ascent){
                fm.ascent = customTextPaintFm.ascent;
            }
            if(customTextPaintFm.bottom > fm.bottom){
                fm.bottom = customTextPaintFm.bottom;
            }
            if(customTextPaintFm.descent > fm.descent){
                fm.descent = customTextPaintFm.descent;
            }
        }
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d(TAG, "draw: text="+text+" ,start="+start+" ,end="+end+" ,x="+x+" ,y="+y+" ,top="+top+" ,bottom="+bottom);
        Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
        Log.d(TAG, "draw: customTextPaintFm="+customTextPaintFm.toString());
        //调整分段文本居中显示
        int textHeight = bottom - top;
        int segmentTextHeight = customTextPaintFm.bottom - customTextPaintFm.top;
        y = (textHeight - segmentTextHeight) / 2 - customTextPaintFm.top;
        canvas.drawText(text, start, end, x+mLeftMargin, y, mPaint);
    }
}
