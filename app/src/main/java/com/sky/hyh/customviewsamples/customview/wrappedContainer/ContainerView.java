package com.sky.hyh.customviewsamples.customview.wrappedContainer;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.hyh.base_lib.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/3/18 14:30
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class ContainerView extends View {
    private List<CustomDrawable> mDrawableList;

    public ContainerView(Context context) {
        this(context,null);
    }

    public ContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mDrawableList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(CustomDrawable customDrawable: mDrawableList){
            customDrawable.draw(canvas);
        }
    }

    public List<CustomDrawable> getDrawableList() {
        return mDrawableList;
    }

    public void setCurTouchDrawable(CustomDrawable curTouchDrawable) {
        mDrawableList.remove(curTouchDrawable);
        mDrawableList.add(curTouchDrawable);
    }

    public void addDrawable(){
        CustomDrawable customDrawable = new CustomDrawable(getContext());
        customDrawable.setX(SizeUtils.dp2px(200));
        customDrawable.setY(SizeUtils.dp2px(200));
        mDrawableList.add(customDrawable);
    }
}
