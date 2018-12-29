package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.ConLimitedEditTextFragment;

public class ConLimitedEditTextFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new ConLimitedEditTextFragment();
    }
}
