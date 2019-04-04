package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.CoordinatorLayoutFragment;

/**
 * created by hyh on 2019/4/4
 */
public class CoordinatorLayoutFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new CoordinatorLayoutFragment();
    }
}
