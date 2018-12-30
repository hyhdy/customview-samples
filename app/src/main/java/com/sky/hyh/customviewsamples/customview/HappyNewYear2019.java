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
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.sky.hyh.customviewsamples.utils.DensityUtil;
import com.sky.hyh.customviewsamples.utils.TransformUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HappyNewYear2019 extends View {
    public static final int DURING_COUNT_DOWN_MILLIS = 1000;
    public static final double ALPHA_UN_TRANSPARENT = 255;
    public static final int SIZE_COUNT_DOWN_SP = 200;
    public static final String SYMBOL_SPLITE = ",";
    public static final int  TIMES_CYCLE = 10+1;

    public static final String COLOR_BUBBLE_TEXT = "#FFFDFA";
    public static final String COLOR_BG = "#66000000";
    public static final String NAME_FONT = "HYXinXiuTiW-2.ttf";

    private String mContent = "新,年,快,乐,2,0,1,9";
    private String[] mContentSegment;
    private int mCurIndex = -1;
    private int mCountDownNum = 10;
    private float mTextSizeRate;
    private float mTextAlphaRate;
    private Paint mCountDownPaint;
    private Paint mBubbleTextPaint;
    private float mSinWidth;

    private SpringSystem mSpringSystem;
    private SpringConfig mSpringConfig;
    private boolean mPlayCountDown = true;

    private Map<Integer, BubbleTextStruct> mBubbleMap;
    private Point[] mPoints;

    public HappyNewYear2019(Context context) {
        this(context, null);
    }

    public HappyNewYear2019(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        mBubbleMap = new HashMap<>();
        mCountDownPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCountDownPaint.setColor(Color.WHITE);
        mBubbleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubbleTextPaint.setColor(Color.parseColor(COLOR_BUBBLE_TEXT));
        mBubbleTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), NAME_FONT));
        mBubbleTextPaint.setTextSize(DensityUtil.sp2px(BubbleTextStruct.SIZE_BUBBLE_TEXT_SP));
        mSinWidth = mBubbleTextPaint.measureText("单");
        //mBubbleTextPaint.setShadowLayer(10,5,5,Color.parseColor("#FFFB5C"));
        spliteContent();
        startCountDownAnimation();
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
        spliteContent();
    }

    private void spliteContent() {
        if (TextUtils.isEmpty(mContent)) {
            return;
        }
        mContentSegment = mContent.split(SYMBOL_SPLITE);
    }

    private void startCountDownAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();

        Keyframe k0 = Keyframe.ofFloat(0f, 0);
        Keyframe k1 = Keyframe.ofFloat(0.8f, 1.2f);
        Keyframe k2 = Keyframe.ofFloat(1f, 1);
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofKeyframe("text_size", k0, k1, k2);

        Keyframe kA0 = Keyframe.ofFloat(0f, 0);
        Keyframe kA1 = Keyframe.ofFloat(0.8f, 1);
        Keyframe kA2 = Keyframe.ofFloat(1f, 0);
        PropertyValuesHolder valuesHolderA = PropertyValuesHolder.ofKeyframe("text_alpha", kA0, kA1, kA2);

        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, valuesHolder, valuesHolderA);
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
                    mCountDownNum--;
                    animator.cancel();
                }
            });

            animatorList.add(animator);
        }

        animatorSet.playTogether(animatorList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPlayCountDown = false;
                playBubbleAnim();
            }
        });
        animatorSet.start();
    }

    private Animator startBubbleEnterAnimSpring(int index,long startTime) {
        //spring动画
        if (mSpringSystem == null) {
            mSpringSystem = SpringSystem.create();
        }

        if (mSpringConfig == null) {
            mSpringConfig = new SpringConfig(300, 10);
        }

        final Spring animatorSpring = mSpringSystem.createSpring();
        BubbleTextStruct bubbleTextStruct = new BubbleTextStruct();
        bubbleTextStruct.text = getText();
        int cycleTimes = index / mContentSegment.length + 1;
        if(cycleTimes == TIMES_CYCLE){
            bubbleTextStruct.point = mPoints[index%mContentSegment.length];
            mBubbleMap.put(animatorSpring.hashCode(), bubbleTextStruct);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    animatorSpring.setSpringConfig(mSpringConfig);
                    animatorSpring.addListener(new SimpleSpringListener() {
                        @Override
                        public void onSpringUpdate(Spring spring) {
                            float springValue = (float) spring.getCurrentValue();
                            BubbleTextStruct struct = mBubbleMap.get(spring.hashCode());
                            struct.textSize = (int) (DensityUtil.sp2px(BubbleTextStruct.SIZE_BUBBLE_TEXT_SP) * springValue);
                            invalidate();
                        }

                        @Override
                        public void onSpringAtRest(Spring spring) {
                            spring.destroy();
                        }
                    });
                    animatorSpring.setEndValue(1);
                }
            }, startTime + 1600);

            return null;
        }else{
            int x = TransformUtils.getRangeRandomInt(0, getWidth());
            int y = TransformUtils.getRangeRandomInt(0, getHeight());
            bubbleTextStruct.point = new Point(x, y);
            mBubbleMap.put(animatorSpring.hashCode(), bubbleTextStruct);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    animatorSpring.setSpringConfig(mSpringConfig);
                    animatorSpring.addListener(new SimpleSpringListener() {
                        @Override
                        public void onSpringUpdate(Spring spring) {
                            float springValue = (float) spring.getCurrentValue();
                            BubbleTextStruct struct = mBubbleMap.get(spring.hashCode());
                            struct.textSize = (int) (DensityUtil.sp2px(BubbleTextStruct.SIZE_BUBBLE_TEXT_SP) * springValue);
                            invalidate();
                        }

                        @Override
                        public void onSpringAtRest(Spring spring) {
                            spring.destroy();
                        }
                    });
                    animatorSpring.setEndValue(1);
                }
            }, startTime);

            return buildBubbleLeaveAnim(startTime,animatorSpring);
        }
    }

    private Animator buildBubbleLeaveAnim(long startTime, final Spring spring){
        Keyframe k0 = Keyframe.ofFloat(0f, 1);
        Keyframe k1 = Keyframe.ofFloat(0.2f, 1.3f);
        Keyframe k2 = Keyframe.ofFloat(1.0f, 0);
        PropertyValuesHolder textSizeProperty = PropertyValuesHolder.ofKeyframe("text_size", k0,k1,k2);
        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, textSizeProperty);
        animator.setDuration(500);
        animator.setStartDelay(startTime+500+1000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BubbleTextStruct struct = mBubbleMap.get(spring.hashCode());
                struct.textSize = (int) (DensityUtil.sp2px(BubbleTextStruct.SIZE_BUBBLE_TEXT_SP) * (float)animation.getAnimatedValue("text_size"));
                invalidate();
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animator.cancel();
            }
        });
        return animator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            int perWidth = w/4;
            int lineCount = getLineCount();
            int sinHeight = mBubbleTextPaint.getFontMetricsInt(null);
            int startY = (h - sinHeight *lineCount)/2;
            mPoints = new Point[lineCount*4];
            int index = 0;
            for(int i=0;i<lineCount;i++){
                for(int j=0;j<4;j++){
                    int x = (int) (perWidth * j + (perWidth - mSinWidth)/2);
                    int y =  sinHeight * i + startY+getBaseLine();
                    mPoints[index++] = new Point(x,y);
                }
            }
        }
    }

    private int getBaseLine(){
        Paint.FontMetrics fontMetrics = mBubbleTextPaint.getFontMetrics();
        return (int) ((fontMetrics.descent - fontMetrics.ascent) - fontMetrics.descent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCountDownPaint.setAlpha((int) (mTextAlphaRate * ALPHA_UN_TRANSPARENT));
        mCountDownPaint.setTextSize(mTextSizeRate * DensityUtil.sp2px(SIZE_COUNT_DOWN_SP));
        String countDownStr = String.valueOf(mCountDownNum);
        float emojiWidth = mCountDownPaint.measureText(countDownStr);
        //计算绘制文本的基线
        float baseLineX = (getWidth() - emojiWidth) / 2;
        Paint.FontMetrics fontMetrics = mCountDownPaint.getFontMetrics();
        float baseLineY = getHeight() / 2 - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;

        canvas.drawColor(Color.parseColor(COLOR_BG));
        if (mPlayCountDown) {
            canvas.drawText(String.valueOf(mCountDownNum), baseLineX, baseLineY, mCountDownPaint);
        } else {
            for (BubbleTextStruct bubbleTextStruct : mBubbleMap.values()) {
                mBubbleTextPaint.setTextSize(bubbleTextStruct.textSize);
                canvas.drawText(bubbleTextStruct.text, bubbleTextStruct.point.x, bubbleTextStruct.point.y, mBubbleTextPaint);
            }
        }
    }

    private static class BubbleTextStruct {
        public static final int SIZE_BUBBLE_TEXT_SP = 80;
        private String text;
        private int textSize;
        private Point point;
    }

    private String getText() {
        if (TextUtils.isEmpty(mContent)) {
            return "";
        }
        mCurIndex++;
        return mContentSegment[mCurIndex % mContentSegment.length];
    }

    public void playBubbleAnim() {
        if(mContentSegment==null){
            return;
        }
        int totalTimes = mContentSegment.length * TIMES_CYCLE;
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorSetList = new ArrayList<>();
        Animator animator = startBubbleEnterAnimSpring(0,0);
        animatorSetList.add(animator);
        long sumTime = 0;
        double timeInternal = 80 ;
        for (int i = 1; i < totalTimes - mContentSegment.length; i++) {
            sumTime += timeInternal;
            animator = startBubbleEnterAnimSpring(i,sumTime);
            if(animator!=null) {
                animatorSetList.add(animator);
            }
        }

        for (int i = totalTimes - mContentSegment.length; i < totalTimes; i++) {
            sumTime += 4*timeInternal;
            animator = startBubbleEnterAnimSpring(i,sumTime);
            if(animator!=null) {
                animatorSetList.add(animator);
            }
        }

        animatorSet.playTogether(animatorSetList);
        animatorSet.start();
    }

    private int getLineCount(){
        if(mContentSegment == null){
            return 0;
        }
        double line = mContentSegment.length/(4*1d);
        return (int) Math.ceil(line);
    }
}
