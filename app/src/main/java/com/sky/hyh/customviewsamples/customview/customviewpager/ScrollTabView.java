package com.sky.hyh.customviewsamples.customview.customviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ScrollTabView extends View {
    private static final String COLOR_DEFAULT ="#fdb709";
    private int mTabNum = 1;
    private int mCurrentNum;
    private float mWidth, mTabWidth, mOffset;
    private final Paint mPaint;
    private String mColor = COLOR_DEFAULT;

    public ScrollTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor(mColor));
    }

    public void setTabNum(int n) {
        mTabNum = n;
    }

    public void setColor(String color) {
        mColor = color;
        mPaint.setColor(Color.parseColor(mColor));
    }

    public void setOffset(int position, float offset) {
        mCurrentNum = position;
        mOffset = offset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTabWidth == 0) {
            mWidth = getWidth();
            mTabWidth = mWidth / mTabNum;
        }
        //根据位置和偏移量来计算滑动条的位置
        float left = (mCurrentNum + mOffset) * mTabWidth;
        final float right = left + mTabWidth;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
