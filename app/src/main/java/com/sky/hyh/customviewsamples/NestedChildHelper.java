package com.sky.hyh.customviewsamples;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewParentCompat;
import android.view.View;
import android.view.ViewParent;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;
import static android.support.v4.view.ViewCompat.TYPE_TOUCH;

public class NestedChildHelper {
    private ViewParent mNestedScrollingParentTouch;
    private ViewParent mNestedScrollingParentNonTouch;
    /**
     * 实现NestedScrollingChild接口的view
     */
    private final View mView;
    private boolean mIsNestedScrollingEnabled;
    private int[] mTempNestedScrollConsumed;

    /**
     * Construct a new helper for a given view.
     */
    public NestedChildHelper(@NonNull View view) {
        mView = view;
    }

    /**
     * Enable nested scrolling.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @param enabled true to enable nested scrolling dispatch from this view, false otherwise
     */
    public void setNestedScrollingEnabled(boolean enabled) {
        if (mIsNestedScrollingEnabled) {
            ViewCompat.stopNestedScroll(mView);
        }
        mIsNestedScrollingEnabled = enabled;
    }

    /**
     * Check if nested scrolling is enabled for this view.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @return true if nested scrolling is enabled for this view
     */
    public boolean isNestedScrollingEnabled() {
        return mIsNestedScrollingEnabled;
    }

    /**
     * Check if this view has a nested scrolling parent view currently receiving events for
     * a nested scroll in progress with the type of touch.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @return true if this view has a nested scrolling parent, false otherwise
     */
    public boolean hasNestedScrollingParent() {
        return hasNestedScrollingParent(TYPE_TOUCH);
    }

    /**
     * Check if this view has a nested scrolling parent view currently receiving events for
     * a nested scroll in progress with the given type.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @return true if this view has a nested scrolling parent, false otherwise
     */
    public boolean hasNestedScrollingParent(@ViewCompat.NestedScrollType int type) {
        return getNestedScrollingParentForType(type) != null;
    }

    public boolean startNestedScroll(@ViewCompat.ScrollAxis int axes) {
        return startNestedScroll(axes, TYPE_TOUCH);
    }

    public boolean startNestedScroll(@ViewCompat.ScrollAxis int axes, @ViewCompat.NestedScrollType int type) {
        if (hasNestedScrollingParent(type)) {
            // Already in progress
            return true;
        }
        if (isNestedScrollingEnabled()) {
            ViewParent p = mView.getParent();
            View child = mView;
            //遍历所有祖辈控件，找到实现了NestedScrollingParent2或NestedScrollingParent的控件，并存下其引用
            while (p != null) {
                if (ViewParentCompat.onStartNestedScroll(p, child, mView, axes, type)) {
                    setNestedScrollingParentForType(type, p);
                    ViewParentCompat.onNestedScrollAccepted(p, child, mView, axes, type);
                    return true;
                }
                if (p instanceof View) {
                    child = (View) p;
                }
                p = p.getParent();
            }
        }
        return false;
    }

    /**
     * Stop a nested scroll in progress.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     */
    public void stopNestedScroll() {
        stopNestedScroll(TYPE_TOUCH);
    }

