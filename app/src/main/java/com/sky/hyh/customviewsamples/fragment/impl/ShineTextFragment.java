package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.TextView;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.utils.SizeUtils;
import com.sky.hyh.customviewsamples.R;

@InjectFragment()
public class ShineTextFragment extends BaseFragment {
    @FindViewByIdAno(R.id.tv_dis)
    private TextView mTvDis;

    @Override
    protected int getResId() {
        return R.layout.fragment_shine_text;
    }

    @Override
    protected void initViews(View rootView) {
        mTvDis.setShadowLayer(SizeUtils.dp2px(5),0,0,0xFFFFFD7C);
    }
}
