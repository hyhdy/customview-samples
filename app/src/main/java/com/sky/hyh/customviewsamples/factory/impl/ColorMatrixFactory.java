package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.ColorMatrixFragment;

/**
 * Created by hyh on 2018/10/27 20:47
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ColorMatrixFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new ColorMatrixFragment();
    }
}
