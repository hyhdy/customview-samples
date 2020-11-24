package com.sky.hyh.customviewsamples.danmu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.hyh.base_lib.utils.SizeUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

/**
 * created by curdyhuang on 2020/11/24
 * 弹道，用于控制弹幕运行轨迹
 */
public class BarrageWay {
    public static final int MIN_DISTANCE = SizeUtils.dp2px(10);
    public static final float SPEED = SizeUtils.dp2px(100);//每秒移动100dp
    private ViewGroup mContainer;//弹道通道
    private BarrageViewFactory mFactory;//弹幕控件工厂
    private Queue<BarrageItem> mQueue = new LinkedList<>();//弹幕队列
    private boolean mIsLock = false;//是否封锁弹道，用于控制弹幕上屏时机，当封锁时不能发射弹幕，只有解封时才可以
    private Handler mHandler = new Handler();
    private ScheduleTask mTask;

    public BarrageWay(ViewGroup group, BarrageViewFactory factory) {
        mContainer = group;
        mFactory = factory;
        mTask = new ScheduleTask(this);
    }

    /**
     * 添加弹幕
     */
    public void addBarrage(BarrageItem item){
        if(!mIsLock){
            //弹道未封锁则直接加载弹幕显示
            loadBarrage(item);
        }else{
            //进队列
            enterQueue(item);
        }
    }

    /**
     * 进入队列等候
     */
    private void enterQueue(BarrageItem item){
        mQueue.add(item);
    }

    /**
     * 装载弹幕
     */
    private void loadBarrage(BarrageItem item){
        BarrageView view = mFactory.build();
        view.setData(item);
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec,spec);
        view.setTranslationX(mContainer.getWidth());
        view.setVisibility(View.GONE);
        mContainer.addView(view);

        startAnim(view);

        mIsLock = true;
        mHandler.postDelayed(mTask, getDelayTime(view.getMeasuredWidth(),mContainer.getWidth()));
    }

    private long getDelayTime(int viewWidth,int containerWidth){
        int delayDistance = (viewWidth>containerWidth?containerWidth:viewWidth) + MIN_DISTANCE;
        return  (long) (delayDistance/SPEED*1000);
    }

    private long getDuration(int viewWidth,int containerWidth){
        int delayDistance = (viewWidth>containerWidth?containerWidth:viewWidth) + containerWidth;
        return  (long) (delayDistance/SPEED*1000);
    }

    /**
     * 开启弹幕轨迹动画
     */
    private void startAnim(BarrageView view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"translationX",mContainer.getWidth(),-view.getMeasuredWidth());
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(getDuration(view.getMeasuredWidth(),mContainer.getWidth()));
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mContainer.removeView(view);
                mFactory.recycle(view);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    private static class ScheduleTask implements Runnable{
        private WeakReference<BarrageWay> mWay;

        public ScheduleTask(BarrageWay way) {
            mWay = new WeakReference<>(way);
        }

        @Override
        public void run() {
            BarrageWay way = mWay.get();
            if(way != null){
                BarrageItem item = way.mQueue.poll();
                if (item!=null){
                    //从队列取出下一个弹幕播放
                    way.loadBarrage(item);
                }else{
                    //队列为空则解锁弹道
                    way.mIsLock = false;
                }
            }
        }
    }

    public void clear(){
        mIsLock = false;
        mQueue.clear();
        mHandler.removeCallbacks(mTask);
    }

    public static class BarrageItem{
        private String mContent;
        private String mIcon;

        public BarrageItem(String content, String icon) {
            this.mContent = content;
            this.mIcon = icon;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public String getIcon() {
            return mIcon;
        }

        public void setIcon(String icon) {
            mIcon = icon;
        }
    }
}
