package com.sky.hyh.customviewsamples.customview.customviewpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.utils.SizeUtils;

/**
 * 通过tablayout加viewpager实现
 */
public class GraduleTitleViewpagerV2 extends RelativeLayout{
    private static final int RES_ID = R.layout.gradule_title_view_pager_layout_v2;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public GraduleTitleViewpagerV2(Context context) {
        this(context,null);
    }

    public GraduleTitleViewpagerV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,RES_ID,this);
        initViews();
    }

    private void initViews(){
        mViewPager = findViewById(R.id.vp_content);
        mTabLayout = findViewById(R.id.tablayout);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                IndicatorLineUtil.setIndicator(mTabLayout, 10,10);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager,false);
    }

    public void setAdapter(String[] titleContents,PagerAdapter pagerAdapter){
        mTabLayout.removeAllTabs();
        for(int i=0;i<titleContents.length;i++){
            mTabLayout.addTab(mTabLayout.newTab());
        }

        mViewPager.setAdapter(pagerAdapter);

        for(int i=0;i<titleContents.length;i++){
            mTabLayout.getTabAt(i).setText(titleContents[i]);
        }
    }
}
