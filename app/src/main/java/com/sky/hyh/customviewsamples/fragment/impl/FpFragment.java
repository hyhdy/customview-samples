package com.sky.hyh.customviewsamples.fragment.impl;

import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.fans.FpSpan;

//@InjectFragment()
public class FpFragment extends BaseFragment implements View.OnClickListener {
    @FindViewByIdAno(R.id.tv_f)
    private TextView mTvf;
    private FpSpan.RequestParam mParam;
    private FpSpan mSpan;

    @Override
    protected int getResId() {
        return R.layout.fragment_f;
    }

    @Override
    protected void initViews(View rootView) {
        rootView.findViewById(R.id.btn_normal).setOnClickListener(this);
        rootView.findViewById(R.id.btn_s1).setOnClickListener(this);
        rootView.findViewById(R.id.btn_s2).setOnClickListener(this);
        rootView.findViewById(R.id.btn_s3).setOnClickListener(this);
        rootView.findViewById(R.id.btn_l).setOnClickListener(this);

        String name = "哎呦喂哈哈";
        mParam = new FpSpan.RequestParam(20, FpSpan.RequestParam.FType.Style1,name);
        mSpan = new FpSpan(getContext(),mParam);
        SpannableString spannableString = new SpannableString(name);
        spannableString.setSpan(mSpan,0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvf.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_normal: {
                mParam.mType = FpSpan.RequestParam.FType.Normal;
                mSpan = new FpSpan(getContext(),mParam);
                SpannableString spannableString = new SpannableString(mParam.mName);
                spannableString.setSpan(mSpan,0,mParam.mName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvf.setText(spannableString);
            }
            break;
            case R.id.btn_s1: {
                mParam.mType = FpSpan.RequestParam.FType.Style1;
                mSpan = new FpSpan(getContext(),mParam);
                SpannableString spannableString = new SpannableString(mParam.mName);
                spannableString.setSpan(mSpan,0,mParam.mName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvf.setText(spannableString);
            }
            break;
            case R.id.btn_s2: {
                mParam.mType = FpSpan.RequestParam.FType.Style2;
                mSpan = new FpSpan(getContext(),mParam);
                SpannableString spannableString = new SpannableString(mParam.mName);
                spannableString.setSpan(mSpan,0,mParam.mName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvf.setText(spannableString);
            }
            break;
            case R.id.btn_s3: {
                mParam.mType = FpSpan.RequestParam.FType.Style3;
                mSpan = new FpSpan(getContext(),mParam);
                SpannableString spannableString = new SpannableString(mParam.mName);
                spannableString.setSpan(mSpan,0,mParam.mName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvf.setText(spannableString);
            }
            break;
            case R.id.btn_l: {
                mParam.mLevel++;
                mSpan = new FpSpan(getContext(),mParam);
                SpannableString spannableString = new SpannableString(mParam.mName);
                spannableString.setSpan(mSpan,0,mParam.mName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTvf.setText(spannableString);
            }
            break;
            default:
                break;
        }
    }
}
