package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sky.hyh.customviewsamples.utils.SizeUtils;

/**
 * Created by hyh on 2018/9/29 18:10
 * 高亮引导蒙层
 */
public class RoundMaskView extends View {
    private PorterDuffXfermode mPorterDuffXfermode;
    private Paint mPaint;
    private RectF mRectF;
    private int mRadius;

    public RoundMaskView(Context context) {
        this(context,null);
    }

    public RoundMaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mRectF = new RectF();
        mRadius = SizeUtils.dp2px(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(getPaddingLeft(),
                getPaddingTop(),
                getPaddingLeft() + w,
                getPaddingTop() + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);

        //绘制蒙层背景
        canvas.drawColor(Color.parseColor("#99000000"));
        //移动坐标系原点到屏幕中心
        canvas.translate(getWidth()/2,getHeight()/2);
        mPaint.setXfermode(mPorterDuffXfermode);
        //绘制圆形高亮区域
        canvas.drawCircle(0,0,mRadius,mPaint);

        canvas.restore();
    }
}
