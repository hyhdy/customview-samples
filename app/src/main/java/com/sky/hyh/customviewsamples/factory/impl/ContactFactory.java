package com.sky.hyh.customviewsamples.factory.impl;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.ContactFragment;

/**
 * Created by hyh on 2019/1/2 16:29
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContactFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        return new ContactFragment();
    }
}
