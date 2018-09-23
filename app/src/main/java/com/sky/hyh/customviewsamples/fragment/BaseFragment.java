package com.sky.hyh.customviewsamples.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private OnDestroyCallBack mOnDestroyCallBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(getResId(),null);
       initViews(rootView);
       return rootView;
    }

    protected abstract int getResId();
    protected abstract void initViews(View rootView);

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
