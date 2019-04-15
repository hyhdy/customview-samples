package com.sky.hyh.customviewsamples.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CachedProxy implements InvocationHandler {
    protected Object mSubject;

    public Object bind(Object subject){
        mSubject = subject;
        return Proxy.newProxyInstance(subject.getClass().getClassLoader(),
                subject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //做cache操作
        result = method.invoke(mSubject,args);
        return result;
    }
}
