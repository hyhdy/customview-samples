package com.sky.hyh.customviewsamples.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sky.hyh.customviewsamples.utils.SwayTwoLayoutAnimHolder.Direction.mid;
import static com.sky.hyh.customviewsamples.utils.SwayTwoLayoutAnimHolder.Direction.left;
import static com.sky.hyh.customviewsamples.utils.SwayTwoLayoutAnimHolder.Direction.right;

/**
 * created by curdyhuang on 2021/11/22
 * 分屏摆动动画处理类
 */
public class SwayTwoLayoutAnimHolder {
    public static final float PET_LARGE = 8f;//大屏的屏幕占比
    public static final float PET_SMALL = 5f;//小屏的屏幕占比

    public static final int DURATION= 1000;//动画时间，毫秒

    @IntDef({mid, left, right})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction{
        int mid = 0;//朝中间移动
        int left = 1;//朝左边移动
        int right = 2;//朝右边移动
    }
    private @Direction int mOldDirection;//记录移动方向
    private @Direction int mPosition = mid;//记录初始位置

    private ValueAnimator mAnimator;
    private AnimCallBack mAnimCallBack;
    private float mLastLeftTransX;
    private float mLastRightTransX;
    private float mLastMidTransX;

    public SwayTwoLayoutAnimHolder(AnimCallBack animCallBack) {
        mAnimCallBack = animCallBack;
    }

