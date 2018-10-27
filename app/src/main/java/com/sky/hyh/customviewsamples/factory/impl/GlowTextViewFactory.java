package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.GlowTextViewFragment;

/**
 * Created by hyh on 2018/10/27 17:14
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class GlowTextViewFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new GlowTextViewFragment();
    }
}
