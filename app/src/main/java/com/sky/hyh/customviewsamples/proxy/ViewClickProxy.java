package com.sky.hyh.customviewsamples.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ViewClickProxy implements InvocationHandler {
    private Object mObj;

    public Object bind(Object obj){
        mObj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("onClick")){
            Log.d("hyh","ViewClickProxy: invoke: 点击view");
        }
        Object result = null;
        result = method.invoke(mObj,args);
        return result;
    }
}
