package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sky.hyh.customviewsamples.R;

import java.util.Arrays;

/**
 * Created by hyh on 2018/10/27 21:15
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ColorMatrixView extends View {
    private int mColor = 0xff25a600;
    private Paint mPaint;
    private float[] mColorMatrixArray;
    private Bitmap mBitmap;
    private Matrix mMatrix;//单位矩阵

    public ColorMatrixView(Context context) {
        this(context,null);
    }

    public ColorMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mColorMatrixArray = new float[]{
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0};
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camera_edit_addtext_fontfamily_mountains_bg);
        mMatrix = new Matrix();
        mPaint = new Paint();
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int[] argb = parseRgb(mColor);
        Log.d("hyh", "ColorMatrixFragment: onStopTrackingTouch: argb="+ Arrays.toString(argb));

        // 根据SeekBar定义RGBA的矩阵, 通过修改矩阵第五列颜色的偏移量改变图片的颜色
        mColorMatrixArray[4] = argb[0];
        mColorMatrixArray[9] = argb[1];
        mColorMatrixArray[14] = argb[2];
        Log.d("hyh", "ColorMatrixView: onDraw: mColorMatrixArray="+Arrays.toString(mColorMatrixArray));

        // 4.使用ColorMatrix创建一个ColorMatrixColorFilter对象, 作为画笔的滤镜, 设置Paint的颜色
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrixArray));

        // 5.通过指定了RGBA矩阵的Paint把原图画到空白图片上
        canvas.drawBitmap(mBitmap ,mMatrix ,mPaint);
    }

    public int[] parseRgb(int color) {
        int[] colors = new int[3];
        colors[0] =   Color.red(color);
        colors[1] =   Color.green(color);
        colors[2] =   Color.blue(color);
        return colors;
    }
}
