package com.sky.hyh.customviewsamples.customview;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sky.hyh.customviewsamples.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class HappyNewYear2009 extends View {
    public static final int DURING_COUNT_DOWN_MILLIS = 1000;
    public static final double ALPHA_UN_TRANSPARENT = 255;
    public static final int SIZE_COUNT_DOWN_SP = 200;

    private String mContent;
    private int mCountDownNum = 10;
    private float mTextSizeRate;
    private float mTextAlphaRate;
    private Paint mCountDownPaint;

    public HappyNewYear2009(Context context) {
        this(context, null);
    }

    public HappyNewYear2009(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        mCountDownPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCountDownPaint.setColor(Color.WHITE);
        startCountDownAnimation();
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    private void startCountDownAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();

        Keyframe k0 = Keyframe.ofFloat(0f, 0);
        Keyframe k1 = Keyframe.ofFloat(0.8f, 1.2f);
        Keyframe k2 = Keyframe.ofFloat(1f, 1);
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("text_size",k0,k1,k2);

        Keyframe kA0 = Keyframe.ofFloat(0f, 0);
        Keyframe kA1 = Keyframe.ofFloat(0.8f, 1);
        Keyframe kA2 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder valuesHolderA = PropertyValuesHolder.ofKeyframe("text_alpha",kA0,kA1,kA2);

        List<Animator> animatorList = new ArrayList<>();
        for(int i=0;i<10;i++){
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this,valuesHolder,valuesHolderA);
            animator.setDuration(DURING_COUNT_DOWN_MILLIS);
            animator.setStartDelay(DURING_COUNT_DOWN_MILLIS * i);
            //animator.setInterpolator(new SpringInterpolator(0.8f));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mTextSizeRate = (float) animation.getAnimatedValue("text_size");
                    mTextAlphaRate = (float) animation.getAnimatedValue("text_alpha");
                    invalidate();
                }
            });

            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCountDownNum --;
                }
            });

            animatorList.add(animator);
        }

        animatorSet.playTogether(animatorList);
        animatorSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCountDownPaint.setAlpha((int) (mTextAlphaRate * ALPHA_UN_TRANSPARENT));
        mCountDownPaint.setTextSize(mTextSizeRate * DensityUtil.sp2px(SIZE_COUNT_DOWN_SP));
        String countDownStr = String.valueOf(mCountDownNum);
        float emojiWidth = mCountDownPaint.measureText(countDownStr);
        //计算绘制文本的基线
        float baseLineX = (getWidth() - emojiWidth)/2;
        Paint.FontMetrics fontMetrics = mCountDownPaint.getFontMetrics();
        float baseLineY = getHeight()/2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;

        canvas.drawColor(0x80000000);
        canvas.drawText(String.valueOf(mCountDownNum),baseLineX,baseLineY,mCountDownPaint);
    }
}
