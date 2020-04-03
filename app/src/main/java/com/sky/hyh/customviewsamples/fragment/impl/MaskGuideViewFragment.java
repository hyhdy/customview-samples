package com.sky.hyh.customviewsamples.fragment.impl;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.maskguide.GestureAnimGuideView;
import com.sky.hyh.customviewsamples.customview.maskguide.MaskGuideView;

/**
 * created by curdyhuang on 2019-10-21
 */
@InjectFragment()
public class MaskGuideViewFragment extends BaseFragment {
    @FindViewByIdAno(R.id.rl_root)
    private RelativeLayout mRlRoot;
    @FindViewByIdAno(R.id.tv_target)
    private TextView mTvTarget;
    @FindViewByIdAno(R.id.tv_target2)
    private TextView mTvTarget2;
    @FindViewByIdAno(R.id.tv_add)
    private TextView mTvAdd;
    private MaskGuideView mMaskGuideView;
    private GestureAnimGuideView mGestureAnimGuideView;
    private int mIndex;

    @Override
    protected int getResId() {
        return R.layout.fragment_maskguideview;
    }

    @Override
    protected void initViews(View rootView) {
        mTvTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"点击了我",Toast.LENGTH_SHORT).show();
            }
        });
        mTvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMaskGuideView == null) {
                    mMaskGuideView = new MaskGuideView.Builder(getContext())
                            .setFreeze(false)
                            .setOnTouchCallBack(new MaskGuideView.OnTouchCallBack() {
                                @Override
                                public void onTouch() {
                                    mRlRoot.removeView(mMaskGuideView);
                                    mGestureAnimGuideView.destroy();
                                    mRlRoot.removeView(mGestureAnimGuideView);
                                    mMaskGuideView = null;
                                    mGestureAnimGuideView = null;
                                }
                            }).build();
                    mRlRoot.addView(mMaskGuideView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mGestureAnimGuideView = new GestureAnimGuideView(getContext());
                    mRlRoot.addView(mGestureAnimGuideView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
                if(mIndex%2==0){
                    mMaskGuideView.attachTarget(mTvTarget);
                    mGestureAnimGuideView.attachTarget(mTvTarget);
                }else{
                    mMaskGuideView.attachTarget(mTvTarget2);
                    mGestureAnimGuideView.attachTarget(mTvTarget2);
                }
                mIndex++;
            }
        });
    }
}
