package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.ProgressBar;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.R;

@InjectFragment()
public class ProgressFragment extends BaseFragment {

    @Override
    protected int getResId() {
        return R.layout.fragment_progress;
    }

    @Override
    protected void initViews(View rootView) {
    }
}
