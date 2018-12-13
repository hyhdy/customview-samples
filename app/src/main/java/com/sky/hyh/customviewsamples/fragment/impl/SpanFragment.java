package com.sky.hyh.customviewsamples.fragment.impl;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.CustomSpan;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.utils.DensityUtil;

public class SpanFragment extends BaseFragment {
    private TextView mTvStr;
    private TextView mTvCount;

    @Override
    protected int getResId() {
        return R.layout.fragment_span;
    }

    @Override
    protected void initViews(View rootView) {
        mTvStr = rootView.findViewById(R.id.tv_text);
        mTvCount = rootView.findViewById(R.id.tv_count);

        String original = "89人看过";
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomSpan customSpan1 = new CustomSpan(DensityUtil.sp2px(getContext(),16), Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC), Color.WHITE,0);
        CustomSpan customSpan2 = new CustomSpan(DensityUtil.sp2px(getContext(),14), Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD), Color.WHITE,DensityUtil.dip2px(getContext(),4));
        spannableString.setSpan(customSpan1,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,2,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvStr.setText(spannableString);

        setCount();

    }

    private void setCount(){
        String original = "😃😎😂x17";
        int length = original.length();
        Log.d("hyh", "SpanFragment: setCount length="+length);
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomSpan customSpan1 = new CustomSpan(DensityUtil.sp2px(getContext(),20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,0);
        CustomSpan customSpan2 = new CustomSpan(DensityUtil.sp2px(getContext(),20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,
                DensityUtil.dip2px(getContext(),0));
        CustomSpan customSpan3 = new CustomSpan(DensityUtil.sp2px(getContext(),20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,
                DensityUtil.dip2px(getContext(),0));
        CustomSpan customSpan4 = new CustomSpan(DensityUtil.sp2px(getContext(),10),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC),
                0x99ffffff,
                DensityUtil.dip2px(getContext(),
                2));
        CustomSpan customSpan5 = new CustomSpan(DensityUtil.sp2px(getContext(),16),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC),
                Color.WHITE,
                DensityUtil.dip2px(getContext(),
                2));
        spannableString.setSpan(customSpan1,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan3,4,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan4,6,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan5,7,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvCount.setText(spannableString);
    }
}
