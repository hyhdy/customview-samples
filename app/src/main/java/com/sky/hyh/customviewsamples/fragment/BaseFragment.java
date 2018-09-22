package com.sky.hyh.customviewsamples.fragment;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private OnDestroyCallBack mOnDestroyCallBack;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mOnDestroyCallBack!=null){
            mOnDestroyCallBack.onDestroy();
        }
    }

    public void setOnDestroyCallBack(OnDestroyCallBack onDestroyCallBack) {
        mOnDestroyCallBack = onDestroyCallBack;
    }

    public interface OnDestroyCallBack{
        void onDestroy();
    }
}
