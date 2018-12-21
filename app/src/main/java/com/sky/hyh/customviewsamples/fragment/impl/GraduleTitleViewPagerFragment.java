package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.customview.customviewpager.GraduleTitleViewpager;
import com.sky.hyh.customviewsamples.customview.customviewpager.ViewPagerAdapter;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.utils.InjectUtil;

import java.util.ArrayList;
import java.util.List;

public class GraduleTitleViewPagerFragment extends BaseFragment {
    @FindViewByIdAno(R.id.gtv_view_pager)
    private GraduleTitleViewpager mGraduleTitleViewpager;

    @Override
    protected int getResId() {
        return R.layout.fragment_gradule_title_view_pager;
    }

    @Override
    protected void initViews(View rootView) {
        //准备数据源
        List<String> datas=new ArrayList<>();
        datas.add("#990000");
        datas.add("#271309");
        datas.add("#213456");
        datas.add("#016716");
        datas.add("#170456");
        datas.add("#ff11ff");

        String[] titleContents = new String[datas.size()];
        for(int i=0;i<datas.size();i++){
            titleContents[i] = "哈哈"+i;
        }
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),datas);

        mGraduleTitleViewpager.setTitleContents(titleContents);
        mGraduleTitleViewpager.setAdapter(viewPagerAdapter);
    }
}
