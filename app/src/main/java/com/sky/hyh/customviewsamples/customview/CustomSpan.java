package com.sky.hyh.customviewsamples.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;

public class CustomSpan extends ReplacementSpan {
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

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) paint.measureText(text, start, end)+mLeftMargin;
        return mWidth;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.drawText(text, start, end, x+mLeftMargin, y, mPaint);
    }
}
