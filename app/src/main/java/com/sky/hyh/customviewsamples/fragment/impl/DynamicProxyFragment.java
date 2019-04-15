package com.sky.hyh.customviewsamples.fragment.impl;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.hyh.base_lib.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.proxy.ConsumeProxy;
import com.sky.hyh.customviewsamples.proxy.RealSubject;
import com.sky.hyh.customviewsamples.proxy.Subject;
import com.sky.hyh.customviewsamples.proxy.CachedProxy;

public class DynamicProxyFragment extends BaseFragment implements View.OnClickListener {
    @FindViewByIdAno(R.id.proxy_a)
    private TextView mTvProxyA;
    @FindViewByIdAno(R.id.proxy_b)
    private TextView mTvProxyB;

    @Override
    protected int getResId() {
        return R.layout.fragment_dynamic_proxy;
    }

    @Override
    protected void initViews(View rootView) {
        mTvProxyA.setOnClickListener(this);
        mTvProxyB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //缓存代理
        CachedProxy proxy = new CachedProxy();
        Subject subject = (Subject) proxy.bind(new RealSubject());
        Log.e("Shawn", subject.operationA());
        Log.e("Shawn", subject.operationB());
        Log.e("Shawn", subject.operationC());

        //计算耗时代理
        ConsumeProxy consumeProxy = new ConsumeProxy();
        subject = (Subject) consumeProxy.bind(new RealSubject());
        Log.e("Shawn", subject.operationA());
        Log.e("Shawn", subject.operationB());
        Log.e("Shawn", subject.operationC());
    }
}
