package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.sky.hyh.customviewsamples.constant.FlexTypeValue;
import com.sky.hyh.customviewsamples.utils.SizeUtils;

/**
 * created by hyh on 2019/4/3
 */
public class FlexViewHolder extends RecyclerView.ViewHolder {
    private ImageView mIvImg;

    public FlexViewHolder(@NonNull View itemView) {
        super(itemView);
        mIvImg = (ImageView) itemView;
    }

    public void setData(FlexModel flexModel,int itemType){
        //mIvImg.setImageResource(drawableId);
        ViewGroup.LayoutParams layoutParams = mIvImg.getLayoutParams();
        if(layoutParams instanceof FlexboxLayoutManager.LayoutParams){
            FlexboxLayoutManager.LayoutParams lp = (FlexboxLayoutManager.LayoutParams) layoutParams;
            if(itemType == FlexTypeValue.ITEM_TYPE_1){
//                lp.setWidth(SizeUtils.dp2px(198));
//                lp.setHeight(SizeUtils.dp2px(355));
                lp.setFlexBasisPercent(0.55f);
            }else if(itemType == FlexTypeValue.ITEM_TYPE_2){
//                lp.setWidth(SizeUtils.dp2px(133));
//                lp.setHeight(SizeUtils.dp2px(218));
                lp.setFlexBasisPercent(0.61f);
            }else if(itemType == FlexTypeValue.ITEM_TYPE_3){
//                lp.setWidth(SizeUtils.dp2px(133));
//                lp.setHeight(SizeUtils.dp2px(133));
                lp.setFlexBasisPercent(1);
            }
        }
    }

}
