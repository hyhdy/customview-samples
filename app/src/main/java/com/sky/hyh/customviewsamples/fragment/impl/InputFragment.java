package com.sky.hyh.customviewsamples.fragment.impl;

import android.text.TextUtils;
import android.view.View;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.customview.ConLimitedLinesEditText;
import com.sky.hyh.customviewsamples.customview.HappyNewYear2019;
import com.sky.hyh.customviewsamples.customview.customviewpager.HorizontalTitleBar;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

public class InputFragment extends BaseFragment {
    @FindViewByIdAno(R.id.et_input)
    ConLimitedLinesEditText mConLimitedLinesEditText;

    @Override
    protected int getResId() {
        return R.layout.fragment_input;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!TextUtils.isEmpty(mConLimitedLinesEditText.getText().toString())){
            HappyNewYear2019.setContent(mConLimitedLinesEditText.getText().toString());
        }
    }
}
