package com.sky.hyh.customviewsamples.factory.impl;

import android.util.Log;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.CustomEmojiPanelFragment;

public class CustomEmojiPanelFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        Log.d("hyh", "CustomEmojiPanelFactory: createFragment");
        return new CustomEmojiPanelFragment();
    }
}
