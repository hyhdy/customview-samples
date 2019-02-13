package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.WrappedContainerFragment;

/**
 * Created by hyh on 2019/2/13 21:04
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class WrappedContainerFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new WrappedContainerFragment();
    }
}
