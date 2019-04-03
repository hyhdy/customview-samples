package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sky.hyh.customviewsamples.R;

import java.util.List;

import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_1;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_2;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_3;

/**
 * created by hyh on 2019/4/3
 */
public class CatAdapter extends RecyclerView.Adapter {

    public static final int FLEX_COUNT_PER_ITEM = 3;
    private int[] CAT_IMAGE_IDS = new int[]{
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
            R.drawable.cat_5,
            R.drawable.cat_6,
            R.drawable.cat_7,
            R.drawable.cat_8,
            R.drawable.cat_9,
            R.drawable.cat_10,
            R.drawable.cat_11,
            R.drawable.cat_12,
            R.drawable.cat_13,
            R.drawable.cat_14,
            R.drawable.cat_15,
            R.drawable.cat_16,
            R.drawable.cat_17,
            R.drawable.cat_18,
            R.drawable.cat_19
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_cat, viewGroup, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((CatViewHolder)viewHolder).setData(CAT_IMAGE_IDS[i]);
    }

    @Override
    public int getItemCount() {
        return CAT_IMAGE_IDS.length;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_TYPE_1;

        int i = position / FLEX_COUNT_PER_ITEM;
        int j = position % FLEX_COUNT_PER_ITEM;
        if(i % 2 == 0){
            if(j == 0){
                type = ITEM_TYPE_1;
            }else if(j == 1){
                type = ITEM_TYPE_2;
            }else if(j == 2){
                type = ITEM_TYPE_3;
            }
        }else{
            if(j == 0){
                type = ITEM_TYPE_3;
            }else if(j == 1){
                type = ITEM_TYPE_1;
            }else if(j == 2){
                type = ITEM_TYPE_2;
            }
        }

        return type;
    }


    private int getLayoutId(int type){
        int layoutId = R.layout.flex_layout_1;

        if(type == ITEM_TYPE_1){
            layoutId = R.layout.flex_layout_1;
        }else if(type == ITEM_TYPE_2){
            layoutId = R.layout.flex_layout_2;
        }else if(type == ITEM_TYPE_3){
            layoutId = R.layout.flex_layout_3;
        }

        return layoutId;
    }
}
