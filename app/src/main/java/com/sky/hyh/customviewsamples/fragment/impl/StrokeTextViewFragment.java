package com.sky.hyh.customviewsamples.fragment.impl;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.customview.StrokeTextView;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.utils.InjectUtil;

/**
 * Created by hyh on 2018/12/24 15:33
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class StrokeTextViewFragment extends BaseFragment {
    @FindViewByIdAno(R.id.stv_text_1)
    private StrokeTextView mStrokeTextView;
    @FindViewByIdAno(R.id.stv_text_2)
    private StrokeTextView mStrokeTextView2;
    @FindViewByIdAno(R.id.stv_text_3)
    private StrokeTextView mStrokeTextView3;

    @Override
    protected int getResId() {
        return R.layout.fragment_stroke_text_view;
    }

    @Override
    protected void initViews(View rootView) {
        InjectUtil.injectView(this);
        mStrokeTextView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "camera_addtext_font_jimu.ttf"));
        mStrokeTextView2.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "camera_addtext_font_jimu.ttf"));
        mStrokeTextView2.setLineSpacing(0,1.5f);
        mStrokeTextView2.setColor(Color.WHITE,Color.BLUE);
        mStrokeTextView3.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "camera_addtext_font_jimu.ttf"));
        mStrokeTextView3.setColor(Color.WHITE,Color.RED);
    }
}
