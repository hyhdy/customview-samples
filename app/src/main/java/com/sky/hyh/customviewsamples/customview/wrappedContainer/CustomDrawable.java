package com.sky.hyh.customviewsamples.customview.wrappedContainer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.sky.hyh.customviewsamples.R;

/**
 * Created by hyh on 2019/3/18 14:59
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class CustomDrawable extends Drawable {
    private int mX;
    private int mY;
    private final Rect mDstRect = new Rect();
    private Paint mPaint;
    private Bitmap mBitmap;
    private Context mContext;

    public CustomDrawable(Context context) {
        mContext = context;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.p1);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        updateDstRect();
        canvas.drawBitmap(mBitmap, null, mDstRect, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
        setBounds(mX,mY,mX+getIntrinsicWidth(),mY+getIntrinsicHeight());
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
        setBounds(mX,mY,mX+getIntrinsicWidth(),mY+getIntrinsicHeight());
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }

    private void updateDstRect(){
        copyBounds(mDstRect);
    }
}
