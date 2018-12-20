package com.sky.hyh.customviewsamples.customview.customviewpager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalTitleBar extends LinearLayout {
    private List<TextView> mTitleViewList;
    private Context mContext;
    private OnClickTitleCallBack mOnClickTitleCallBack;

    public HorizontalTitleBar(Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        mTitleViewList = new ArrayList<>();
    }

    /**
     * 设置标题栏所有标题
     * @param titleContents
     * @param defSelectdPos：默认选择的标题
     */
    public void setTitleContent(String[] titleContents, int defSelectdPos) {
        if(titleContents == null){
            return;
        }

        for(int i=0;i<titleContents.length;i++){
            TextView textView = new TextView(mContext);
            textView.setText(titleContents[i]);
            textView.setTextSize(14);
            textView.setTextColor(Color.parseColor("#212121"));
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            if(i == defSelectdPos){
                textView.setAlpha(1);
            }else{
                textView.setAlpha(0.2f);
            }
            textView.setGravity(Gravity.CENTER);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            textView.setLayoutParams(layoutParams);
            addView(textView);
            mTitleViewList.add(textView);

            final int tempI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnClickTitleCallBack!=null){
                        mOnClickTitleCallBack.onClickTitle(tempI);
                    }
                }
            });
        }
    }

    @Nullable
    public TextView getTitleByPos(int pos){
        if(pos < 0 ||pos > (mTitleViewList.size()-1)){
            return null;
        }
        return mTitleViewList.get(pos);
    }

    public void setTransparentAlpha(int exceptPos){
        for(int i=0;i<mTitleViewList.size();i++){
            if(i!=exceptPos){
                mTitleViewList.get(i).setAlpha(0.2f);
            }else{
                mTitleViewList.get(i).setAlpha(1);
            }
        }
    }

    public void setOnClickTitleCallBack(
        OnClickTitleCallBack onClickTitleCallBack) {
        mOnClickTitleCallBack = onClickTitleCallBack;
    }

    public interface OnClickTitleCallBack{
        void onClickTitle(int pos);
    }
}
