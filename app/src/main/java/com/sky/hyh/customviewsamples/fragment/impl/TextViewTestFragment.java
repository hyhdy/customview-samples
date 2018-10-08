package com.sky.hyh.customviewsamples.fragment.impl;

import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

/**
 * Created by hyh on 2018/10/8 13:29
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TextViewTestFragment extends BaseFragment {
    private AppCompatEditText mEtInput;
    private Button mBtnSend;
    private AppCompatTextView mTvDisplay;

    @Override
    protected int getResId() {
        return R.layout.fragment_textview_test;
    }

    @Override
    protected void initViews(View rootView) {
        mEtInput = rootView.findViewById(R.id.et_input);
        mBtnSend = rootView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEtInput.getText().toString();
                mTvDisplay.setText(text);
            }
        });
        mTvDisplay = rootView.findViewById(R.id.tv_display);
    }
}
