package com.sky.hyh.customviewsamples.customview.wrappedContainer;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
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
 * Created by hyh on 2019/3/18 14:28
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class WrappedContainer2 extends FrameLayout {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;
    private int mActivePointerIndex = 0;
    private float mLastTouchX;
    private float mLastTouchY;
    private final float mTouchSlop;
    private boolean mIsDragging;

    private ContainerView mContainerView;
    private CustomDrawable mCurTouchDrawable;
    //private ScaleGestureDetector mScaleGestureDetector;

    public WrappedContainer2(@NonNull Context context) {
        this(context,null);
    }

    public WrappedContainer2(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        final ViewConfiguration configuration = ViewConfiguration
            .get(context);
        mTouchSlop = configuration.getScaledTouchSlop();

        //ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        //    @Override
        //    public boolean onScale(ScaleGestureDetector detector) {
        //        if(mCurTouchView!=null){
        //            float scaleFactor = detector.getScaleFactor();
        //            float scaleX = mCurTouchView.getScaleX();
        //            float scaleY = mCurTouchView.getScaleY();
        //            scaleX *= scaleFactor;
        //            scaleY *= scaleFactor;
        //            mCurTouchView.setScaleX(scaleX);
        //            mCurTouchView.setScaleY(scaleY);
        //        }
        //        return true;
        //    }
        //
        //    @Override
        //    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //        return true;
        //    }
        //
        //    @Override
        //    public void onScaleEnd(ScaleGestureDetector detector) {
        //
        //    }
        //};
        //mScaleGestureDetector = new ScaleGestureDetector(context,onScaleGestureListener);

        mContainerView = new ContainerView(getContext());
        addView(mContainerView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN: {
                mCurTouchDrawable = null;
                //获取第1个手指的id
                mActivePointerId = event.getPointerId(0);
                mLastTouchX = getActiveX(event);
                mLastTouchY = getActiveY(event);
                mIsDragging = false;
                int x = (int) event.getX();
                int y = (int) event.getY();
                List<CustomDrawable> customDrawableList = mContainerView.getDrawableList();
                //判断对哪个子view操作
                for (int i = customDrawableList.size() - 1; i >= 0; i--) {
                    CustomDrawable drawable = customDrawableList.get(i);
                    Rect rect = new Rect();
                    drawable.copyBounds(rect);
                    Log.d("hyh", "WrappedContainer: onTouchEvent: rect=" + rect.toString());
                    if (rect.contains(x, y)) {
                        mCurTouchDrawable = drawable;
                        mContainerView.setCurTouchDrawable(drawable);
                        break;
                    }
                }
            }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                if(mCurTouchDrawable!=null) {
                    final float x = getActiveX(event);
                    final float y = getActiveY(event);
                    final int dx = (int) (x - mLastTouchX);
                    final int dy = (int) (y - mLastTouchY);

                    if (!mIsDragging) {
                        mIsDragging = Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
                    }

                    if (mIsDragging) {
                        mCurTouchDrawable.setX(mCurTouchDrawable.getX()+dx);
                        mCurTouchDrawable.setY(mCurTouchDrawable.getY()+dy);
                        mContainerView.invalidate();
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

        //if(mScaleGestureDetector!=null){
        //    mScaleGestureDetector.onTouchEvent(event);
        //}
        return true;
    }

    public void add(){
        mContainerView.addDrawable();
        mContainerView.invalidate();
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
