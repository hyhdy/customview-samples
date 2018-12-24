package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.StrokeTextViewFragment;

/**
 * Created by hyh on 2018/12/24 15:41
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class StrokeTextViewFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new StrokeTextViewFragment();
    }
}