    /**
     * 播放动画
     * @param direction：动画移动方向
     * @param totalWidth：双屏总宽度
     * @param height：高度
     */
    public void startAnim(@Direction int direction,int totalWidth, int height){
        Log.d("hyh","SwayTwoLayoutAnimHolder: startAnim: mPosition="+mPosition+" ,mOldDirection="+mOldDirection+" ,direction="+direction+" ,totalWidth="+totalWidth+" ,height="+height);

        int leftInitW = 0;
        int leftInitH = 0;
        int leftTargetW = 0;
        int leftTargetH = 0;
        int rightInitW = 0;
        int rightInitH = 0;
        int rightTargetW = 0;
        int rightTargetH = 0;

        float ratio = PET_LARGE /(PET_LARGE + PET_SMALL);//分屏调整比例
        Log.d("hyh","SwayTwoLayoutAnimHolder: startAnim: ratio="+ratio);
        int halfWidth = (int) (totalWidth * 0.5f);
        int largetWidth = (int) (totalWidth * ratio);
        int largetHeight = (int) (largetWidth / (halfWidth*1f/height));

        if(mAnimator != null&&mAnimator.isRunning()){
            //动画执行过程中开始新的动画
            if (mOldDirection == direction){
                //新动画的方向和原本的运动方向一致则不处理
            }else{
                //动画方向发生改变
                if (direction == mid){
                    //往中间移动，左右两边视图尺寸都调整成原始大小
                    leftInitW = (int) mAnimator.getAnimatedValue("vlw");
                    leftInitH = (int) mAnimator.getAnimatedValue("vlh");
                    leftTargetW = halfWidth;
                    leftTargetH = height;
                    rightInitW = (int) mAnimator.getAnimatedValue("vrw");
                    rightInitH = (int) mAnimator.getAnimatedValue("vrh");
                    rightTargetW = halfWidth;
                    rightTargetH = height;
                }else if (direction == left){
                    //往左移动，左边视图尺寸调整成原始大小，右边视图尺寸放大
                    leftInitW = (int) mAnimator.getAnimatedValue("vlw");
                    leftInitH = (int) mAnimator.getAnimatedValue("vlh");
                    leftTargetW = halfWidth;
                    leftTargetH = height;
                    rightInitW = (int) mAnimator.getAnimatedValue("vrw");
                    rightInitH = (int) mAnimator.getAnimatedValue("vrh");
                    rightTargetW = largetWidth;
                    rightTargetH = largetHeight;
                } else if (direction == right){
                    //往右移动，左边视图尺寸放大，右边视图尺寸调整成原始大小
                    leftInitW = (int) mAnimator.getAnimatedValue("vlw");
                    leftInitH = (int) mAnimator.getAnimatedValue("vlh");
                    leftTargetW = largetWidth;
                    leftTargetH = largetHeight;
                    rightInitW = (int) mAnimator.getAnimatedValue("vrw");
                    rightInitH = (int) mAnimator.getAnimatedValue("vrh");
                    rightTargetW = halfWidth;
                    rightTargetH = height;
                }
                //取消当前动画
                mAnimator.cancel();
            }
        }else {
            if(mPosition == direction){
                //已处于目标位置，不处理
                Log.d("hyh","SwayTwoLayoutAnimHolder: startAnim: 已处于目标位置，不处理");
                return;
            }

            if (mPosition == mid) {
                if (direction == right) {
                    //由中间往右移动，左边视图尺寸放大，右边视图尺寸不变
                    leftInitW = halfWidth;
                    leftInitH = height;
                    leftTargetW = largetWidth;
                    leftTargetH = largetHeight;

                    rightInitW = halfWidth;
                    rightInitH = height;
                    rightTargetW = rightInitW;
                    rightTargetH = rightInitH;
                } else if (direction == left) {
                    //由中间往左边移动，左边视图尺寸不变，右边视图尺寸放大
                    leftInitW = halfWidth;
                    leftInitH = height;
                    leftTargetW = leftInitW;
                    leftTargetH = leftInitH;

                    rightInitW = halfWidth;
                    rightInitH = height;
                    rightTargetW = largetWidth;
                    rightTargetH = largetHeight;
                }
            } else if (mPosition == left) {
                if (direction == right) {
                    //由左边往右移动，左边视图尺寸放大，右边视图尺寸缩小
                    leftInitW = halfWidth;
                    leftInitH = height;
                    leftTargetW = largetWidth;
                    leftTargetH = largetHeight;

                    rightInitW = largetWidth;
                    rightInitH = largetHeight;
                    rightTargetW = halfWidth;
                    rightTargetH = height;
                } else if (direction == mid) {
                    //由左边往中间移动，左边视图尺寸不变，右边视图尺寸缩小
                    leftInitW = halfWidth;
                    leftInitH = height;
                    leftTargetW = leftInitW;
                    leftTargetH = leftInitH;

                    rightInitW = largetWidth;
                    rightInitH = largetHeight;
                    rightTargetW = halfWidth;
                    rightTargetH = height;
                }
            } else if (mPosition == right) {
                if (direction == mid) {
                    //由右边往中间移动，左边视图尺寸缩小，右边视图尺寸不变
                    leftInitW = largetWidth;
                    leftInitH = largetHeight;
                    leftTargetW = halfWidth;
                    leftTargetH = height;

                    rightInitW = halfWidth;
                    rightInitH = height;
                    rightTargetW = rightInitW;
                    rightTargetH = rightInitH;
                } else if (direction == left) {
                    //由右边往左边移动，左边视图尺寸缩小，右边视图尺寸放大
                    leftInitW = largetWidth;
                    leftInitH = largetHeight;
                    leftTargetW = halfWidth;
                    leftTargetH = height;

                    rightInitW = halfWidth;
                    rightInitH = height;
                    rightTargetW = largetWidth;
                    rightTargetH = largetHeight;
                }
            }
        }

        if(leftInitW==0||leftInitH==0||leftTargetW==0||leftTargetH==0
            ||rightInitW==0||rightInitH==0||rightTargetW==0||rightTargetH==0){
            //出现异常，不做动画
            Log.d("hyh","SwayTwoLayoutAnimHolder: startAnim: 出现异常，不做动画");
            return;
        }

        Log.d("hyh","SwayTwoLayoutAnimHolder: startAnim: leftInitW="+leftInitW+" ,leftInitH="+leftInitH
        +" ,leftTargetW="+leftTargetW+" ,leftTargetH="+leftTargetH+" ,rightInitW="+rightInitW+" ,rightInitH="+rightInitH
        +" ,rightTargetW="+rightTargetW+" ,rightTargetH="+rightTargetH);

        PropertyValuesHolder vlw = PropertyValuesHolder.ofInt("vlw",leftInitW,leftTargetW);
        PropertyValuesHolder vlh = PropertyValuesHolder.ofInt("vlh",leftInitH,leftTargetH);
        PropertyValuesHolder vrw = PropertyValuesHolder.ofInt("vrw",rightInitW,rightTargetW);
        PropertyValuesHolder vrh = PropertyValuesHolder.ofInt("vrh",rightInitH,rightTargetH);

        mAnimator = ValueAnimator.ofPropertyValuesHolder(vlw,vlh,vrw,vrh);
        float finalLeftInitW = leftInitW;
        float finalRightInitW = rightInitW;
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int lw = (int) animation.getAnimatedValue("vlw");
                int lh = (int) animation.getAnimatedValue("vlh");
                int rw = (int) animation.getAnimatedValue("vrw");
                int rh = (int) animation.getAnimatedValue("vrh");
                Log.d("hyh","SwayTwoLayoutAnimHolder: onAnimationUpdate: lw="+lw+" ,lh="+lh+" ,rw="+rw+" ,rh="+rh);

                float lTransX = -(rw - finalRightInitW);
                float rTransX = lw - finalLeftInitW;
                float midTransX = lTransX+rTransX;
                Log.d("hyh","SwayTwoLayoutAnimHolder: onAnimationUpdate: lTransX="+lTransX+" ,rTransX="+rTransX+" ,midTransX="+midTransX);

                float lTransXOffset = lTransX - mLastLeftTransX;//左边视图偏移量
                float rTransXOffset = rTransX - mLastRightTransX;//右边视图偏移量
                float midTransXOffset = midTransX - mLastMidTransX;//中间分割线偏移量
                Log.d("hyh","SwayTwoLayoutAnimHolder: onAnimationUpdate: lTransXOffset="+lTransXOffset+" ,rTransXOffset="+rTransXOffset+" ,midTransXOffset="+midTransXOffset);

                mLastLeftTransX = lTransX;
                mLastRightTransX = rTransX;
                mLastMidTransX = midTransX;

                //将数值回调给上层
                if(mAnimCallBack!=null){
                    mAnimCallBack.onUpdate(lw,lh,rw,rh,lTransXOffset,rTransXOffset,midTransXOffset);
                }
            }
        });

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mLastLeftTransX = 0;
                mLastRightTransX = 0;
                mLastMidTransX = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("hyh","SwayTwoLayoutAnimHolder: onAnimationEnd: ");
                //动画结束后更新位置
                mPosition = direction;
            }
        });

        mAnimator.setDuration(DURATION);
        mAnimator.start();

        mOldDirection = direction;
    }

    public static interface AnimCallBack{
        /**
         * @param leftW：左边视图宽度
         * @param leftH：左边视图高度
         * @param rightW：右边视图宽度
         * @param rightH：右边视图高度
         * @param leftTransXOffset：左边视图x轴偏移量
         * @param rightTransXOffset：右边视图x轴偏移量
         * @param midTransOffset：两个视图中间分割线x轴偏移量
         */
        void onUpdate(int leftW,int leftH,int rightW,int rightH,float leftTransXOffset,float rightTransXOffset, float midTransOffset);
    }
}
