package com.sky.hyh.customviewsamples.fragment.impl;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.entity.CatAdapter;
import com.sky.hyh.customviewsamples.entity.FlexAdapter;
import com.sky.hyh.customviewsamples.entity.FlexModel;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hyh on 2019/4/3
 */
public class FlexBoxLayoutManagerFragment extends BaseFragment {
    @FindViewByIdAno(R.id.rv_list)
    private RecyclerView mRvList;

    @Override
    protected int getResId() {
        return R.layout.fragment_flexboxlayout_manager;
    }

    @Override
    protected void initViews(View rootView) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRvList.setLayoutManager(layoutManager);
        List<FlexModel> dataList = new ArrayList<>();
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        dataList.add(new FlexModel());
        mRvList.setAdapter(new FlexAdapter(dataList));
    }
}
