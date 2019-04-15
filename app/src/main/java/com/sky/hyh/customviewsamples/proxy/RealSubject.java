package com.sky.hyh.customviewsamples.proxy;

public class RealSubject implements Subject {
    @Override
    public String operationA() {
        return "this is operationA";
    }

    @Override
    public String operationB() {
        return "this is operationB";
    }

    @Override
    public String operationC() {
        return "this is operationC";
    }
}
