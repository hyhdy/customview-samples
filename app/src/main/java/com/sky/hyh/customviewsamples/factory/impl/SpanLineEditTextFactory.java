package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.SpanLineEditTextFragment;

/**
 * Created by hyh on 2019/2/27 16:50
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class SpanLineEditTextFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new SpanLineEditTextFragment();
    }
}
