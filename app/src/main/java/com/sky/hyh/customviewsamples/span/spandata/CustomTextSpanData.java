package com.sky.hyh.customviewsamples.span.spandata;

import android.graphics.Color;
import android.graphics.Typeface;
import com.sky.hyh.customviewsamples.span.CustomTextSpan;

/**
 * Created by hyh on 2018/12/18 16:18
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class CustomTextSpanData extends BaseSpanData {
    private String mOriStr;
    private int mStartIndex;
    private int mEndIndex;
    private int mTextSize;
    private Typeface mTypeface;
    private int mColor;
    private int mLeftMargin;
    @CustomTextSpan.AlignType
    private int mAlign;

    public CustomTextSpanData(Builder builder) {
        mOriStr = builder.mOriStr;
        mStartIndex = builder.mStartIndex;
        mEndIndex = builder.mEndIndex;
        mTextSize = builder.mTextSize;
        mTypeface = builder.mTypeface;
        mColor = builder.mColor;
        mLeftMargin = builder.mLeftMargin;
        mAlign = builder.mAlign;
    }

    public String getOriStr() {
        return mOriStr;
    }

    public int getStartIndex() {
        return mStartIndex;
    }

    public int getEndIndex() {
        return mEndIndex;
    }

    @Override
    public CustomTextSpan onCreateSpan() {
        return new CustomTextSpan(mTextSize,
            mTypeface,
            mColor,
            mLeftMargin,
            mAlign);
    }

    public static class Builder{
        private String mOriStr;
        private int mStartIndex;
        private int mEndIndex;
        private int mTextSize;
        private Typeface mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);;
        private int mColor = Color.WHITE;
        private int mLeftMargin;
        @CustomTextSpan.AlignType
        private int mAlign = CustomTextSpan.ALIGN_BOTTOM;

        public Builder(String oriStr,int startIndex,int endIndex) {
            mOriStr = oriStr;
            mStartIndex = startIndex;
            mEndIndex = endIndex;
        }

        public Builder setTextSize(int textSize) {
            mTextSize = textSize;
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        public Builder setColor(int color) {
            mColor = color;
            return this;
        }

        public Builder setLeftMargin(int leftMargin) {
            mLeftMargin = leftMargin;
            return this;
        }

        public Builder setAlign(@CustomTextSpan.AlignType int align) {
            mAlign = align;
            return this;
        }

        public CustomTextSpanData build(){
            return new CustomTextSpanData(this);
        }
    }
}
