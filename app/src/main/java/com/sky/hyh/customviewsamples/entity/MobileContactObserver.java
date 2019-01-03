package com.sky.hyh.customviewsamples.entity;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;
import com.sky.hyh.customviewsamples.MyApplication;
import com.sky.hyh.customviewsamples.utils.ContactUtil;
import java.util.List;

/**
 * Created by hyh on 2019/1/3 17:14
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class MobileContactObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public MobileContactObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.d("hyh", "MobileContactObserver: onChange: selfChange="+selfChange);
        List<PhoneInfo> phoneInfoList = ContactUtil.getMobileContact();
        for(PhoneInfo phoneInfo: phoneInfoList){
            Log.d("hyh", "MobileContactObserver: onChange: phoneInfo="+phoneInfo.toString());
        }
    }
}
