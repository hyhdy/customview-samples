package com.sky.hyh.customviewsamples.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.sky.hyh.customviewsamples.entity.PhoneInfo;
import java.util.ArrayList;
import java.util.List;
import android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * Created by hyh on 2019/1/2 16:29
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContactUtil {

    public static List<PhoneInfo> getMobileContact(Context context) {
        List list = new ArrayList<PhoneInfo>();
        Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
            null, null, null, null);

        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                .getColumnIndex(Phone.DISPLAY_NAME));

            //读取通讯录的号码
            String number = cursor.getString(cursor
                .getColumnIndex(Phone.NUMBER));

            PhoneInfo phoneInfo = new PhoneInfo(name, number);
            list.add(phoneInfo);
        }
        return list;
    }

}
