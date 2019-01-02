package com.sky.hyh.customviewsamples.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.entity.PhoneInfo;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hyh on 2019/1/2 16:51
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContactListAdapter extends RecyclerView.Adapter {
    public static final int RES_ITEM = R.layout.item_contact;
    private Context mContext;
    private List<PhoneInfo> mPhoneInfos;

    public ContactListAdapter(Context context, List<PhoneInfo> phoneInfos) {
        mContext = context;
        mPhoneInfos = phoneInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(RES_ITEM,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NormalViewHolder)holder).setViewData(mPhoneInfos.get(position));
    }

    @Override
    public int getItemCount() {
        if(mPhoneInfos!=null){
            return mPhoneInfos.size();
        }
        return 0;
    }

    public static final class NormalViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvPhone;

        public NormalViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_contact_name);
            mTvPhone = itemView.findViewById(R.id.tv_contact_phone);
        }

        protected void setViewData(PhoneInfo phoneInfo){
            if(phoneInfo == null){
                return;
            }
            mTvName.setText(String.format("%s%s","名字：",phoneInfo.getName()));
            mTvPhone.setText(String.format("%s%s","号码：",phoneInfo.getPhoneNum()));
        }
    }
}
