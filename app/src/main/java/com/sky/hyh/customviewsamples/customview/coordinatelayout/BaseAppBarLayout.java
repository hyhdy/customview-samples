package com.sky.hyh.customviewsamples.customview.coordinatelayout;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sky.hyh.customviewsamples.R;

/**
 * created by hyh on 2019/4/4
 */
public abstract class BaseAppBarLayout extends AppBarLayout {
    private TabLayout mTabLayout;
    private CallBack mCallBack;

    public BaseAppBarLayout(Context context) {
        this(context,null);
    }

    public BaseAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View root = inflate(context,getResId(),this);
        initViews(root);
    }

    private void initViews(View view){
        mTabLayout = view.findViewById(R.id.tablayout);
    }

    public void addOffsetListener(){
        addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                //Log.d("hyh","BaseAppBarLayout: onOffsetChanged: offset="+offset);
                boolean isTop = false;
                if(Math.abs(offset) == mTabLayout.getTop()){
                    mTabLayout.setBackgroundColor(0xff9EA19F);
                    isTop = true;
                }else{
                    mTabLayout.setBackgroundColor(0xffffffff);
                }
                if(mCallBack != null){
                    mCallBack.tabArriveTop(isTop);
                }
            }
        });
    }

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack{
        void tabArriveTop(boolean isTop);
    }

    abstract int getResId();
}
