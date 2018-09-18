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
import java.util.List;

/**
 * Created by hyh on 2018/9/14 14:57
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class CustomEmojiPanel extends View {
    /**
     * 默认值常量
     */
    private static final float DEFAULT_SIZE_ROUND_RADIUS_DP = 12;//圆角半径dp
    private static final float DEFAULLT_SIZE_BUTTON_MORE_DP = 42;//按钮大小dp
    private static final float DEFAULT_SIZE_EMOJI_TEXT_SP = 36;//emoji大小Sp
    private static final int DEFAULT_NUM_VERTICAL = 6;//列数
    public static final String[] sSeizeEmojiList = new String[]{
            "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊", "😋", "😎"
    };

    private float mRadius;
    private int mButtonMoreSize;
    private float mEmojiSize;
    private int mItemCountPerLine = DEFAULT_NUM_VERTICAL;//列数
    private String[] mEmojiList = sSeizeEmojiList;
    private int mWidth;//表情面板宽度
    private int mHeight;//表情面板高度

    private TextPaint mTextPaint;
    private GestureDetector mGestureDetector;
    private Drawable mDrawable;
    /**
     * 存放每个表情的显示区域
     */
    private List<Rect> mEmojiRectList;
    /**
     * 存放更多按钮的显示区域
     */
    private Rect mButtonMoreRect;

    public CustomEmojiPanel(Context context) {
        this(context,null);
    }

    public CustomEmojiPanel(Context context,
                            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        mEmojiRectList = new ArrayList<>();

        mRadius = DensityUtil.dip2px(context, DEFAULT_SIZE_ROUND_RADIUS_DP);
        mButtonMoreSize = DensityUtil.dip2px(context, DEFAULLT_SIZE_BUTTON_MORE_DP);
        mEmojiSize = DensityUtil.sp2px(context, DEFAULT_SIZE_EMOJI_TEXT_SP);

        mDrawable = getContext().getResources().getDrawable(R.drawable.btn_more);
        //int drawableWidth = mDrawable.getIntrinsicWidth();
        //int drawableHeight = mDrawable.getIntrinsicHeight();
        //Log.d("hyh", "PraiseEmojiPanelSmall: onDraw: drawableWidth="+drawableWidth+" ,drawableHeight="+drawableHeight+" ,mBtnMoreSize="+DensityUtil.dip2px(getContext(),mBtnMoreSize));
        //
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.btn_more,options);
        //Log.d("hyh", "PraiseEmojiPanelSmall: onDraw: bitmapWidth="+options.outWidth+" ,bitmapHeight="+options.outHeight);

        mGestureDetector = new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //点击事件
                int x = (int) e.getX();
                int y = (int) e.getY();

                for(int i=0;i<mEmojiRectList.size();i++){
                    if(RectUtil.isOverLay(mEmojiRectList.get(i),x,y)){
                        Toast.makeText(getContext(),"点击了第"+i+"个emoji",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if(RectUtil.isOverLay(mButtonMoreRect,x,y)){
                    Toast.makeText(getContext(),"点击了更多按钮",Toast.LENGTH_SHORT).show();
                }

                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                //这里需要返回true，不然不会调用到其他方法
                return true;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("hyh", "CustomEmojiPanel: onSizeChanged: w="+w+" ,h="+h+" ,oldw="+oldw+" ,oldh="+oldh);
        mWidth = w;
        mHeight = h;

        int lineCount = mEmojiList.length/mItemCountPerLine + 1;

        int perWidth = mWidth/ mItemCountPerLine;
        int perHeight = mHeight/ lineCount;
        Log.d("hyh", "CustomEmojiPanel: onSizeChanged: perWidth="+perWidth+" ,perHeight="+perHeight);

        int index = 0;
        //计算每个表情的显示区域
        for(int i = 0; i< lineCount; i++){
            for(int j = 0; j< mItemCountPerLine; j++){
                index++;
                if(index>mEmojiList.length+1){
                    break;
                }
                Rect rect = new Rect();
                int l = perWidth * j;
                int t = perHeight * i;
                rect.set(l,t,l+perWidth,t+perHeight);

                if(index == mEmojiList.length+1){
                    //最后一个是按钮
                    mButtonMoreRect = rect;
                }else{
                    mEmojiRectList.add(rect);
                }
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
        //Log.d("hyh", "PraiseEmojiPanelSmall: PraiseEmojiPanelSmall: top="+fontMetrics.top+" ,ascent="+fontMetrics.ascent+" ,descent="+fontMetrics.descent+" ,bottom="+fontMetrics.bottom+" ,leading="+fontMetrics.leading);
        for(int i=0;i<mEmojiRectList.size();i++){
            if(i<mEmojiList.length) {
                String emoji = mEmojiList[i];
                float emojiWidth = mTextPaint.measureText(emoji);
                Log.d("hyh", "CustomEmojiPanel: onDraw: emojiWidth=" + emojiWidth);

                Rect rect = mEmojiRectList.get(i);
                Log.d("hyh", "CustomEmojiPanel: onDraw: rect=" + rect.toString());

                //每个表情显示区域rect的y轴中心点
                int centerY = (rect.top + rect.bottom) / 2;
                //计算绘制文本的基线
                float baseLineX = (rect.right - rect.left - emojiWidth) / 2 + rect.left;
                float baseLineY = centerY - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
                Log.d("hyh", "CustomEmojiPanel: onDraw: baseLineX=" + baseLineX + " ,baseLineY=" + baseLineY);
                canvas.drawText(emoji, baseLineX, baseLineY, mTextPaint);
            }
        }

        //3.绘制图片按钮
        int left = (mButtonMoreRect.right - mButtonMoreRect.left - mButtonMoreSize)/2 + mButtonMoreRect.left;
        int top = (mButtonMoreRect.bottom - mButtonMoreRect.top - mButtonMoreSize)/2 + mButtonMoreRect.top;
        int right = left + mButtonMoreSize;
        int bottom = top + mButtonMoreSize;
        mDrawable.setBounds(left,top,right,bottom);
        mDrawable.draw(canvas);

    }

    public void setRadius(float radius) {
        mRadius = DensityUtil.dip2px(getContext(),radius);
    }

    public void setButtonMoreSize(int buttonMoreSize) {
        mButtonMoreSize = DensityUtil.dip2px(getContext(),buttonMoreSize);
    }

    public void setEmojiSize(float emojiSize) {
        mEmojiSize = DensityUtil.sp2px(getContext(),emojiSize);
    }

    public void setItemCountPerLine(int itemCountPerLine) {
        mItemCountPerLine = itemCountPerLine;
    }

    public void setEmojiList(String[] emojiList) {
        mEmojiList = emojiList;
    }
}
