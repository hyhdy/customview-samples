package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.hyh.base_lib.utils.SizeUtils;

/**
 * Created by hyh on 2018/12/26 21:56
 * E-Mail Address：fjnuhyh122@gmail.com
 * 思路：获取text的path，再以strokePaint根据path来绘制文本作为描边背景
 */
public class StrokeTextView extends AppCompatTextView{
    public static final int EXTRA_SPACE = 10;
    /**
     * 描边颜色
     */
    private int mBorderColor =  Color.WHITE;
    private int mStrokeWidth = 8;

    private int mTextWidth;
    private int mMaxWidth = Integer.MAX_VALUE;

    private Paint mBorderPaint;

    public StrokeTextView(Context context) {
        this(context,null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(SizeUtils.dp2px(mStrokeWidth));
        //设置path的连接处为圆弧，实现光滑描边
        mBorderPaint.setStrokeJoin(Paint.Join.ROUND);
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
            int width = mTextWidth + getPaddingLeft() + getPaddingRight() + SizeUtils.dp2px(EXTRA_SPACE);
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
        drawStrokeBorder(canvas);
        super.onDraw(canvas);
    }

    public void setColor(int borderColor,int textColor){
        setTextColor(textColor);
        mBorderColor = borderColor;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        mStrokeWidth = strokeWidth;
    }

    private void drawStrokeBorder(Canvas canvas){
        Layout layout = getLayout();
        if(layout!=null){
            String text = getText().toString();
            int lineCount = getLineCount();
            //第一行文字顶部与控件顶部的距离
            int totalPaddingTop = getTotalPaddingTop();
            for (int i = 0; i < lineCount; i++) {
                int start = layout.getLineStart(i);
                int end = layout.getLineEnd(i);
                String rowStr = text.substring(start, end);

                float x = layout.getLineLeft(i)+getPaddingLeft();
                int y = totalPaddingTop + getBaseLine(i);

                Path textPath = new Path();
                getPaint().getTextPath(rowStr,0,rowStr.length(),x,y,textPath);
                mBorderPaint.setColor(mBorderColor);
                canvas.drawPath(textPath,mBorderPaint);
            }
        }
    }

    private int getBaseLine(int line){
        int lbottom = getLayout().getLineTop(line + 1);
        int lbaseline = lbottom - getLayout().getLineDescent(line);
        return lbaseline;
    }
}
