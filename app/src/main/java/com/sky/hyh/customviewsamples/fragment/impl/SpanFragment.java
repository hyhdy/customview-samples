package com.sky.hyh.customviewsamples.fragment.impl;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.span.CustomTextSpan;
import com.sky.hyh.customviewsamples.utils.DensityUtil;
import com.sky.hyh.customviewsamples.utils.InjectUtil;

public class SpanFragment extends BaseFragment {
    @FindViewByIdAno(R.id.tv_tip1)
    private TextView mTvTip1;
    @FindViewByIdAno(R.id.tv_show1)
    private TextView mTvShow1;
    @FindViewByIdAno(R.id.tv_tip2)
    private TextView mTvTip2;
    @FindViewByIdAno(R.id.tv_show2)
    private TextView mTvShow2;
    @FindViewByIdAno(R.id.tv_tip3)
    private TextView mTvTip3;
    @FindViewByIdAno(R.id.tv_show3)
    private TextView mTvShow3;

    @Override
    protected int getResId() {
        return R.layout.fragment_span;
    }

    @Override
    protected void initViews(View rootView) {
        setShow1();
        setShow2();
        setShow3();
    }

    private void setShow1(){
        String original = "第一段文字第二段文字第三段文字";
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomTextSpan customSpan1 = new CustomTextSpan(DensityUtil.sp2px(15),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,0,CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan2 = new CustomTextSpan(DensityUtil.sp2px(15),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD),
                Color.RED, 0,CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan3 = new CustomTextSpan(DensityUtil.sp2px(15),
                Typeface.createFromAsset(getContext().getAssets(), "HYTangMeiRenJ-2.ttf"),
                Color.YELLOW, 0,CustomTextSpan.ALIGN_CENTER);

        spannableString.setSpan(customSpan1,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,5,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan3,10,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvShow1.setText(spannableString);
    }

    private void setShow2(){
        String original = "第一段文字第二段文字第三段文字";
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomTextSpan customSpan1 = new CustomTextSpan(DensityUtil.sp2px(10),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,DensityUtil.dip2px(4),CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan2 = new CustomTextSpan(DensityUtil.sp2px(20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD),
                Color.RED, DensityUtil.dip2px(8),CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan3 = new CustomTextSpan(DensityUtil.sp2px(10),
                Typeface.createFromAsset(getContext().getAssets(), "HYTangMeiRenJ-2.ttf"),
                Color.YELLOW, 0,CustomTextSpan.ALIGN_CENTER);

        spannableString.setSpan(customSpan1,0,5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,5,10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan3,10,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvShow2.setText(spannableString);
    }

    private void setShow3(){
        String original = "😃😎😂文字表情居中";
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomTextSpan customSpan1 = new CustomTextSpan(DensityUtil.sp2px(15),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,0,CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan2 = new CustomTextSpan(DensityUtil.sp2px(15),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD),
                Color.RED, 0,CustomTextSpan.ALIGN_CENTER);

        spannableString.setSpan(customSpan1,0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,6,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvShow3.setText(spannableString);
    }

    private void setCount(){
        String original = "😃😎😂x17";
        int length = original.length();
        Log.d("hyh", "SpanFragment: setCount length="+length);
        char EXTRA_SPACE = ' ';
        original = String.format("%s%s",original,EXTRA_SPACE);
        SpannableString spannableString = new SpannableString(original);
        CustomTextSpan customSpan1 = new CustomTextSpan(DensityUtil.sp2px(20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,0,CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan2 = new CustomTextSpan(DensityUtil.sp2px(20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,
                DensityUtil.dip2px(-6),CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan3 = new CustomTextSpan(DensityUtil.sp2px(20),
                Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL),
                Color.WHITE,
                DensityUtil.dip2px(-6),CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan4 = new CustomTextSpan(DensityUtil.sp2px(10),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC),
                0x99ffffff,
                DensityUtil.dip2px(10), CustomTextSpan.ALIGN_CENTER);
        CustomTextSpan customSpan5 = new CustomTextSpan(DensityUtil.sp2px(16),
                Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD_ITALIC),
                Color.WHITE,
                DensityUtil.dip2px(2),CustomTextSpan.ALIGN_CENTER);
        spannableString.setSpan(customSpan1,0,2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan2,2,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan3,4,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan4,6,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(customSpan5,7,original.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
