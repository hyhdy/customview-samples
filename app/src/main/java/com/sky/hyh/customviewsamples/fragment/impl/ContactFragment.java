package com.sky.hyh.customviewsamples.fragment.impl;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.adapter.viewholder.ContactListAdapter;
import com.sky.hyh.customviewsamples.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.entity.PhoneInfo;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;
import com.sky.hyh.customviewsamples.utils.ContactUtil;
import java.util.List;

/**
 * Created by hyh on 2019/1/2 16:28
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContactFragment extends BaseFragment {
    @FindViewByIdAno(R.id.rv_contact_list)
    private RecyclerView mRvContactList;

    @Override
    protected int getResId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initViews(View rootView) {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            //申请权限  第二个参数是一个 数组 说明可以同时申请多个权限
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        } else {
            //已授权
           displayContactData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限申请成功
                displayContactData();
            } else {
                Toast.makeText(getContext(), "获取联系人的权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void displayContactData(){
        List<PhoneInfo> phoneInfoList = ContactUtil.getMobileContact();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        for(PhoneInfo phoneInfo: phoneInfoList) {
            try {
                String phoneNum = phoneInfo.getPhoneNum();
                Phonenumber.PhoneNumber phoneNumProto = phoneUtil.parse(phoneNum, "CN");
                boolean isValid = phoneUtil.isValidNumber(phoneNumProto);
                Log.d("hyh", "ContactFragment: displayContactData: isValid="+isValid+" ,phoneNum="+phoneNum);
                if (isValid) {
                    String formatPhoneNum = phoneUtil.format(phoneNumProto,
                        PhoneNumberUtil.PhoneNumberFormat.E164);
                    Log.d("hyh", "ContactMgr: run: formatPhoneNum=" + formatPhoneNum);
                }
            } catch (NumberParseException e) {
                Log.d("hyh", "ContactFragment: displayContactData: phone="+phoneInfo.getPhoneNum());
                e.printStackTrace();
            }
        }

        ContactListAdapter contactListAdapter = new ContactListAdapter(getContext(),phoneInfoList);
        mRvContactList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContactList.setAdapter(contactListAdapter);
    }
}
