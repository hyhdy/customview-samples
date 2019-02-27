package com.sky.hyh.customviewsamples.customview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by hyh on 2018/11/12 16:13
 * E-Mail Address：fjnuhyh122@gmail.com
 * 镂空TextView的另一种实现方式
 */
public class HollowTextViewV2 extends AppCompatTextView {
    private Paint mTextPaint, mBackgroundPaint;
    private Bitmap mBackgroundBitmap, mTextBitmap;
    private Canvas mBackgroundCanvas, mTextCanvas;
    private int mBackgroundColor;

    public HollowTextViewV2(Context context) {
        this(context, null);
    }

    public HollowTextViewV2(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HollowTextViewV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /*** * 初始化画笔属性 */
    private void initPaint() {
        mBackgroundColor =Color.WHITE;
        //画文字的paint
        mTextPaint = new Paint();
        mTextPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mTextPaint.setAntiAlias(true); mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setAntiAlias(true);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBackgroundBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        mBackgroundCanvas = new Canvas(mBackgroundBitmap);
        mTextBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_4444);
        mTextCanvas = new Canvas(mTextBitmap);
    }

    @Override protected void onDraw(Canvas canvas) {
        //这里给super传入的是mTextCanvas，把一些基本属性都支持进去
        super.onDraw(mTextCanvas);
        drawBackground(mBackgroundCanvas);

        canvas.saveLayer(0,0,getMeasuredWidth(),getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBackgroundBitmap,0,0,null);
        canvas.drawBitmap(mTextBitmap, 0, 0, mTextPaint);
        canvas.restore();
    }

    private void drawBackground(Canvas canvas){
        canvas.drawColor(mBackgroundColor);
    }
}
