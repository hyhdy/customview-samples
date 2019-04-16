package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.Button;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.coordinatelayout.RootLayout;

/**
 * created by hyh on 2019/4/4
 */
@InjectFragment()
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
