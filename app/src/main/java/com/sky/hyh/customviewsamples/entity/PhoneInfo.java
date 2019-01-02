package com.sky.hyh.customviewsamples.entity;

/**
 * Created by hyh on 2019/1/2 16:31
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class PhoneInfo {
    private String mContactId;
    /**
     * 联系人版本，联系人信息更改版本也会改变，根据该字段来判断联系人信息是否更新
     */
    private String mVersion;
    private String mName;
    private String mPhoneNum;
    public boolean mChanged;

    public PhoneInfo(String version, String phoneNum) {
        mVersion = version;
        mPhoneNum = phoneNum;
    }

    public PhoneInfo(String contactId, String version, String name, String phoneNum) {
        mContactId = contactId;
        mVersion = version;
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

    public String getContactId() {
        return mContactId;
    }

    public void setContactId(String contactId) {
        mContactId = contactId;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }
}
