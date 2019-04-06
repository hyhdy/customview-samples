package com.sky.hyh.customviewsamples;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 继承该接口的ViewGroup可以处理与继承NestedScrollingChild接口的孩子之间的嵌套滑动
 */
public interface NestedParent {
    /**
     * 询问父控件是否处理嵌套滑动的方法。
     * 当某个继承NestedScrollingChild接口的孩子（包括直接孩子和间接孩子）
     * 通过startNestedScroll发起一个嵌套滑动事件后会调用到该方法，询问父控件是否接受嵌套滑动。
     * @param child：继承本接口的ViewGroup的一个直接孩子，child可能是target也可能是target的父控件（不一定是直接父亲）。
     * @param target：发起嵌套滑动事件的控件。也就是调用startNestedScroll方法的view
     * @param axes ：嵌套滑动方向，包括水平和竖直或都有
     * @return ：返回true接受。
     */
    boolean onStartNestedScroll(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes);

    /**
     * React to the successful claiming of a nested scroll operation.
     *
     * <p>This method will be called after
     * {@link #onStartNestedScroll(View, View, int) onStartNestedScroll} returns true. It offers
     * an opportunity for the view and its superclasses to perform initial configuration
     * for the nested scroll. Implementations of this method should always call their superclass's
     * implementation of this method if one is present.</p>
     *
     * @param child Direct child of this ViewParent containing target
     * @param target View that initiated the nested scroll
     * @param axes Flags consisting of {@link ViewCompat#SCROLL_AXIS_HORIZONTAL},
     *                         {@link ViewCompat#SCROLL_AXIS_VERTICAL} or both
     * @see #onStartNestedScroll(View, View, int)
     * @see #onStopNestedScroll(View)
     */
    void onNestedScrollAccepted(@NonNull View child, @NonNull View target, @ViewCompat.ScrollAxis int axes);

    /**
     * React to a nested scroll operation ending.
     *
     * <p>Perform cleanup after a nested scrolling operation.
     * This method will be called when a nested scroll stops, for example when a nested touch
     * scroll ends with a {@link MotionEvent#ACTION_UP} or {@link MotionEvent#ACTION_CANCEL} event.
     * Implementations of this method should always call their superclass's implementation of this
     * method if one is present.</p>
     *
     * @param target View that initiated the nested scroll
     */
    void onStopNestedScroll(@NonNull View target);

    /**
     * React to a nested scroll in progress.
     *
     * <p>This method will be called when the ViewParent's current nested scrolling child view
     * dispatches a nested scroll event. To receive calls to this method the ViewParent must have
     * previously returned <code>true</code> for a call to
     * {@link #onStartNestedScroll(View, View, int)}.</p>
     *
     * <p>Both the consumed and unconsumed portions of the scroll distance are reported to the
     * ViewParent. An implementation may choose to use the consumed portion to match or chase scroll
     * position of multiple child elements, for example. The unconsumed portion may be used to
     * allow continuous dragging of multiple scrolling or draggable elements, such as scrolling
     * a list within a vertical drawer where the drawer begins dragging once the edge of inner
     * scrolling content is reached.</p>
     *
     * @param target The descendent view controlling the nested scroll
     * @param dxConsumed Horizontal scroll distance in pixels already consumed by target
     * @param dyConsumed Vertical scroll distance in pixels already consumed by target
     * @param dxUnconsumed Horizontal scroll distance in pixels not consumed by target
     * @param dyUnconsumed Vertical scroll distance in pixels not consumed by target
     */
    void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                        int dxUnconsumed, int dyUnconsumed);

    /**
     * 在嵌套滑动的子View未滑动之前告诉父控件准备滑动的情况
     * @param target
     * @param dx：水平方向嵌套滑动子view想要变化的距离
     * @param dy：竖直方向嵌套滑动子view想要变化的距离
     * @param consumed：父控件消耗的距离，onsumed[0]表示水平方向消耗的距离，consumed[1]表示竖直方向消耗的距离。
     *                父控件将消耗的距离存储在这个数组，然后告诉嵌套滑动子view我已经消耗一些距离，子view应做出相应调整，
     *                通常得出嵌套滑动子view水平方向滑动的值等于dx - consumed[0]，嵌套滑动子view水平方向滑动的值等于dy - consumed[1]。
     *
     */
    void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed);

    /**
     * Request a fling from a nested scroll.
     *
     * <p>This method signifies that a nested scrolling child has detected suitable conditions
     * for a fling. Generally this means that a touch scroll has ended with a
     * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
     * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     * along a scrollable axis.</p>
     *
     * <p>If a nested scrolling child view would normally fling but it is at the edge of
     * its own content, it can use this method to delegate the fling to its nested scrolling
     * parent instead. The parent may optionally consume the fling or observe a child fling.</p>
     *
     * @param target View that initiated the nested scroll
     * @param velocityX Horizontal velocity in pixels per second
     * @param velocityY Vertical velocity in pixels per second
     * @param consumed true if the child consumed the fling, false otherwise
     * @return true if this parent consumed or otherwise reacted to the fling
     */
    boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed);

    /**
     * React to a nested fling before the target view consumes it.
     *
     * <p>This method siginfies that a nested scrolling child has detected a fling with the given
     * velocity along each axis. Generally this means that a touch scroll has ended with a
     * {@link VelocityTracker velocity} in the direction of scrolling that meets or exceeds
     * the {@link ViewConfiguration#getScaledMinimumFlingVelocity() minimum fling velocity}
     * along a scrollable axis.</p>
     *
     * <p>If a nested scrolling parent is consuming motion as part of a
     * {@link #onNestedPreScroll(View, int, int, int[]) pre-scroll}, it may be appropriate for
     * it to also consume the pre-fling to complete that same motion. By returning
     * <code>true</code> from this method, the parent indicates that the child should not
     * fling its own internal content as well.</p>
     *
     * @param target View that initiated the nested scroll
     * @param velocityX Horizontal velocity in pixels per second
     * @param velocityY Vertical velocity in pixels per second
     * @return true if this parent consumed the fling ahead of the target view
     */
    boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY);

    /**
     * Return the current axes of nested scrolling for this NestedScrollingParent.
     *
     * <p>A NestedScrollingParent returning something other than {@link ViewCompat#SCROLL_AXIS_NONE}
     * is currently acting as a nested scrolling parent for one or more descendant views in
     * the hierarchy.</p>
     *
     * @return Flags indicating the current axes of nested scrolling
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL
     * @see ViewCompat#SCROLL_AXIS_VERTICAL
     * @see ViewCompat#SCROLL_AXIS_NONE
     */
    @ViewCompat.ScrollAxis
    int getNestedScrollAxes();
}
