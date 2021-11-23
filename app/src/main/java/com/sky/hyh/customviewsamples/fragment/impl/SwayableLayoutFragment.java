package com.sky.hyh.customviewsamples.fragment.impl;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.annotation.InjectFragment;
import com.hyh.base_lib.BaseFragment;
import com.hyh.base_lib.annotation.FindViewByIdAno;
import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.utils.SwayTwoLayoutAnimHolder;

/**
 * Created by hyh on 2019/2/26 15:49
 * E-Mail Address：fjnuhyh122@gmail.com
 */
@InjectFragment()
public class SwayableLayoutFragment extends BaseFragment {
    @FindViewByIdAno(R.id.tv_show)
    private TextView mTvShow;
    @FindViewByIdAno(R.id.tv_left)
    private TextView mTvLeft;
    @FindViewByIdAno(R.id.tv_mid)
    private TextView mTvMid;
    @FindViewByIdAno(R.id.tv_right)
    private TextView mTvRight;

    @FindViewByIdAno(R.id.del_container)
    private View mDelContainer;
    @FindViewByIdAno(R.id.fl_left_layout)
    private View mFlLeft;
    @FindViewByIdAno(R.id.fl_right_layout)
    private View mFlRight;
    @FindViewByIdAno(R.id.iv_lighting)
    private ImageView mIvLighting;

    private SwayTwoLayoutAnimHolder mSwayTwoLayoutAnimHolder;
    private AnimationDrawable mAnimationDrawable;

    @Override
    protected int getResId() {
        return R.layout.fragment_swayable_layout;
    }

    @Override
    protected void initViews(View rootView) {

        mSwayTwoLayoutAnimHolder = new SwayTwoLayoutAnimHolder(new SwayTwoLayoutAnimHolder.AnimCallBack() {
            @Override
            public void onUpdate(int leftW, int leftH, int rightW, int rightH, float leftTransXOffset, float rightTransXOffset, float midTransOffset) {
                ViewGroup.LayoutParams leftLayoutParams = mFlLeft.getLayoutParams();
                if (leftLayoutParams!=null){
                    leftLayoutParams.width = leftW;
                    leftLayoutParams.height = leftH;
                    mFlLeft.setLayoutParams(leftLayoutParams);
                    Log.d("hyh","SwayableLayoutFragment: onUpdate: lCurTransX="+mFlLeft.getTranslationX());
                    mFlLeft.setTranslationX(mFlLeft.getTranslationX()+leftTransXOffset);
                }

                ViewGroup.LayoutParams rightLayoutParams = mFlRight.getLayoutParams();
                if (rightLayoutParams!=null){
                    rightLayoutParams.width = rightW;
                    rightLayoutParams.height = rightH;
                    mFlRight.setLayoutParams(rightLayoutParams);
                    Log.d("hyh","SwayableLayoutFragment: onUpdate: rCurTransX="+mFlRight.getTranslationX());
                    mFlRight.setTranslationX(mFlRight.getTranslationX()+rightTransXOffset);
                }

                Log.d("hyh","SwayableLayoutFragment: onUpdate: lightingCurTransX="+mIvLighting.getTranslationX());
                mIvLighting.setTranslationX(mIvLighting.getTranslationX()+midTransOffset);
            }
        });

        mTvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelContainer.setVisibility(View.VISIBLE);
                if(mAnimationDrawable== null){
                    mAnimationDrawable = generateAnimation();
                }
                mIvLighting.setImageDrawable(mAnimationDrawable);
            }
        });

        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwayTwoLayoutAnimHolder.startAnim(SwayTwoLayoutAnimHolder.Direction.left, mDelContainer.getMeasuredWidth(), mDelContainer.getMeasuredHeight());
                if(mAnimationDrawable!=null) {
                    mAnimationDrawable.start();
                }
            }
        });

        mTvMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwayTwoLayoutAnimHolder.startAnim(SwayTwoLayoutAnimHolder.Direction.mid, mDelContainer.getMeasuredWidth(), mDelContainer.getMeasuredHeight());
                if(mAnimationDrawable!=null) {
                    mAnimationDrawable.start();
                }
            }
        });

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwayTwoLayoutAnimHolder.startAnim(SwayTwoLayoutAnimHolder.Direction.right, mDelContainer.getMeasuredWidth(), mDelContainer.getMeasuredHeight());
                if(mAnimationDrawable!=null) {
                    mAnimationDrawable.start();
                }
            }
        });
    }


    private AnimationDrawable generateAnimation(){
        String pre = "lighting_";
        int count = 77;
        AnimationDrawable animationDrawable = new AnimationDrawable();
        int duration = 3000/count;
        for (int i=1;i<=77;i++){
            int resId = getResources().getIdentifier(pre+i, "drawable", getContext().getPackageName());
            Drawable drawable = getResources().getDrawable(resId);
            animationDrawable.addFrame(drawable,duration);
        }
        return animationDrawable;
    }
}
