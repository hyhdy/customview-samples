package com.sky.hyh.customviewsamples.Factory;

import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.ClipChildrenFragment;

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
