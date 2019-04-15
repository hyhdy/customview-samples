package com.sky.hyh.customviewsamples.factory.impl;

import android.util.Log;

import com.hyh.base_lib.factory.BaseFragmentFactory;
import com.hyh.base_lib.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.impl.CustomEmojiPanelFragment;

public class CustomEmojiPanelFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        Log.d("hyh", "CustomEmojiPanelFactory: createFragment");
        return new CustomEmojiPanelFragment();
    }
}
