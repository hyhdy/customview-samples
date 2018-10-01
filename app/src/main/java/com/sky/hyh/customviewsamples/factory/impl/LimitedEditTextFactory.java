package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.LimitedEditTextFragment;

public class LimitedEditTextFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new LimitedEditTextFragment();
    }
}
