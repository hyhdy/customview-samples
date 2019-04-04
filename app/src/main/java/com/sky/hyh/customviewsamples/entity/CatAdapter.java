package com.sky.hyh.customviewsamples.entity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sky.hyh.customviewsamples.R;

import java.util.ArrayList;
import java.util.List;

import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_1;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_2;
import static com.sky.hyh.customviewsamples.constant.FlexTypeValue.ITEM_TYPE_3;

/**
 * created by hyh on 2019/4/3
 */
public class CatAdapter extends RecyclerView.Adapter {
    private List<Integer> mDataList = new ArrayList<>();
//    private int[] CAT_IMAGE_IDS = new int[]{
//            R.drawable.cat_1,
//            R.drawable.cat_2,
//            R.drawable.cat_3,
//            R.drawable.cat_4,
//            R.drawable.cat_5,
//            R.drawable.cat_6,
//            R.drawable.cat_7,
//            R.drawable.cat_8,
//            R.drawable.cat_9,
//            R.drawable.cat_10,
//            R.drawable.cat_11,
//            R.drawable.cat_12,
//            R.drawable.cat_13,
//            R.drawable.cat_14,
//            R.drawable.cat_15,
//            R.drawable.cat_16,
//            R.drawable.cat_17,
//            R.drawable.cat_18,
//            R.drawable.cat_19
//    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.viewholder_cat, viewGroup, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((CatViewHolder)viewHolder).setData(mDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(List<Integer> mDataList) {
        this.mDataList = mDataList;
    }
}
