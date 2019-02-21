package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.sky.hyh.customviewsamples.utils.DensityUtil;

/**
 * Created by hyh on 2018/12/24 15:27
 * E-Mail Address：fjnuhyh122@gmail.com
 * 描边文字的简单实现
 * 实现思路：先把textview的paint的style设置为stroke绘制文字，作为描边背景，然后再把textview的paint的style设置为fill再叠加绘制一遍文字。
 */
public class StrokeTextViewSimple extends AppCompatTextView {
    public static final int EXTRA_SPACE = 10;
    /**
     * 描边颜色
     */
    private int mBorderColor =  Color.WHITE;
    /**
     * 文字颜色
     */
    private int mTextColor = 0xfffeada7;
    private int mStrokeWidth = 8;

    private int mTextWidth;
    private int mMaxWidth = Integer.MAX_VALUE;

    public StrokeTextViewSimple(Context context) {
        this(context,null);
    }

    public StrokeTextViewSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTextWidth = (int) getPaint().measureText(getText().toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //当宽度是wrap_content时需要适当增加控件宽度，不然描边左右两边会被截断一小部分
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            if(widthMode != MeasureSpec.EXACTLY){
                //获得屏幕宽度
                mMaxWidth = MeasureSpec.getSize(widthMeasureSpec);
            }
            int width = mTextWidth + getPaddingLeft() + getPaddingRight() + DensityUtil.dp2px(EXTRA_SPACE);
            if(width > mMaxWidth){
                //控件宽度不能大于屏幕宽度
                width = mMaxWidth;
            }
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().setStyle(Paint.Style.STROKE);
        getPaint().setStrokeWidth(DensityUtil.dp2px(mStrokeWidth));
        setTextColor(mBorderColor);
        super.onDraw(canvas);
        getPaint().setStyle(Paint.Style.FILL);
        setTextColor(mTextColor);
        super.onDraw(canvas);
    }

    public void setColor(int borderColor,int textColor){
        mBorderColor = borderColor;
        mTextColor = textColor;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
    }
}
