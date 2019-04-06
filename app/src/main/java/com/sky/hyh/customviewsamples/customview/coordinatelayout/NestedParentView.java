package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.customviewpager.CustomViewPager;
import com.sky.hyh.customviewsamples.entity.CatAdapter;

import java.util.ArrayList;
import java.util.List;

public class NestedParentView extends LinearLayout implements NestedScrollingParent2 {
    public static final int RES_ID = R.layout.container_nested_parent_layout;
    private NestedScrollView mNestedScrollView;

    public NestedParentView(@NonNull Context context) {
        this(context,null);
    }

    public NestedParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(R.color.pure_black));
        View root = inflate(context,RES_ID,this);
        initViews(root);
    }

    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;

    protected void initViews(View rootView) {
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
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        //如果是竖直方向滑动，就启动嵌套滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d("hyh","NestedParentView: onNestedScroll: dyConsumed="+dyConsumed+" ,dyUnconsumed="+dyUnconsumed);
        //scrollTo(0,getMeasuredHeight());
        Log.d("hyh","NestedParentView: onNestedScroll: vp height="+mViewPager.getMeasuredHeight());
        if(dyUnconsumed <= mViewPager.getMeasuredHeight()+mTabLayout.getMeasuredHeight()){
            scrollBy(0,dyUnconsumed);
        }else{
            scrollBy(0,mViewPager.getMeasuredHeight()+mTabLayout.getMeasuredHeight());
            findViewById(R.id.rv_list).scrollBy(0,dyUnconsumed - mViewPager.getMeasuredHeight() - mTabLayout.getMeasuredHeight());
        }


    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        Log.d("hyh","NestedParentView: onNestedPreScroll: dy="+dy+" ,consumed[1]="+consumed[1]);

    }
}
