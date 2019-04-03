package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.sky.hyh.customviewsamples.R;

/**
 * created by hyh on 2019/4/3
 */
public class CatViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvImg;

    public CatViewHolder(@NonNull View itemView) {
        super(itemView);
        mIvImg = (ImageView) itemView;
        mIvImg = itemView.findViewById(R.id.imageview);
    }

    public void setData(int drawableId){
        mIvImg.setImageResource(drawableId);
        ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
        if(layoutParams instanceof FlexboxLayoutManager.LayoutParams){
            ((FlexboxLayoutManager.LayoutParams) layoutParams).setFlexGrow(1);
        }
    }

}
