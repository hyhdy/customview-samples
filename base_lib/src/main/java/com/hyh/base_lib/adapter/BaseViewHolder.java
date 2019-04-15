package com.hyh.base_lib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView.ViewHolder的模板基类
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        initViews();
        initListener();
    }

    abstract protected void initViews();
    abstract protected void initListener();
}
