package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hyh on 2018/10/27 17:09
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class GlowTextView extends AppCompatTextView {
    public GlowTextView(Context context) {
        this(context,null);
    }

    public GlowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setShadowLayer(30,5,5,0xffffbe00);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //getPaint().setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));
        //getPaint().setMaskFilter(new BlurMaskFilter(40, BlurMaskFilter.Blur.OUTER));
        super.onDraw(canvas);
    }
}
