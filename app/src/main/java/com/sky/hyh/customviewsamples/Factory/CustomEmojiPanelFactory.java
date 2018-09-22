package com.sky.hyh.customviewsamples.Factory;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.fragment.CustomEmojiPanelFragment;

public class CustomEmojiPanelFactory extends BaseFragmentFactory {
    @Override
    public BaseFragment createFragment() {
        Log.d("hyh", "CustomEmojiPanelFactory: createFragment");
        return new CustomEmojiPanelFragment();
    }
}
