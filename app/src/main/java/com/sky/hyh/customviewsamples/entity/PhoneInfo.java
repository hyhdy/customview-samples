package com.sky.hyh.customviewsamples.entity;

/**
 * Created by hyh on 2019/1/2 16:31
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class PhoneInfo {
    private String mName;
    private String mPhoneNum;

    public PhoneInfo(String name, String phoneNum) {
        mName = name;
        mPhoneNum = phoneNum;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        mPhoneNum = phoneNum;
    }
}
