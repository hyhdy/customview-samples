package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.entity.CatAdapter;

import java.util.List;

/**
 * created by hyh on 2019/4/4
 */
public class RecyclerPagerItem extends FrameLayout {
    public static final int RES_ID = R.layout.layout_recycler_pager_item;
    private RecyclerView mRvList;
    private CatAdapter mCatAdapter;

    public RecyclerPagerItem(Context context) {
        this(context,null);
    }

    public RecyclerPagerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View root = LayoutInflater.from(context).inflate(RES_ID,this);
        initViews(root);
    }

    private void initViews(View view){
        mRvList = view.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCatAdapter = new CatAdapter();
        mRvList.setAdapter(mCatAdapter);
    }

    public void setList(List<Integer> dataList){
        mCatAdapter.setDataList(dataList);
        mCatAdapter.notifyDataSetChanged();
    }
}
