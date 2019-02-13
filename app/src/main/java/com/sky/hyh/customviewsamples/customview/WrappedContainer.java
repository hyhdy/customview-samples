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
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/2/13 21:01
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class WrappedContainer extends FrameLayout {
    private View mCurTouchView;
    private List<View> mChildList;
    private ScaleGestureDetector mScaleGestureDetector;

    public WrappedContainer(@NonNull Context context) {
        this(context,null);
    }

    public WrappedContainer(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
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

                case MotionEvent.ACTION_MOVE:

                break;

                case MotionEvent.ACTION_CANCEL:

                break;

                case MotionEvent.ACTION_UP:

                break;


        }
        if(mScaleGestureDetector!=null){
            mScaleGestureDetector.onTouchEvent(event);
        }
        return true;
    }

    public void addChild(View view){
        addView(view);
        mChildList.add(view);
    }
}
