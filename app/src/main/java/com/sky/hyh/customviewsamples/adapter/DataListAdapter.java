package com.sky.hyh.customviewsamples.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.factory.impl.CustomEmojiPanelFactory;
import com.sky.hyh.customviewsamples.factory.impl.DynamicRoundTextViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.ClipChildrenFactory;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.adapter.viewholder.BaseViewHolder;
import com.sky.hyh.customviewsamples.factory.impl.GlowTextViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.LimitedEditTextFactory;
import com.sky.hyh.customviewsamples.factory.impl.RoundMaskViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.TextViewTestFactory;

import java.util.ArrayList;
import java.util.List;

public class DataListAdapter extends RecyclerView.Adapter {
    public static final int ITEM_RES_ID = R.layout.item_layout;
    private static List<Class> sDataList = new ArrayList<>();
    static {
        sDataList.add(CustomEmojiPanelFactory.class);
        sDataList.add(DynamicRoundTextViewFactory.class);
        sDataList.add(ClipChildrenFactory.class);
        sDataList.add(RoundMaskViewFactory.class);
        sDataList.add(LimitedEditTextFactory.class);
        sDataList.add(TextViewTestFactory.class);
        sDataList.add(GlowTextViewFactory.class);
    }
    private OnClickCallBack mOnClickCallBack;
    private Context mContext;
    public DataListAdapter(Context context,OnClickCallBack onClickCallBack) {
        mContext = context;
        mOnClickCallBack = onClickCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NormalViewHolder(LayoutInflater.from(mContext).inflate(ITEM_RES_ID,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((NormalViewHolder)viewHolder).setData(sDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return sDataList.size();
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
