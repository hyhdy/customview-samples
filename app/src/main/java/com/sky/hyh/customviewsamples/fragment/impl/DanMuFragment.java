package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.widget.FrameLayout;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.danmu.BarrageDispatch;
import com.sky.hyh.customviewsamples.danmu.BarrageWay;

@InjectFragment()
public class DanMuFragment extends BaseFragment {
    BarrageDispatch mDispatch;

    @Override
    protected int getResId() {
        return R.layout.fragment_danmu;
    }

    @Override
    protected void initViews(View rootView) {
        FrameLayout way1 = rootView.findViewById(R.id.fl_barrage_way_1);
        FrameLayout way2 = rootView.findViewById(R.id.fl_barrage_way_2);
        FrameLayout way3 = rootView.findViewById(R.id.fl_barrage_way_3);

        mDispatch = new BarrageDispatch(getContext());
        mDispatch.addBarrageWay(way1);
        mDispatch.addBarrageWay(way2);
        mDispatch.addBarrageWay(way3);

        rootView.findViewById(R.id.btn_add_danmu1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarrageWay.BarrageItem item = new BarrageWay.BarrageItem("小姐姐哈哈哈","");
                mDispatch.dispatch(item);
            }
        });

        rootView.findViewById(R.id.btn_add_danmu2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarrageWay.BarrageItem item = new BarrageWay.BarrageItem("小姐姐哈哈哈","1111");
                mDispatch.dispatch(item);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDispatch.clear();
    }
}
