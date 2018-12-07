package com.sky.hyh.customviewsamples.customview;

import android.os.Handler;
import android.os.Message;

/**
 *长按辅助类，可以监听长按开始，长按结束和持续长按事件
 */
public class LongClickHelper {
    private static final int STATE_NORMAL = 0;
    private static final int STATE_LONG_CLICK = 1;
    private static final int TIME_DELAY = 100;
    private Runnable mLongClickRunnable;
    /**
     * 触发长按事件的时间阈值，毫秒
     */
    private static final int DEFAULT_LONG_CLICK_LIMITED_TIME = 500;
    private EventHandler mHandler;
    private OnLongClickStateCallBack mOnLongClickStateCallBack;

    public LongClickHelper() {
        mHandler = new EventHandler();

        mLongClickRunnable = new Runnable() {
            @Override
            public void run() {
                longClickStart();
            }
        };
    }

    private static class EventHandler extends Handler {
        private int mState;
        private OnClickContinuousCallBack mOnClickContinuousCallBack;

        @Override
        public void handleMessage(Message msg) {
            if (mState == STATE_LONG_CLICK) {
                if (mOnClickContinuousCallBack != null) {
                    mOnClickContinuousCallBack.onClickContinuous();
                }
                sendMessageDelayed(Message.obtain(), TIME_DELAY);
            }
        }

        public int getState() {
            return mState;
        }

        public void setState(int state) {
            mState = state;
        }

        public void setOnClickContinuousCallBack(
            OnClickContinuousCallBack onClickContinuousCallBack) {
            mOnClickContinuousCallBack = onClickContinuousCallBack;
        }
    }

    public void postMessage() {
        mHandler.postDelayed(mLongClickRunnable, DEFAULT_LONG_CLICK_LIMITED_TIME);
    }

    public void removeMessage() {
        longClickEnd();
    }

    private void longClickStart() {
        mHandler.setState(STATE_LONG_CLICK);
        if(mOnLongClickStateCallBack!=null){
            mOnLongClickStateCallBack.onLongClickStart();
        }
        mHandler.sendEmptyMessage(STATE_LONG_CLICK);
    }

    private void longClickEnd() {
        if(mHandler.getState() == STATE_LONG_CLICK){
            if(mOnLongClickStateCallBack!=null){
                mOnLongClickStateCallBack.onLongClickEnd();
            }
            mHandler.setState(STATE_NORMAL);
        }
        mHandler.removeCallbacks(mLongClickRunnable);
    }

    public void setOnClickContinuousCallBack(
        OnClickContinuousCallBack onClickContinuousCallBack) {
        mHandler.setOnClickContinuousCallBack(onClickContinuousCallBack);
    }

    public interface OnClickContinuousCallBack {
        void onClickContinuous();
    }

    public void setOnLongClickStateCallBack(
        OnLongClickStateCallBack onLongClickStateCallBack) {
        mOnLongClickStateCallBack = onLongClickStateCallBack;
    }

    public interface OnLongClickStateCallBack{
        void onLongClickStart();

        void onLongClickEnd();
    }

    public boolean isLongClick(){
        return mHandler.getState() == STATE_LONG_CLICK;
    }
}
