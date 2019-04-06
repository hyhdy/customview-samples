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
    public boolean startNestedScroll(int axes) {
        Log.d("hyh","CustomRecyclerView: startNestedScroll: 1");
        return super.startNestedScroll(axes);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.d("hyh","CustomRecyclerView: startNestedScroll: 2");
        return super.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll() {
        Log.d("hyh","CustomRecyclerView: stopNestedScroll: 1");
        super.stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        Log.d("hyh","CustomRecyclerView: stopNestedScroll: 2");
        super.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.d("hyh","CustomRecyclerView: hasNestedScrollingParent: 1");
        return super.hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        Log.d("hyh","CustomRecyclerView: hasNestedScrollingParent: 2");
        return super.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedScroll: 1");
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedScroll: 2");
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedPreScroll: 1");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedPreScroll: 2");
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedFling: ");
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d("hyh","CustomRecyclerView: dispatchNestedPreFling: ");
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }
}
