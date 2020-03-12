package com.sky.hyh.customviewsamples.customview.ratinggridbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hyh.base_lib.utils.SizeUtils;
import com.sky.hyh.customviewsamples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by curdyhuang on 2020-03-12
 * 简易的网格星星进度条，没有重写onMeasure方法，使用时宽高要设为固定值。
 */
public class RatingGridView extends View {
    private int mWidth;
    private int mHeight;

    private int mColumnCount = 1;//列数
    private int mLineCount = 1;//行数
    private Drawable mBackDrawable;//进度背景
    private Drawable mFrontDrawable;//进度前景
    private int mStarW;//星星图标宽度
    private int mStarH;//星星图标高度
    /**
     * 记录每个网格的区域
     */
    private List<Rect> mRectList = new ArrayList<>();
    private Rect mProgressRect = new Rect();
    private float mRate;

    public RatingGridView(Context context) {
        this(context,null);
    }

    public RatingGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context,@Nullable AttributeSet attrs){
        if(context == null || attrs == null){
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatingGridView);
        if(ta!=null){
            mLineCount = ta.getInt(R.styleable.RatingGridView_lineCount,1);
            mColumnCount = ta.getInt(R.styleable.RatingGridView_columeCount,1);
            mBackDrawable = context.getResources().getDrawable(ta.getResourceId(R.styleable.RatingGridView_starbg,R.drawable.fx_ic_star_grey));
            mFrontDrawable = context.getResources().getDrawable(ta.getResourceId(R.styleable.RatingGridView_starfront,R.drawable.fx_ic_star_light));
            mStarW = (int) ta.getDimension(R.styleable.RatingGridView_starW,SizeUtils.dp2px(7));
            mStarH = (int) ta.getDimension(R.styleable.RatingGridView_starH,SizeUtils.dp2px(7));

            ta.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        if(w!=oldw||h!=oldh){
            updateRect();
        }
    }

    private void updateRect(){
        if(mWidth > 0 && mHeight > 0) {
            mRectList.clear();
            int perWidth = mWidth / mColumnCount;
            int perHeight = mHeight / mLineCount;
            for (int i = 0; i < mLineCount; i++) {
                for (int j = 0; j < mColumnCount; j++) {
                    Rect rect = new Rect();
                    int l = perWidth * j;
                    int t = perHeight * i;
                    rect.set(l, t, l + perWidth, t + perHeight);
                    mRectList.add(rect);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int index = 0;
        for (Rect rect : mRectList) {
            //1.绘制背景
            if (mBackDrawable != null) {
                int left = (rect.right - rect.left - mStarW) / 2 + rect.left;
                int top = (rect.bottom - rect.top - mStarH) / 2 + rect.top;
                int right = left + mStarW;
                int bottom = top + mStarH;
                mBackDrawable.setBounds(left, top, right, bottom);
                mBackDrawable.draw(canvas);
            }

            float balance = mRate - index;
            if (balance <= 0) {
                continue;
            }

            //2.绘制前景
            if (mFrontDrawable != null) {
                int left = (rect.right - rect.left - mStarW) / 2 + rect.left;
                int top = (rect.bottom - rect.top - mStarH) / 2 + rect.top;
                int right = left + mStarW;
                int bottom = top + mStarH;
                mFrontDrawable.setBounds(left, top, right, bottom);
                int progress = left;
                if (balance < 1) {
                    progress += mStarW * balance;
                    canvas.save();
                    mProgressRect.set(left, top, progress, bottom);
                    //裁剪进度
                    canvas.clipRect(mProgressRect);
                    mFrontDrawable.draw(canvas);
                    canvas.restore();
                } else {
                    mFrontDrawable.draw(canvas);
                }
            }

            index++;
        }
    }

    public void setRate(float rate){
        mRate = rate;
        invalidate();
    }
}