    /**
     * Stop a nested scroll in progress.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild2} interface method with the same
     * signature to implement the standard policy.</p>
     */
    public void stopNestedScroll(@ViewCompat.NestedScrollType int type) {
        ViewParent parent = getNestedScrollingParentForType(type);
        if (parent != null) {
            ViewParentCompat.onStopNestedScroll(parent, mView, type);
            setNestedScrollingParentForType(type, null);
        }
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        return dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow, TYPE_TOUCH);
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow,
                                        @ViewCompat.NestedScrollType int type) {
        if (isNestedScrollingEnabled()) {
            //获取实现了NestedScrollingParent2或NestedScrollingParent的祖辈控件，如果没有则为null
            final ViewParent parent = getNestedScrollingParentForType(type);
            if (parent == null) {
                //没有实现NestedScrollingParent2或NestedScrollingParent的祖辈控件，则不作任何处理
                return false;
            }

            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }

                //将嵌套滑动事件分发给祖辈控件的onNestedScroll方法
                ViewParentCompat.onNestedScroll(parent, mView, dxConsumed,
                        dyConsumed, dxUnconsumed, dyUnconsumed, type);

                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] -= startX;
                    offsetInWindow[1] -= startY;
                }
                return true;
            } else if (offsetInWindow != null) {
                // No motion, no dispatch. Keep offsetInWindow up to date.
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed,
                                           @Nullable int[] offsetInWindow) {
        return dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, TYPE_TOUCH);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed,
                                           @Nullable int[] offsetInWindow, @ViewCompat.NestedScrollType int type) {
        if (isNestedScrollingEnabled()) {
            //获取实现了NestedScrollingParent2或NestedScrollingParent的祖辈控件，如果没有则为null
            final ViewParent parent = getNestedScrollingParentForType(type);
            if (parent == null) {
                //没有实现NestedScrollingParent2或NestedScrollingParent的祖辈控件，则不作任何处理
                return false;
            }

            if (dx != 0 || dy != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }

                if (consumed == null) {
                    if (mTempNestedScrollConsumed == null) {
                        mTempNestedScrollConsumed = new int[2];
                    }
                    consumed = mTempNestedScrollConsumed;
                }
                consumed[0] = 0;
                consumed[1] = 0;

                //将嵌套滑动事件分发给祖辈控件的onNestedPreScroll方法
                ViewParentCompat.onNestedPreScroll(parent, mView, dx, dy, consumed, type);

                if (offsetInWindow != null) {
                    mView.getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] -= startX;
                    offsetInWindow[1] -= startY;
                }
                return consumed[0] != 0 || consumed[1] != 0;
            } else if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    /**
     * Dispatch a nested fling operation to the current nested scrolling parent.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @return true if the parent consumed the nested fling
     */
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        if (isNestedScrollingEnabled()) {
            ViewParent parent = getNestedScrollingParentForType(TYPE_TOUCH);
            if (parent != null) {
                return ViewParentCompat.onNestedFling(parent, mView, velocityX,
                        velocityY, consumed);
            }
        }
        return false;
    }

    /**
     * Dispatch a nested pre-fling operation to the current nested scrolling parent.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @return true if the parent consumed the nested fling
     */
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (isNestedScrollingEnabled()) {
            ViewParent parent = getNestedScrollingParentForType(TYPE_TOUCH);
            if (parent != null) {
                return ViewParentCompat.onNestedPreFling(parent, mView, velocityX,
                        velocityY);
            }
        }
        return false;
    }

    /**
     * View subclasses should always call this method on their
     * <code>NestedScrollingChildHelper</code> when detached from a window.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     */
    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(mView);
    }

    /**
     * Called when a nested scrolling child stops its current nested scroll operation.
     *
     * <p>This is a delegate method. Call it from your {@link android.view.View View} subclass
     * method/{@link android.support.v4.view.NestedScrollingChild} interface method with the same
     * signature to implement the standard policy.</p>
     *
     * @param child Child view stopping its nested scroll. This may not be a direct child view.
     */
    public void onStopNestedScroll(@NonNull View child) {
        ViewCompat.stopNestedScroll(mView);
    }

    private ViewParent getNestedScrollingParentForType(@ViewCompat.NestedScrollType int type) {
        switch (type) {
            case TYPE_TOUCH:
                return mNestedScrollingParentTouch;
            case TYPE_NON_TOUCH:
                return mNestedScrollingParentNonTouch;
        }
        return null;
    }

    private void setNestedScrollingParentForType(@ViewCompat.NestedScrollType int type, ViewParent p) {
        switch (type) {
            case TYPE_TOUCH:
                mNestedScrollingParentTouch = p;
                break;
            case TYPE_NON_TOUCH:
                mNestedScrollingParentNonTouch = p;
                break;
        }
    }
}
