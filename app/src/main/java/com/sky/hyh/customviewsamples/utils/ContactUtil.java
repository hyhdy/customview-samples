package com.sky.hyh.customviewsamples.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.hyh.base_lib.MyApplication;
import com.sky.hyh.customviewsamples.entity.MobileContactObserver;
import com.sky.hyh.customviewsamples.entity.PhoneInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hyh on 2019/1/2 16:29
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContactUtil {
    public static String TAG = "ContactUtil";
    public static final String KEY_CONTACT_VERSION = "version";
    public static final String KEY_PHONE_NUM = "phone";
    private static MobileContactObserver sMobileContactObserver;

    /**
     * 全量获取手机联系人信息
     * @return
     */
    private static List<PhoneInfo> getMobileContactInner() {
        List list = new ArrayList<PhoneInfo>();
        Cursor cursor = null;
        ContentResolver contentResolver = MyApplication.getApplication().getContentResolver();
        if(sMobileContactObserver == null){
            Log.d("hyh", "ContactUtil: getMobileContactInner: 注册MobileContactObserver");
            sMobileContactObserver = new MobileContactObserver(null);
            contentResolver.registerContentObserver(Phone.CONTENT_URI,false,sMobileContactObserver);
        }
        try {
            cursor = contentResolver.query(Phone.CONTENT_URI,
                null, null, null, null);
            while (cursor.moveToNext()) {
                //读取通讯录的姓名
                String name = cursor.getString(cursor
                    .getColumnIndex(Phone.DISPLAY_NAME));

                //读取通讯录的号码
                String number = cursor.getString(cursor
                    .getColumnIndex(Phone.NUMBER));

                //联系人id，不过同一个联系人有可能存多个号码，所以会存在不同号码对应相同id的情况
                String contactId = cursor.getString(cursor
                    .getColumnIndex(Phone.CONTACT_ID));

                String version = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.VERSION));

                PhoneInfo phoneInfo = new PhoneInfo(contactId,version,name,number);
                list.add(phoneInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 增量获取手机联系人信息
     * @return
     */
    public static List<PhoneInfo> getMobileContactIncremental(){
        String oldContactStr = getOldMobileContact();
        Map<String,String> oldContactMap = wrapContactStrToPhoneInfo(oldContactStr);
        //待上传的联系人列表
        List<PhoneInfo> toUploadContactList = new ArrayList<>();

        List<PhoneInfo> phoneInfoList = getMobileContactInner();
        putMobileContact(wrapPhoneInfoToContactStr(phoneInfoList));
        for(PhoneInfo phoneInfo: phoneInfoList){
            if(oldContactMap.containsKey(phoneInfo.getPhoneNum())){
                String oldVersion = oldContactMap.get(phoneInfo.getPhoneNum());
                if(!TextUtils.equals(oldVersion,phoneInfo.getVersion())){
                    //版本号更新，证明联系人信息有更新，需要重新上传
                    toUploadContactList.add(phoneInfo);
                }
            }else{
                //新增的联系人需上传
                toUploadContactList.add(phoneInfo);
            }
        }
        return toUploadContactList;
    }

    /**
     * 获取手机联系人信息
     * @return
     */
    public static List<PhoneInfo> getMobileContact(){
        String oldContactStr = getOldMobileContact();
        Map<String,String> oldContactMap = wrapContactStrToPhoneInfo(oldContactStr);

        List<PhoneInfo> phoneInfoList = getMobileContactInner();
        putMobileContact(wrapPhoneInfoToContactStr(phoneInfoList));

        for(PhoneInfo phoneInfo: phoneInfoList){
            if(oldContactMap.containsKey(phoneInfo.getPhoneNum())){
                String oldVersion = oldContactMap.get(phoneInfo.getPhoneNum());
                if(!TextUtils.equals(oldVersion,phoneInfo.getVersion())){
                    phoneInfo.mChanged = true;
                }
            }else{
                phoneInfo.mChanged = true;
            }
        }
        return phoneInfoList;
    }

    private static String getOldMobileContact(){
        SharedPreferences spf = MyApplication.getApplication().getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        return spf.getString("mobile_contact","");
    }

    private static void putMobileContact(String contactStr){
        SharedPreferences spf = MyApplication.getApplication().getSharedPreferences("contact_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("mobile_contact",contactStr);
        //异步写入磁盘，如果是commit会同步写人磁盘，阻塞线程
        editor.apply();
    }

    /**
     * 将json字符串转成HashMap
     * @param contactStr
     * @return
     */
    private static Map<String, String> wrapContactStrToPhoneInfo(String contactStr){
        Map<String,String> phoneInfoMap = new HashMap<>();
        if(!TextUtils.isEmpty(contactStr)){
            try {
                JSONArray jsonArray = new JSONArray(contactStr);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject member = jsonArray.getJSONObject(i);
                    String version = member.getString(KEY_CONTACT_VERSION);
                    String phoneNum = member.getString(KEY_PHONE_NUM);
                    phoneInfoMap.put(phoneNum,version);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return phoneInfoMap;
    }

    /**
     * 将联系人信息列表转成json字符串
     * @param phoneInfoList
     * @return
     */
    private static String wrapPhoneInfoToContactStr(List<PhoneInfo> phoneInfoList){
        if(phoneInfoList == null || phoneInfoList.size() == 0){
            return "";
        }

        JSONArray jsonArray = new JSONArray();
        for(PhoneInfo phoneInfo: phoneInfoList){
            try {
                JSONObject member = new JSONObject();
                member.put(KEY_PHONE_NUM,phoneInfo.getPhoneNum());
                member.put(KEY_CONTACT_VERSION,phoneInfo.getVersion());
                jsonArray.put(member);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String result = jsonArray.toString();
        byte[] bytes = result.getBytes();
        Log.d(TAG, "wrapPhoneInfoToContactStr: bytes.length ="+bytes.length);
        return jsonArray.toString();
    }
}
