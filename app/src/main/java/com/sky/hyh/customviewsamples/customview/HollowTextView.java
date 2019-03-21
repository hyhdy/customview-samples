package com.sky.hyh.customviewsamples.customview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by hyh on 2018/11/12 16:13
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class HollowTextView extends AppCompatTextView {
    private Paint mPaint;
    private PorterDuffXfermode mPorterDuffXfermode;

    public HollowTextView(Context context) {
        this(context,null);
    }

    public HollowTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR );
        //mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        //mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //需要在新的图层上绘制，绘制完后再再叠加到旧的图层上。
        canvas.saveLayer(0,0,canvas.getWidth(),canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),mPaint);
        getPaint().setXfermode(mPorterDuffXfermode);
        super.onDraw(canvas);
        canvas.restore();
    }
}
