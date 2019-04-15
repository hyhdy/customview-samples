package com.sky.hyh.customviewsamples.fragment.impl;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.entity.FlexAdapter;
import com.sky.hyh.customviewsamples.entity.FlexModel;

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
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);
        mRvList.setHasFixedSize(true);
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
        //dataList.add(new FlexModel());
        mRvList.setAdapter(new FlexAdapter(dataList));
    }
}
