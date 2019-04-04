package com.sky.hyh.customviewsamples.fragment.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.CoordinatePagerAdapter;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.BaseAppBarLayout;
import com.sky.hyh.customviewsamples.customview.customviewpager.CustomViewPager;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hyh on 2019/4/4
 */
public class CoordinatorLayoutFragment extends BaseFragment {
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private BaseAppBarLayout mAppBarLayout;

    @Override
    protected int getResId() {
        return R.layout.fragment_coordinatorlayout;
    }

    @Override
    protected void initViews(View rootView) {
        mAppBarLayout = rootView.findViewById(R.id.app_bar_layout);
        mAppBarLayout.addOffsetListener();

        mViewPager = findViewById(R.id.vp_content);
        mViewPager.setPagingEnabled(false);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.setupWithViewPager(mViewPager,false);

        List<String> datas=new ArrayList<>();
        datas.add("#990000");
        datas.add("#271309");
        datas.add("#213456");
        datas.add("#016716");
        datas.add("#170456");
        datas.add("#ff11ff");
        datas.add("#016755");
        datas.add("#170111");
        datas.add("#3001ff");

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
}
