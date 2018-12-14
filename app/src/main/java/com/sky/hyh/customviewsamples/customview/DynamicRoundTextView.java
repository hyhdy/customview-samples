package com.sky.hyh.customviewsamples.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.sky.hyh.customviewsamples.utils.DensityUtil;

/**
 * Created by hyh on 2018/9/11 17:48
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class DynamicRoundTextView extends AppCompatTextView {
    public static final int TIME_DURING_SPREAD_TO_CLOSE = 300;
    public static final int TIME_DURING_CLOSE_TO_DISAPPEAR = 300;
    public static final int STATE_SPREAD = 1;
    public static final int STATE_NORMAL = 2;
    public static final int STATE_DISAPPEAR = 3;

    private int mWidthClose;
    private float mSpreadWidth;
    private int mUnReadCount;
    private String mTips;
    private int mState = STATE_NORMAL;

    private Spring mSpring;

    public DynamicRoundTextView(Context context) {
        this(context,null);
    }

    public DynamicRoundTextView(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("hyh", "DynamicRoundTextView: onSizeChanged w="+w+" ,oldw="+oldw);
        if(oldw == 0){
            mWidthClose = w;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        Log.d("hyh", "DynamicRoundTextView: onMeasure widthMode="+widthMode+" ,widthSize="+widthSize);
    }

    /**
     * 展开
     */
    public void spread(){
        if(mState == STATE_NORMAL) {
            updateContent();
            buildSpreadAnimSpring();
            mState = STATE_SPREAD;
        }else if(mState == STATE_DISAPPEAR){
            appear(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    spread();
                    mState = STATE_SPREAD;
                }
            });
        }
    }

    /**
     * 缩回
     */
    public void close(){
        if(mState == STATE_SPREAD) {
            Animator spreadToCloseAnimator = buildCloseAnimator();
            Animator closeToDisappearAnimator = buildDisappearAnimator();

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(spreadToCloseAnimator).before(closeToDisappearAnimator);

            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.GONE);
                }
            });
            mState = STATE_DISAPPEAR;
        }else if(mState == STATE_NORMAL){
            Animator animator = buildDisappearAnimator();
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setVisibility(View.GONE);
                }
            });
            mState = STATE_DISAPPEAR;
        }

    }

    /**
     * 出现
     */
    public void appear(AnimatorListenerAdapter animatorListenerAdapter){
        if(mState == STATE_DISAPPEAR) {
            setVisibility(View.VISIBLE);
            Animator animator = buildAppearAnimator();
            if(animatorListenerAdapter!=null){
                animator.addListener(animatorListenerAdapter);
            }
            animator.start();
            mState = STATE_NORMAL;
        }
    }

    private void initContent(){
        String text = String.valueOf(mUnReadCount);
        if(mUnReadCount>99){
            text = "99+";
        }
        setText(text);
    }

    private void updateContent() {
        if(TextUtils.isEmpty(mTips)){
            mTips = "";
            mSpreadWidth = mWidthClose;
        }else{
            TextPaint textPaint = getPaint();
            mSpreadWidth = textPaint.measureText(mTips)+DensityUtil.dip2px(30);
        }
        setText(mTips);
    }

    private void buildSpreadAnimSpring(){
        SpringSystem springSystem = SpringSystem.create();
        mSpring = springSystem.createSpring();
        mSpring.setSpringConfig(new SpringConfig(150,10));
        mSpring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                int value = (int) spring.getCurrentValue();
                getLayoutParams().width = value;
                requestLayout();
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
        mSpring.setCurrentValue(getMeasuredWidth());
        mSpring.setEndValue(mSpreadWidth);
    }

    private Animator buildCloseAnimator(){
        if(mSpring!=null&&!mSpring.isAtRest()){
            mSpring.setAtRest();
        }
        int widthClose = mWidthClose;
        int widthSpread = (int) mSpreadWidth;

        ValueAnimator widthAnimator = ValueAnimator.ofInt(widthSpread,widthClose);
        widthAnimator.setDuration(TIME_DURING_SPREAD_TO_CLOSE);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                getLayoutParams().width = value;
                requestLayout();
            }
        });
        widthAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                initContent();
            }
        });

        return widthAnimator;
    }

    private Animator buildDisappearAnimator(){
        Keyframe k0 = Keyframe.ofFloat(0f, 1);
        Keyframe k1 = Keyframe.ofFloat(0.5f, 0.5f);
        Keyframe k2 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder widthValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X, k0,k1,k2);
        PropertyValuesHolder heightValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, k0,k1,k2);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this,widthValuesHolder,heightValuesHolder);
        animator.setDuration(TIME_DURING_CLOSE_TO_DISAPPEAR);
        return animator;
    }

    private Animator buildAppearAnimator(){
        Keyframe k0 = Keyframe.ofFloat(0f, 0);
        Keyframe k1 = Keyframe.ofFloat(0.5f, 0.5f);
        Keyframe k2 = Keyframe.ofFloat(1f, 1);
        PropertyValuesHolder widthValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X, k0,k1,k2);
        PropertyValuesHolder heightValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, k0,k1,k2);

        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this,widthValuesHolder,heightValuesHolder);
        animator.setDuration(TIME_DURING_CLOSE_TO_DISAPPEAR);
        return animator;
    }

    public void setUnReadCount(int unReadCount) {
        mUnReadCount = unReadCount;
        initContent();
    }

    public void setTips(String tips) {
        mTips = tips;
    }
}
