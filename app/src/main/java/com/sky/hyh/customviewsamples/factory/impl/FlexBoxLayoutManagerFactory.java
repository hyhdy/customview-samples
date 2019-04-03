package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.FlexBoxLayoutManagerFragment;

/**
 * created by hyh on 2019/4/3
 */
public class FlexBoxLayoutManagerFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new FlexBoxLayoutManagerFragment();
    }
}
