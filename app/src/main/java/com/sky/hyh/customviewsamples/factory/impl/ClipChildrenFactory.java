package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.ClipChildrenFragment;

/**
 * Created by hyh on 2018/9/25 14:05
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ClipChildrenFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new ClipChildrenFragment();
    }
}
