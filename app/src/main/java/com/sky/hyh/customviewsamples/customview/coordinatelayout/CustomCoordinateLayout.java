package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.customviewpager.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hyh on 2019/4/4
 */
public class CustomCoordinateLayout extends CoordinatorLayout {
    public static final int RES_ID = R.layout.container_coordinate_layout;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private BaseAppBarLayout mAppBarLayout;

    public CustomCoordinateLayout(Context context) {
        this(context,null);
    }

    public CustomCoordinateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View root = inflate(getContext(),RES_ID,this);
        initViews(root);
    }

    protected void initViews(View rootView) {
        mAppBarLayout = rootView.findViewById(R.id.app_bar_layout);
        mAppBarLayout.addOffsetListener();

        mViewPager = findViewById(R.id.vp_content);
        mViewPager.setPagingEnabled(true);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager,false);

        List<List<Integer>> datas=new ArrayList<>();
        int num = 30;
        List<Integer> l1 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l1.add(R.drawable.cat_1);
        }
        datas.add(l1);

        List<Integer> l2 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l2.add(R.drawable.cat_2);
        }
        datas.add(l2);

        List<Integer> l3 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l3.add(R.drawable.cat_3);
        }
        datas.add(l3);

        List<Integer> l4 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l4.add(R.drawable.cat_4);
        }
        datas.add(l4);

        List<Integer> l5 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l5.add(R.drawable.cat_5);
        }
        datas.add(l5);

        List<Integer> l6 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l6.add(R.drawable.cat_6);
        }
        datas.add(l6);

        List<Integer> l7 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l7.add(R.drawable.cat_7);
        }
        datas.add(l7);

        List<Integer> l8 = new ArrayList<>();
        for(int i=0; i< num;i++){
            l8.add(R.drawable.cat_8);
        }
        datas.add(l8);

        String[] titleContents = new String[datas.size()];
        for(int i=0;i<datas.size();i++){
            titleContents[i] = "哈哈"+i;
        }
        CoordinatePagerAdapter coordinatePagerAdapter=new CoordinatePagerAdapter(getContext(),datas);

        setAdapter(titleContents,coordinatePagerAdapter);
    }

    public void setAdapter(String[] titleContents, CoordinatePagerAdapter pagerAdapter){
        mTabLayout.removeAllTabs();
        for(int i=0;i<titleContents.length;i++){
            mTabLayout.addTab(mTabLayout.newTab());
        }

        mViewPager.setAdapter(pagerAdapter);

        for(int i=0;i<titleContents.length;i++){
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(tab!=null) {
                tab.setText(titleContents[i]);
            }
        }
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int axes, int type) {
        Log.d("hyh","CustomCoordinateLayout: onStartNestedScroll: ");
        return super.onStartNestedScroll(child, target, axes, type);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes, int type) {
        Log.d("hyh","CustomCoordinateLayout: onNestedScrollAccepted: ");
        super.onNestedScrollAccepted(child, target, nestedScrollAxes, type);
    }

    @Override
    public void onStopNestedScroll(View target, int type) {
        Log.d("hyh","CustomCoordinateLayout: onStopNestedScroll: ");
        super.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d("hyh","CustomCoordinateLayout: onNestedScroll: dyConsumed="+dyConsumed +
                ",dyUnconsumed="+dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.d("hyh","CustomCoordinateLayout: onNestedScroll after: dyConsumed="+dyConsumed +
                ",dyUnconsumed="+dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed, int type) {
        Log.d("hyh","CustomCoordinateLayout: onNestedPreScroll: dy="+dy+" ,consumed[1]="+
                consumed[1]);
        super.onNestedPreScroll(target, dx, dy, consumed, type);
        Log.d("hyh","CustomCoordinateLayout: onNestedPreScroll after: dy="+dy+" ,consumed[1]="+
                consumed[1]);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d("hyh","CustomCoordinateLayout: onNestedFling: velocityY="+
                velocityY+" ,consumed="+consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d("hyh","CustomCoordinateLayout: onNestedPreFling: velocityY="+velocityY);
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        Log.d("hyh","CustomCoordinateLayout: getNestedScrollAxes: ");
        return super.getNestedScrollAxes();
    }
}
