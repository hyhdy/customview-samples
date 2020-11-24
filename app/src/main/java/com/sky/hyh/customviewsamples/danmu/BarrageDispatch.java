package com.sky.hyh.customviewsamples.danmu;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sky.hyh.customviewsamples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹幕分发器，用于控制弹幕该发往哪个弹道
 */
public class BarrageDispatch {
    private int mIndex = -1;
    private List<BarrageWay> mBarrageWayList = new ArrayList<>();
    private BarrageViewFactory mFactory;//弹幕工厂

    public BarrageDispatch(Context context) {
        mFactory = new BarrageViewFactory(context);
    }

    /**
     * 添加弹道
     */
    public void addBarrageWay(ViewGroup group){
        mBarrageWayList.add(new BarrageWay(group,mFactory));
    }

    /**
     * 弹幕分发
     */
    public void dispatch(BarrageWay.BarrageItem item){
        if(item == null||mBarrageWayList.size()<=0){
            return;
        }
        mIndex++;
        int size = mBarrageWayList.size();
        BarrageWay way = mBarrageWayList.get(mIndex%size);
        way.addBarrage(item);
    }

    public void clear(){
        for (BarrageWay way: mBarrageWayList){
            way.clear();
        }
        mFactory.clear();
    }
}
