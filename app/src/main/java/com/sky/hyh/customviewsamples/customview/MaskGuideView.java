package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyh.base_lib.utils.SizeUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by curdyhuang on 2019-10-21
 * 蒙层引导控件
 */
public class MaskGuideView extends View {
    private static final int DEF_MASK_COLOR = 0x99000000;
    private static final int DEF_MARGIN = 10;
    private static final int DEF_ROUND_RADIUS = 5;

    private static final int SHAPE_ROUND_RECT = 0;
    private static final int SHAPE_CIRCLE = 1;
    /**
     * 定义高亮区域形状的常量值，目前只支持圆角和圆形，可自行扩展
     */
    @IntDef({SHAPE_ROUND_RECT,SHAPE_CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightAreaShape{}

    /**
     * 蒙层颜色
     */
    private int mMaskColor = DEF_MASK_COLOR;
    private int mMargin;
    private int mRoundRadius;
    private ShapeParam mShapeParam;

    private PorterDuffXfermode mPorterDuffXfermode;
    private Paint mPaint;

    private OnTouchCallBack mOnTouchCallBack;

    public MaskGuideView(Context context) {
        this(context,null);
    }

    public MaskGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMargin = SizeUtils.dp2px(DEF_MARGIN);
        mRoundRadius = SizeUtils.dp2px(DEF_ROUND_RADIUS);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    public void setMaskColor(int maskColor) {
        mMaskColor = maskColor;
    }

    public void setMargin(int margin) {
        mMargin = margin;
    }

    public void setRoundRadius(int roundRadius) {
        mRoundRadius = roundRadius;
    }

    public void attachTarget(View targetView){
        attachTarget(targetView,SHAPE_ROUND_RECT);
    }

    public void attachTarget(View targetView,@LightAreaShape int shape){
        if(targetView == null){
            return;
        }
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int[] guideLocation= new int[2];
        getLocationOnScreen(guideLocation);
        location[0] -= guideLocation[0];
        location[1] -= guideLocation[1];
        buildShapeParam(location,targetView,shape);
        invalidate();
    }

    private void buildShapeParam(int[] location,View targetView,@LightAreaShape int shape){
        mShapeParam = new ShapeParam(shape);
        if(shape == SHAPE_ROUND_RECT){
            RectF rectF = new RectF();
            rectF.left = location[0] - mMargin;
            rectF.top = location[1] - mMargin;
            rectF.right = location[0] + targetView.getWidth() + mMargin;
            rectF.bottom = location[1] + targetView.getHeight() + mMargin;
            //圆角矩形区域
            mShapeParam.mRectF = rectF;
            mShapeParam.mRoundRadius = mRoundRadius;
        }else if(shape == SHAPE_CIRCLE){
            //圆心点坐标
            mShapeParam.mCenterX = location[0] + targetView.getWidth()/2;
            mShapeParam.mCenterY = location[1] + targetView.getHeight()/2;
            //圆的半径，这里算的是目标控件矩形区域对角线的长度一半加上margin的值作为半径
            mShapeParam.mRadius = (int) (Math.sqrt(Math.pow(targetView.getWidth()/2,2)+Math.pow(targetView.getHeight()/2,2))+mMargin);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //需要在新的图层上绘制，绘制完后再再叠加到旧的图层上。
        canvas.saveLayer(0,0,getWidth(),getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //绘制蒙层背景
        canvas.drawColor(mMaskColor);
        //绘制高亮区域
        if(mShapeParam!=null){
            mPaint.setXfermode(mPorterDuffXfermode);
            int shape = mShapeParam.mShape;
            if(shape == SHAPE_ROUND_RECT){
                //绘制圆角矩形高亮区域
                canvas.drawRoundRect(mShapeParam.mRectF,mShapeParam.mRoundRadius,mShapeParam.mRoundRadius,mPaint);
            }else if(shape == SHAPE_CIRCLE){
                //绘制圆形高亮区域
                canvas.drawCircle(mShapeParam.mCenterX,mShapeParam.mCenterY,mShapeParam.mRadius,mPaint);
            }
        }
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(mOnTouchCallBack!=null){
                mOnTouchCallBack.onTouch();
            }
        }
        return true;
    }

    private class ShapeParam{
        @LightAreaShape
        private int mShape = SHAPE_ROUND_RECT;
        private int mRadius;
        private int mCenterX;
        private int mCenterY;
        private RectF mRectF;
        private int mRoundRadius;

        public ShapeParam(@LightAreaShape int mShape) {
            this.mShape = mShape;
        }
    }

    public void setOnTouchCallBack(OnTouchCallBack onTouchCallBack) {
        mOnTouchCallBack = onTouchCallBack;
    }

    public interface OnTouchCallBack{
        void onTouch();
    }
}
