package com.sky.hyh.customviewsamples.customview.customviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;

/**
 * 实现滑动标题栏渐变效果的viewpager
 */
public class GraduleTitleViewpager extends RelativeLayout {
    private static final int RES_ID = R.layout.gradule_title_view_pager_layout;

    private ViewPager mViewPager;
    private ScrollTabView mScrollTabView;
    private HorizontalTitleBar mHorizontalTitleBar;

    public GraduleTitleViewpager(Context context) {
        this(context,null);
    }

    public GraduleTitleViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context,RES_ID,this);
        initViews();
    }

    private void initViews(){
        mScrollTabView = findViewById(R.id.stv_indicator);
        mHorizontalTitleBar = findViewById(R.id.ll_title);
        mViewPager = findViewById(R.id.vp_content);

        mHorizontalTitleBar.setOnClickTitleCallBack(new HorizontalTitleBar.OnClickTitleCallBack() {
            @Override
            public void onClickTitle(int pos) {
                //mViewPager.setCurrentItem(pos);
                mViewPager.setCurrentItem(pos,false);
                mScrollTabView.setOffset(pos,0);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //i滑动到第几个item，v滑动过程的偏移量（取值范围0-1)
                mScrollTabView.setOffset(i,v);

                TextView textViewCur = mHorizontalTitleBar.getTitleByPos(i);
                TextView textViewAfter = mHorizontalTitleBar.getTitleByPos(i+1);

                if(textViewCur!=null){
                    textViewCur.setAlpha(1-0.8f*v);
                }
                if(textViewAfter!=null){
                    textViewAfter.setAlpha(0.2f+0.8f*v);
                }

                if(v == 0 ){
                    mHorizontalTitleBar.setTransparentAlpha(i);
                }
            }

            @Override
            public void onPageSelected(int i) {}

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }

    public void setTitleContents(String[] titleContents){
        mHorizontalTitleBar.setTitleContent(titleContents,0);
        mScrollTabView.setTabNum(titleContents.length);
    }

    public void setAdapter(PagerAdapter pagerAdapter){
        mViewPager.setAdapter(pagerAdapter);
    }
}
