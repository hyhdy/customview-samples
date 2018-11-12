package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.HollowTextViewFragment;

/**
 * Created by hyh on 2018/11/12 16:25
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class HollowTextViewFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new HollowTextViewFragment();
    }
}
