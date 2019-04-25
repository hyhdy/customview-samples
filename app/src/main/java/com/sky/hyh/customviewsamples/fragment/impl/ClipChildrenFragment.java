package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;

import com.hyh.base_lib.BaseFragment;
import com.sky.hyh.customviewsamples.R;

/**
 * 1.android:clipChildren必须设置在祖父布局上，也就是要想让某个view在该view的父布局外显示，那么clipChildren需要在该view的父布局的父布局设置，也就是他的祖父布局上。
 * 2.android:clipChildren只有在祖父布局为其父布局有留下足够的显示空间才能生效。比如某个view的父布局高度是50dp，他的祖父布局高度也是50dp，那么设置android:clipChildren属性后
 * 也是没办法让该veiw在父布局之外显示的，因为其父布局之外已经没有其他空间了。
 */
public class ClipChildrenFragment extends BaseFragment {
    @Override
    protected int getResId() {
        return R.layout.fragment_clipchildren;
    }

    @Override
    protected void initViews(View rootView) {

    }
}
