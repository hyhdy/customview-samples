package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.DynamicRoundTextView;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

public class DynamicRoundTextViewFragment extends BaseFragment implements View.OnClickListener{
    DynamicRoundTextView mDynamicRoundTextView;

    @Override
    protected int getResId() {
        return R.layout.fragment_dynamic_round_textview;
    }

    @Override
    protected void initViews(View rootView) {
        TextView tvSpread = rootView.findViewById(R.id.tv_click_spread);
        tvSpread.setOnClickListener(this);
        TextView tvClose = rootView.findViewById(R.id.tv_click_close);
        tvClose.setOnClickListener(this);
        TextView tvAppear = rootView.findViewById(R.id.tv_click_appear);
        tvAppear.setOnClickListener(this);

        mDynamicRoundTextView = rootView.findViewById(R.id.drt_round);
        mDynamicRoundTextView.setUnReadCount(1);
        mDynamicRoundTextView.setTips("1条新提示");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_click_spread:
                mDynamicRoundTextView.spread();
                break;
            case R.id.tv_click_close:
                mDynamicRoundTextView.close();
                break;
            case R.id.tv_click_appear:
                mDynamicRoundTextView.appear(null);
                break;

        }
    }
}
