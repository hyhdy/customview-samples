package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.sky.hyh.customviewsamples.utils.RectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义网格视图
 */
public abstract class BaseSplitGridView extends View {
    public static final int STATE_LONG_CLICK_START = 1;
    public static final int STATE_LONG_CLICK_CONTINUOUS = 2;
    public static final int STATE_LONG_CLICK_END = 3;

    protected int mWidth;
    protected int mHeight;
    /**
     * 记录每个网格的区域
     */
    protected List<Rect> mRectList;
    private int mCurClickX;
    private int mCurClickY;
    private LongClickHelper mLongClickHelper;
    private boolean mIsLongClick;

    public BaseSplitGridView(Context context) {
        this(context,null);
    }

    public BaseSplitGridView(Context context,
                             @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRectList = new ArrayList<>();
        mLongClickHelper = new LongClickHelper();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsLongClick){
                    return;
                }
                //点击事件
                Rect rect = getTouchRect();
                if(rect!=null){
                    onClickView(rect);
                }
            }
        });

        mLongClickHelper.setOnLongClickStateCallBack(
            new LongClickHelper.OnLongClickStateCallBack() {
                @Override
                public void onLongClickStart() {
                    mIsLongClick = true;
                    //长按开始
                    Rect rect = getTouchRect();
                    if(rect!=null){
                        onLongClick(STATE_LONG_CLICK_START,rect);
                    }
                }

                @Override
                public void onLongClickEnd() {
                    //长按结束
                    Rect rect = getTouchRect();
                    if(rect!=null){
                        onLongClick(STATE_LONG_CLICK_END,rect);
                    }
                }
            });

        mLongClickHelper.setOnClickContinuousCallBack(
            new LongClickHelper.OnClickContinuousCallBack() {
                @Override
                public void onClickContinuous() {
                    //持续长按
                    Rect rect = getTouchRect();
                    if(rect!=null){
                        onLongClick(STATE_LONG_CLICK_CONTINUOUS,rect);
                    }
                }
            });
    }

    /**
     * 获取接触区域
     * @return
     */
    private Rect getTouchRect(){
        Rect rect = null;
        for(int i=0;i<mRectList.size();i++){
            if(RectUtil.isOverLay(mRectList.get(i),mCurClickX,mCurClickY)){
                rect = mRectList.get(i);
                break;
            }
        }
        return rect;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        int perWidth = mWidth/getPerLineCount();
        int perHeight = mHeight/getLineCount();

        for(int i=0;i<getLineCount();i++){
            for(int j=0;j<getPerLineCount();j++){
                Rect rect = new Rect();
                int l = perWidth * j;
                int t = perHeight * i;
                rect.set(l,t,l+perWidth,t+perHeight);
                mRectList.add(rect);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            mIsLongClick = false;
            mCurClickX = (int) event.getX();
            mCurClickY = (int) event.getY();
            mLongClickHelper.postMessage();
        } else if(action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL){
            mLongClickHelper.removeMessage();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 获取行数
     * @return
     */
    protected abstract int getLineCount();

    /**
     * 获取列数
     * @return
     */
    protected abstract int getPerLineCount();

    /**
     * 点击操作
     * @param rect：点击的区域
     */
    protected abstract void onClickView(Rect rect);

    /**
     * 长按操作
     * @param state
     * @param rect
     */
    protected void onLongClick(int state,Rect rect){

    }

    protected static class FakeView<T>{
        int type;
        Rect rect;
        T data;
    }
}
