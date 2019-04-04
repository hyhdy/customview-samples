package com.sky.hyh.customviewsamples.fragment.impl;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.CoordinatePagerAdapter;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.BaseAppBarLayout;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.RootLayout;
import com.sky.hyh.customviewsamples.customview.customviewpager.CustomViewPager;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hyh on 2019/4/4
 */
public class CoordinatorLayoutFragment extends BaseFragment {
    private RootLayout mRootLayout;
    private Button mBtnSwitch;

    @Override
    protected int getResId() {
        return R.layout.fragment_coordinatorlayout;
    }

    @Override
    protected void initViews(View rootView) {
        mRootLayout = rootView.findViewById(R.id.root);
        mBtnSwitch = findViewById(R.id.btn_switch);
        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootLayout.updateView(!mRootLayout.isHaveList());
            }
        });
    }
}
