package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(Context context) {
        this(context,null);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.d("hyh","CustomRecyclerView: setNestedScrollingEnabled: enabled="+enabled);
        super.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.d("hyh","CustomRecyclerView: isNestedScrollingEnabled: ");
        return super.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.d("hyh","CustomRecyclerView: startNestedScroll: ");
        return super.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        Log.d("hyh","CustomRecyclerView: stopNestedScroll: ");
        super.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        Log.d("hyh","CustomRecyclerView: hasNestedScrollingParent: ");
        return super.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedScroll: dyConsumed="+dyConsumed+
                " ,dyUnconsumed="+dyUnconsumed);
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedPreScroll: dy="+dy+
                " ,consumed[1]="+consumed[1]);
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedFling: velocityY="+velocityY+" ,consumed="+consumed);
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedPreFling: velocityY="+velocityY);
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }
}
