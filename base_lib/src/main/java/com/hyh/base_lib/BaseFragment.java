package com.hyh.base_lib;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyh.base_lib.utils.InjectUtil;

public abstract class BaseFragment extends Fragment {
    private OnDestroyCallBack mOnDestroyCallBack;
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResId(), null);
        InjectUtil.injectView(this);
        initViews(mRootView);
        return mRootView;
    }

    protected abstract int getResId();

    protected abstract void initViews(View rootView);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOnDestroyCallBack != null) {
            mOnDestroyCallBack.onDestroy();
        }
    }

    public void setOnDestroyCallBack(OnDestroyCallBack onDestroyCallBack) {
        mOnDestroyCallBack = onDestroyCallBack;
    }

    public interface OnDestroyCallBack {
        void onDestroy();
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }
}
