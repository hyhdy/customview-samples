package com.sky.hyh.customviewsamples.Factory;

import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.DynamicRoundTextViewFragment;

public class DynamicRoundTextViewFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new DynamicRoundTextViewFragment();
    }
}
