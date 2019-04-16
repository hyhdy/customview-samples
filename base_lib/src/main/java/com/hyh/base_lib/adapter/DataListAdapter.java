package com.hyh.base_lib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyh.base_lib.R;
import com.hyh.base_lib.BaseFragmentFactory;

import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter {
    public static final int ITEM_RES_ID = R.layout.item_layout;
    private List<Class> mDataList;

    private OnClickCallBack mOnClickCallBack;
    private Context mContext;
    public DataListAdapter(Context context,OnClickCallBack onClickCallBack,List<Class> dataList) {
        mContext = context;
        mOnClickCallBack = onClickCallBack;
        mDataList = dataList;
    }

    public void setDataList(List<Class> dataList) {
        mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(ITEM_RES_ID,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((NormalViewHolder)viewHolder).setData(mDataList.get(i));
    }

    @Override
    public int getItemCount() {
        if(mDataList == null){
            return 0;
        }
        return mDataList.size();
    }

    private class NormalViewHolder extends BaseViewHolder{
        private TextView mTvname;
        private BaseFragmentFactory mBaseFragmentFactory;

        public NormalViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initViews() {
            mTvname = itemView.findViewById(R.id.tv_name);
        }

        @Override
        protected void initListener() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnClickCallBack!=null){
                        mOnClickCallBack.onClick(mBaseFragmentFactory);
                    }
                }
            });
        }

        protected void setData(Class factory){
            try {
                mBaseFragmentFactory = (BaseFragmentFactory) factory.newInstance();
                String className = mBaseFragmentFactory.getClass().getName();
                int start = className.lastIndexOf(".") + 1;
                int end = className.lastIndexOf("Factory");
                String name = className.substring(start,end);
                mTvname.setText(name);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnClickCallBack{
        void onClick(BaseFragmentFactory baseFragmentFactory);
    }
}
