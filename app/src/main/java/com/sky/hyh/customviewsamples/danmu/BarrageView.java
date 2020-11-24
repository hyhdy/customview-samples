package com.sky.hyh.customviewsamples.danmu;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyh.base_lib.utils.SizeUtils;
import com.sky.hyh.customviewsamples.R;

/**
 * created by curdyhuang on 2020/11/24
 * 弹幕控件
 */
public class BarrageView extends RelativeLayout {
    public static final int RES_ID = R.layout.item_danmu;
    private ImageView mIvHead;
    private TextView mTvContent;

    public BarrageView(Context context) {
        this(context,null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews(){
        mIvHead = findViewById(R.id.iv_head);
        mTvContent = findViewById(R.id.tv_content);
    }

    public void setData(BarrageWay.BarrageItem item){
        if(item == null){
            return;
        }
        int dp35 = SizeUtils.dp2px(35);
        int dp8 = SizeUtils.dp2px(8);
        mTvContent.setText(item.getContent());
        if(TextUtils.isEmpty(item.getIcon())){
            mIvHead.setVisibility(GONE);
            mTvContent.setPadding(dp8,getPaddingTop(),dp8,getPaddingBottom());
        }else{
            mIvHead.setVisibility(VISIBLE);
            mTvContent.setPadding(dp35,getPaddingTop(),dp8,getPaddingBottom());
        }
    }
}
