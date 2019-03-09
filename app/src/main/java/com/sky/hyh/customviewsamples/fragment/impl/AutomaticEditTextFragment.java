package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.customview.automaitcEditText.AutoEditText;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

/**
 * Created by hyh on 2019/2/26 15:49
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class AutomaticEditTextFragment extends BaseFragment {
    @FindViewByIdAno(R.id.aet_input)
    private AutoEditText mAutomaticEditText;

    @Override
    protected int getResId() {
        return R.layout.fragment_automatic_edittext;
    }

    @Override
    protected void initViews(View rootView) {
        mAutomaticEditText.requestFocus();
    }
}
