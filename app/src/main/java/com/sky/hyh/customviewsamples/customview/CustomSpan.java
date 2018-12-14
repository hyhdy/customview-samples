package com.sky.hyh.customviewsamples.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import android.util.Log;

import com.sky.hyh.customviewsamples.utils.DensityUtil;

public class CustomSpan extends ReplacementSpan {
    private Paint mPaint;
    private int mWidth;
    private int mLeftMargin;
    private int mSpacing = DensityUtil.dip2px(5);

    public CustomSpan(int textSize, Typeface typeFace, int color,int leftMargin) {
        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(typeFace);
        mPaint.setColor(color);
        mLeftMargin = leftMargin;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text, start, end)+mLeftMargin;
        if(fm!=null){
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            Log.d("hyh", "CustomSpan: getSize: fm="+fm.toString()+" ,fmPaint="+fmPaint.toString()+" ,text="+text+" ,start="+start+" ,end="+end);

            fm.top = fmPaint.top - mSpacing;
            fm.ascent = fmPaint.ascent - mSpacing;
            fm.bottom = fmPaint.bottom + mSpacing;
            fm.descent = fmPaint.descent + mSpacing;

        }
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Log.d("hyh", "CustomSpan: draw: text="+text+" ,start="+start+" ,end="+end+" ,x="+x+" ,y="+y+" ,top="+top+" ,bottom="+bottom);
        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
        Log.d("hyh", "CustomSpan: draw: fmPaint="+fmPaint.toString());
        canvas.drawText(text, start, end, x+mLeftMargin, y, mPaint);
    }
}
