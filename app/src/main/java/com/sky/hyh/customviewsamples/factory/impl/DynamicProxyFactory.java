package com.sky.hyh.customviewsamples.factory.impl;

import com.hyh.base_lib.factory.BaseFragmentFactory;
import com.hyh.base_lib.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.DynamicProxyFragment;

public class DynamicProxyFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new DynamicProxyFragment();
    }
}
