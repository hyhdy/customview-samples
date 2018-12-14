package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.utils.DensityUtil;
import com.sky.hyh.customviewsamples.utils.RectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义表情面板
 */
public class CustomEmojiPanel extends BaseSplitGridView {
    private static final int TYPE_EMOJI = 0;
    private static final int TYPE_MORE = 1;
    /**
     * 默认值常量
     */
    private static final float DEFAULT_SIZE_ROUND_RADIUS_DP = 12;//圆角半径dp
    private static final float DEFAULLT_SIZE_BUTTON_MORE_DP = 42;//按钮大小dp
    private static final float DEFAULT_SIZE_EMOJI_TEXT_SP = 36;//emoji大小Sp

    public static final String[] sSeizeEmojiList = new String[]{
            "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊", "😋", "😎"
    };

    private float mRadius;
    private int mButtonMoreSize;
    private float mEmojiSize;

    private TextPaint mTextPaint;
    private Drawable mDrawable;
    private Map<Rect,FakeView<String>> mFakeViewMap;

    public CustomEmojiPanel(Context context) {
        this(context,null);
    }

    public CustomEmojiPanel(Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        mFakeViewMap = new HashMap<>();

        mRadius = DensityUtil.dip2px(DEFAULT_SIZE_ROUND_RADIUS_DP);
        mButtonMoreSize = DensityUtil.dip2px(DEFAULLT_SIZE_BUTTON_MORE_DP);
        mEmojiSize = DensityUtil.sp2px(DEFAULT_SIZE_EMOJI_TEXT_SP);

        mDrawable = getContext().getResources().getDrawable(R.drawable.btn_more);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw||h!=oldh){
            loadData();
        }
    }

    private void loadData(){
        int rectSize = mRectList.size();
        int size = Math.min(rectSize,sSeizeEmojiList.length+1);
        mFakeViewMap.clear();
        for(int i=0;i<size;i++){
            FakeView<String> fakeView = new FakeView<>();
            fakeView.rect = mRectList.get(i);
            if(i == size-1){
                fakeView.type = TYPE_MORE;
            }else{
                fakeView.type = TYPE_EMOJI;
                fakeView.data = sSeizeEmojiList[i];
            }
            mFakeViewMap.put(fakeView.rect,fakeView);
        }
    }

    @Override
    protected int getLineCount() {
        return 3;
    }

    @Override
    protected int getPerLineCount() {
        return 5;
    }

    @Override
    protected void onClickView(Rect rect) {
        FakeView<String> fakeView = mFakeViewMap.get(rect);
        if(fakeView!=null){
            int type = fakeView.type;
            if(type == TYPE_MORE){
                Toast.makeText(getContext(),"点击了更多按钮",Toast.LENGTH_SHORT).show();
            }else if(type == TYPE_EMOJI){
                Toast.makeText(getContext(),"点击了"+fakeView.data,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制圆角矩形背景
        mTextPaint.setColor(Color.parseColor("#b3000000"));
        RectF rectf = new RectF(0,0,mWidth,mHeight);
        canvas.drawRoundRect(rectf,mRadius,mRadius,mTextPaint);

        //2.绘制表情
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(mEmojiSize);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        for(FakeView<String> fakeView: mFakeViewMap.values()){
            int type = fakeView.type;
            if(type == TYPE_MORE){
                //3.绘制图片按钮
                int left = (fakeView.rect.right - fakeView.rect.left - mButtonMoreSize)/2 + fakeView.rect.left;
                int top = (fakeView.rect.bottom - fakeView.rect.top - mButtonMoreSize)/2 + fakeView.rect.top;
                int right = left + mButtonMoreSize;
                int bottom = top + mButtonMoreSize;
                mDrawable.setBounds(left,top,right,bottom);
                mDrawable.draw(canvas);
            }else if(type == TYPE_EMOJI){
                String emoji = fakeView.data;
                float emojiWidth = mTextPaint.measureText(emoji);
                Rect rect = fakeView.rect;

                //每个表情显示区域rect的y轴中心点
                int centerY = (rect.top + rect.bottom) / 2;
                //计算绘制文本的基线
                float baseLineX = (rect.right - rect.left - emojiWidth) / 2 + rect.left;
                float baseLineY = centerY - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
                canvas.drawText(emoji, baseLineX, baseLineY, mTextPaint);
            }
        }
    }
}
