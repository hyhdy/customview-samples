package com.sky.hyh.customviewsamples.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.text.style.ReplacementSpan;
import android.util.Log;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CustomTextSpan extends ReplacementSpan {
    public static String TAG = "CustomTextSpan";
    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_TOP = 2;

    @IntDef({ALIGN_BOTTOM,ALIGN_CENTER,ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignType{};

    /**
     * 绘制每一分段文本的画笔
     */
    private Paint mPaint;
    /**
     * 分段文本宽度
     */
    private int mWidth;
    /**
     * 分段文本左边距
     */
    private int mLeftMargin;
    /**
     * 分段文本对齐方式，默认底部对齐
     */
    @AlignType
    private int mAlign;
    public static final int SPACING = SizeUtils.dp2px(2);

    public CustomTextSpan(float textSizeSp, Typeface typeFace, int color,int leftMarginDp,int align) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(SizeUtils.sp2px(textSizeSp));
        mPaint.setTypeface(typeFace);
        mPaint.setColor(color);
        mLeftMargin = SizeUtils.dp2px(leftMarginDp);
        mAlign = align;
    }

    /**
     *
     * @param paint: textView的画笔
     * @param text
     * @param start
     * @param end
     * @param fm
     * @return
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mWidth = (int) mPaint.measureText(text, start, end)+mLeftMargin;

        if(fm!=null){
            Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();

            int top = customTextPaintFm.top-SPACING;
            int ascent = customTextPaintFm.ascent-SPACING;
            int bottom = customTextPaintFm.bottom+SPACING;
            int descent = customTextPaintFm.descent+SPACING;

            //每一分段文本的画笔不一样，字体大小，样式等不同导致不同分段文本高度不一致，
            //这里把每一分段的fm值赋为自定义画笔的fm值，这样分段所在行行文本的高度就等于该行所有分段高度最高的字体高度
            fm.top = top;
            fm.ascent = ascent;
            fm.bottom = bottom;
            fm.descent = descent;
        }
        return mWidth;
    }

    /**
     *
     * @param canvas
     * @param text
     * @param start
     * @param end
     * @param x
     * @param top：所在行的top
     * @param y
     * @param bottom：所在行的bottom
     * @param paint
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        switch (mAlign){
            case ALIGN_BOTTOM: {
                canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
            }
            break;
            case ALIGN_CENTER: {
                Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
                Log.d(TAG, "draw: customTextPaintFm=" + customTextPaintFm.toString());
                //调整分段文本居中显示，这里的textHeight等于fm.descent-fm.ascent
                int textHeight = bottom - top;
                int segmentTextHeight = customTextPaintFm.bottom - customTextPaintFm.top;
                y = (textHeight - segmentTextHeight) / 2 - customTextPaintFm.top;
                canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
            }
            break;
            case ALIGN_TOP:{
                Paint.FontMetricsInt customTextPaintFm = mPaint.getFontMetricsInt();
                Log.d(TAG, "draw: customTextPaintFm=" + customTextPaintFm.toString());
                //调整分段文本居中显示
                int textHeight = bottom - top;
                int segmentTextHeight = customTextPaintFm.bottom - customTextPaintFm.top;
                y -= (textHeight - segmentTextHeight) / 2;
                canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
            }
            break;
            default: {
                canvas.drawText(text, start, end, x + mLeftMargin, y, mPaint);
            }
            break;
        }
    }
}
