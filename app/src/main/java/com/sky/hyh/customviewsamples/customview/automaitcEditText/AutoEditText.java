package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hyh on 2019/2/26 15:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 自动排版输入框,文本每行的字体大小可能不一样
 */
public class AutoEditText extends AppCompatEditText {
    private AutoProcessor mAutoMaticProcessor;

    public AutoEditText(Context context) {
        this(context,null);
    }

    public AutoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(),"HYQiHei-55S.ttf"));
        setTextColor(Color.WHITE);
        mAutoMaticProcessor = new AutoProcessor(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
       mAutoMaticProcessor.handleSizeChanged(w,h,oldw,oldh);
    }
}
