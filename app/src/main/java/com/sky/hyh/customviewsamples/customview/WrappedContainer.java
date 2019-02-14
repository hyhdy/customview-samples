package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/2/13 21:01
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class WrappedContainer extends FrameLayout {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private int mActivePointerIndex = 0;
    private float mLastTouchX;
    private float mLastTouchY;
    private final float mTouchSlop;
    private boolean mIsDragging;
    private View mCurTouchView;
    private List<View> mChildList;
    private ScaleGestureDetector mScaleGestureDetector;

    public WrappedContainer(@NonNull Context context) {
        this(context,null);
    }

    public WrappedContainer(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        final ViewConfiguration configuration = ViewConfiguration
            .get(context);
        mTouchSlop = configuration.getScaledTouchSlop();

        mChildList = new ArrayList<>();
        ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if(mCurTouchView!=null){
                    float scaleFactor = detector.getScaleFactor();
                    float scaleX = mCurTouchView.getScaleX();
                    float scaleY = mCurTouchView.getScaleY();
                    scaleX *= scaleFactor;
                    scaleY *= scaleFactor;
                    mCurTouchView.setScaleX(scaleX);
                    mCurTouchView.setScaleY(scaleY);
                }
                return true;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        };
        mScaleGestureDetector = new ScaleGestureDetector(context,onScaleGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();

        switch (action){
                case MotionEvent.ACTION_DOWN:
                    mCurTouchView = null;
                    //获取第1个手指的id
                    mActivePointerId = event.getPointerId(0);
                    mLastTouchX = getActiveX(event);
                    mLastTouchY = getActiveY(event);
                    mIsDragging = false;

                    //判断对哪个子view操作
                    for(View view: mChildList){
                        Rect rect = new Rect();
                        view.getGlobalVisibleRect(rect);
                        Log.d("hyh", "WrappedContainer: onTouchEvent: rect="+rect.toString());
                        if(rect.contains(rawX, rawY)){
                            mCurTouchView = view;
                            //将该view置于顶层
                            bringChildToFront(mCurTouchView);
                            break;
                        }
                    }
                break;
                case MotionEvent.ACTION_POINTER_DOWN:
                break;

                case MotionEvent.ACTION_MOVE:
                    if(mCurTouchView!=null) {
                        final float x = getActiveX(event);
                        final float y = getActiveY(event);
                        final float dx = x - mLastTouchX, dy = y - mLastTouchY;

                        if (!mIsDragging) {
                            mIsDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                        }

                        if (mIsDragging) {
                            mCurTouchView.setTranslationX(mCurTouchView.getTranslationX() + dx);
                            mCurTouchView.setTranslationY(mCurTouchView.getTranslationY() + dy);
                            mLastTouchX = x;
                            mLastTouchY = y;
                        }
                    }
                break;

                case MotionEvent.ACTION_CANCEL:
                    mActivePointerId = INVALID_POINTER_ID;
                break;

                case MotionEvent.ACTION_POINTER_UP:
                    int index = event.getActionIndex();
                    final int pointerId = event.getPointerId(index);
                    if (pointerId == mActivePointerId) {
                        //当控制move的主手指抬起时需要更换另一个手指为主手指
                        final int newPointerIndex = index == 0 ? 1 : 0;
                        mActivePointerId = event.getPointerId(newPointerIndex);
                        mLastTouchX = event.getX(newPointerIndex);
                        mLastTouchY = event.getY(newPointerIndex);
                    }

                break;

                case MotionEvent.ACTION_UP:
                    mActivePointerId = INVALID_POINTER_ID;
                break;


        }

        mActivePointerIndex = event.findPointerIndex(mActivePointerId != INVALID_POINTER_ID ? mActivePointerId : 0);

        if(mScaleGestureDetector!=null){
            mScaleGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    public void addChild(View view){
        addView(view);
        mChildList.add(view);
    }

    private float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(mActivePointerIndex);
        } catch (Exception e) {
            return ev.getX();
        }
    }

    private float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(mActivePointerIndex);
        } catch (Exception e) {
            return ev.getY();
        }
    }
}
