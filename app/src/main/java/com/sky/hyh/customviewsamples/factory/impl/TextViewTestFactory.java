package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.TextViewTestFragment;

/**
 * Created by hyh on 2018/10/8 13:42
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TextViewTestFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new TextViewTestFragment();
    }
}
