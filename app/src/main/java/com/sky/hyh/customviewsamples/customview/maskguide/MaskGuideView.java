package com.sky.hyh.customviewsamples.customview.maskguide;

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
    private static final int SHAPE_ROUND_RECT = 0;
    private static final int SHAPE_CIRCLE = 1;
    /**
     * 定义高亮区域形状的常量值，目前只支持圆角和圆形，可自行扩展
     */
    @IntDef({SHAPE_ROUND_RECT,SHAPE_CIRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LightAreaShape{}

    private PorterDuffXfermode mPorterDuffXfermode;
    private Paint mPaint;

    private boolean mIsFreeze;
    private int mMaskColor;
    private int mMargin;
    private float mRoundRadius;
    private int mShape;
    private OnTouchCallBack mOnTouchCallBack;
    private ShapeParam mShapeParam;

    public MaskGuideView(Context context) {
        this(context,null);
    }

    public MaskGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    public MaskGuideView(Builder builder) {
        this(builder.mContext);
        mIsFreeze = builder.mIsFreeze;
        mMaskColor = builder.mMaskColor;
        mShape = builder.mShape;
        mRoundRadius = builder.mRoundRadius;
        mMargin = builder.mMargin;
        mOnTouchCallBack = builder.mOnTouchCallBack;
    }

    public void attachTarget(View targetView){
        if(targetView == null){
            return;
        }
        int[] location = new int[2];
        targetView.getLocationOnScreen(location);
        int[] guideLocation= new int[2];
        getLocationOnScreen(guideLocation);
        location[0] -= guideLocation[0];
        location[1] -= guideLocation[1];
        buildShapeParam(location,targetView,mShape);
        invalidate();
    }

    private void buildShapeParam(int[] location, View targetView, @LightAreaShape int shape){
        mShapeParam = new ShapeParam(shape);
        if(shape == SHAPE_ROUND_RECT){
            //高亮矩形区域
            RectF rectF = new RectF();
            rectF.left = location[0] - mMargin;
            rectF.top = location[1] - mMargin;
            rectF.right = location[0] + targetView.getWidth() + mMargin;
            rectF.bottom = location[1] + targetView.getHeight() + mMargin;

            mShapeParam.mRectF = rectF;
            mShapeParam.mRoundRadius = mRoundRadius;
        }else if(shape == SHAPE_CIRCLE){
            //圆心点坐标
            int centerX = location[0] + targetView.getWidth()/2;
            int centerY = location[1] + targetView.getHeight()/2;
            //圆的半径，这里算的是目标控件矩形区域对角线的长度一半加上margin的值作为半径
            float radius = (float) (Math.sqrt(Math.pow(targetView.getWidth()/2,2)+ Math.pow(targetView.getHeight()/2,2))+mMargin);
            //高亮矩形区域，这里算的是圆外切矩形
            RectF rectF = new RectF(centerX - radius,centerY-radius,centerX+radius,centerY+radius);

            mShapeParam.mCenterX = centerX;
            mShapeParam.mCenterY = centerY;
            mShapeParam.mRadius = radius;
            mShapeParam.mRectF = rectF;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mShapeParam!=null){
            //需要在新的图层上绘制，绘制完后再再叠加到旧的图层上。
            canvas.saveLayer(0,0,getWidth(),getHeight(), null, Canvas.ALL_SAVE_FLAG);
            //绘制蒙层背景
            canvas.drawColor(mMaskColor);
            //绘制高亮区域
            mPaint.setXfermode(mPorterDuffXfermode);
            int shape = mShapeParam.mShape;
            if(shape == SHAPE_ROUND_RECT){
                //绘制圆角矩形高亮区域
                canvas.drawRoundRect(mShapeParam.mRectF,mShapeParam.mRoundRadius,mShapeParam.mRoundRadius,mPaint);
            }else if(shape == SHAPE_CIRCLE){
                //绘制圆形高亮区域
                canvas.drawCircle(mShapeParam.mCenterX,mShapeParam.mCenterY,mShapeParam.mRadius,mPaint);
            }
            canvas.restore();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(mShapeParam==null){
            return super.dispatchTouchEvent(event);
        }else {
            boolean isIntercept = true;//是否拦截事件
            boolean isRespond = true;//是否响应回调
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //判断点击区域是否在高亮区域
                if (mShapeParam != null && mShapeParam.mRectF.contains(event.getX(), event.getY())) {
                    //点击高亮区域不拦截事件
                    isIntercept = false;
                } else if (mIsFreeze) {
                    //冻结界面不响应点击
                    isRespond = false;
                }
                if (isRespond) {
                    if (mOnTouchCallBack != null) {
                        mOnTouchCallBack.onTouch();
                        clear();
                    }
                }
            }

            return isIntercept || super.dispatchTouchEvent(event);
        }
    }

    private class ShapeParam{
        @LightAreaShape
        private int mShape;
        private float mRadius;//圆形半径
        private int mCenterX;//圆形中心点x坐标
        private int mCenterY;//圆形中心点y坐标
        private RectF mRectF = new RectF();//高亮区域
        private float mRoundRadius;//圆角矩形半径

        public ShapeParam(@LightAreaShape int mShape) {
            this.mShape = mShape;
        }
    }

    public interface OnTouchCallBack{
        void onTouch();
    }

    public void clear(){
        mShapeParam = null;
    }

    /**
     * 通过建造者实例化对象
     */
    public static class Builder{
        private static final int DEF_MASK_COLOR = 0x99000000;
        private static final int DEF_MARGIN = 5;
        private static final int DEF_ROUND_RADIUS = 5;

        private Context mContext;
        /**
         * 是否冻结界面，冻结模式下点击蒙层不会有响应
         */
        private boolean mIsFreeze;
        /**
         * 蒙层颜色
         */
        private int mMaskColor = DEF_MASK_COLOR;
        /**
         * 高亮区域形状
         */
        @LightAreaShape
        private int mShape = SHAPE_ROUND_RECT;
        /**
         * 圆角矩形半径
         */
        private float mRoundRadius;
        private int mMargin;
        private OnTouchCallBack mOnTouchCallBack;

        public Builder(Context context) {
            mContext = context;
            mRoundRadius = SizeUtils.dp2px(DEF_ROUND_RADIUS);
            mMargin = SizeUtils.dp2px(DEF_MARGIN);
        }

        public Builder setFreeze(boolean freeze) {
            mIsFreeze = freeze;
            return this;
        }

        public Builder setMaskColor(int maskColor) {
            mMaskColor = maskColor;
            return this;
        }

        public Builder setShape(@LightAreaShape int shape) {
            mShape = shape;
            return this;
        }

        public Builder setRoundRadius(float roundRadius) {
            mRoundRadius = roundRadius;
            return this;
        }

        public Builder setMargin(int margin) {
            mMargin = margin;
            return this;
        }

        public Builder setOnTouchCallBack(OnTouchCallBack onTouchCallBack) {
            mOnTouchCallBack = onTouchCallBack;
            return this;
        }

        public MaskGuideView build(){
            return new MaskGuideView(this);
        }
    }
}
