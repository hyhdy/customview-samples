package com.sky.hyh.customviewsamples;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public interface NestedChild {
    /**
     * 设置是否支持嵌套滑动，Recyclerview会在初始化时默认设置为true，
     * @param enabled
     */
    void setNestedScrollingEnabled(boolean enabled);

    /**
     * 是否支持嵌套滑动
     * @return
     */
    boolean isNestedScrollingEnabled();

    /**
     * 开始嵌套滑动，一般在down事件触发
     * @param axes：嵌套滑动方向
     * @return
     */
    boolean startNestedScroll(@ViewCompat.ScrollAxis int axes);

    void stopNestedScroll();

    /**
     * Returns true if this view has a nested scrolling parent.
     *
     * <p>The presence of a nested scrolling parent indicates that this view has initiated
     * a nested scroll and it was accepted by an ancestor view further up the view hierarchy.</p>
     *
     * @return whether this view has a nested scrolling parent
     */
    boolean hasNestedScrollingParent();

    /**
     * Dispatch one step of a nested scroll in progress.
     *
     * <p>Implementations of views that support nested scrolling should call this to report
     * info about a scroll in progress to the current nested scrolling parent. If a nested scroll
     * is not currently in progress or nested scrolling is not
     * {@link #isNestedScrollingEnabled() enabled} for this view this method does nothing.</p>
     *
     * <p>Compatible View implementations should also call
     * {@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll} before
     * consuming a component of the scroll event themselves.</p>
     *
     * @param dxConsumed Horizontal distance in pixels consumed by this view during this scroll step
     * @param dyConsumed Vertical distance in pixels consumed by this view during this scroll step
     * @param dxUnconsumed Horizontal scroll distance in pixels not consumed by this view
     * @param dyUnconsumed Horizontal scroll distance in pixels not consumed by this view
     * @param offsetInWindow Optional. If not null, on return this will contain the offset
     *                       in local view coordinates of this view from before this operation
     *                       to after it completes. View implementations may use this to adjust
     *                       expected input coordinate tracking.
     * @return true if the event was dispatched, false if it could not be dispatched.
     * @see #dispatchNestedPreScroll(int, int, int[], int[])
     */
    boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                 int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow);

    /**
     * 分发相邻的两次move事件的坐标差值
     * @param dx：手指move过程x轴的偏移量
     * @param dy：手指move过程y轴的偏移量
     * @param consumed
     * @param offsetInWindow
     * @return
     */
    boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed,
                                    @Nullable int[] offsetInWindow);

    /**
     * Dispatch a fling to a nested scrolling parent.
     *
     * <p>This method should be used to indicate that a nested scrolling child has detected
     * suitable conditions for a fling. Generally this means that a touch scroll has ended with a
     * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
     * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     * along a scrollable axis.</p>
     *
     * <p>If a nested scrolling child view would normally fling but it is at the edge of
     * its own content, it can use this method to delegate the fling to its nested scrolling
     * parent instead. The parent may optionally consume the fling or observe a child fling.</p>
     *
     * @param velocityX Horizontal fling velocity in pixels per second
     * @param velocityY Vertical fling velocity in pixels per second
     * @param consumed true if the child consumed the fling, false otherwise
     * @return true if the nested scrolling parent consumed or otherwise reacted to the fling
     */
    boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);

    /**
     * Dispatch a fling to a nested scrolling parent before it is processed by this view.
     *
     * <p>Nested pre-fling events are to nested fling events what touch intercept is to touch
     * and what nested pre-scroll is to nested scroll. <code>dispatchNestedPreFling</code>
     * offsets an opportunity for the parent view in a nested fling to fully consume the fling
     * before the child view consumes it. If this method returns <code>true</code>, a nested
     * parent view consumed the fling and this view should not scroll as a result.</p>
     *
     * <p>For a better user experience, only one view in a nested scrolling chain should consume
     * the fling at a time. If a parent view consumed the fling this method will return false.
     * Custom view implementations should account for this in two ways:</p>
     *
     * <ul>
     *     <li>If a custom view is paged and needs to settle to a fixed page-point, do not
     *     call <code>dispatchNestedPreFling</code>; consume the fling and settle to a valid
     *     position regardless.</li>
     *     <li>If a nested parent does consume the fling, this view should not scroll at all,
     *     even to settle back to a valid idle position.</li>
     * </ul>
     *
     * <p>Views should also not offer fling velocities to nested parent views along an axis
     * where scrolling is not currently supported; a {@link android.widget.ScrollView ScrollView}
     * should not offer a horizontal fling velocity to its parents since scrolling along that
     * axis is not permitted and carrying velocity along that motion does not make sense.</p>
     *
     * @param velocityX Horizontal fling velocity in pixels per second
     * @param velocityY Vertical fling velocity in pixels per second
     * @return true if a nested scrolling parent consumed the fling
     */
    boolean dispatchNestedPreFling(float velocityX, float velocityY);
}
