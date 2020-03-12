package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.TextView;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.ratinggridbar.RatingGridView;

/**
 * created by curdyhuang on 2020-03-12
 */
@InjectFragment()
public class RatingGridViewFragment extends BaseFragment {
    @FindViewByIdAno(R.id.rgv_progress)
    private RatingGridView mRatingGridView;
    @FindViewByIdAno(R.id.tv_add_level)
    private TextView mTvAddLevel;
    private float mRate;

    @Override
    protected int getResId() {
        return R.layout.fragment_rating_grid_view;
    }

    @Override
    protected void initViews(View rootView) {
        mTvAddLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRate += 0.1f;
                mRatingGridView.setRate(mRate);
            }
        });
    }
}
