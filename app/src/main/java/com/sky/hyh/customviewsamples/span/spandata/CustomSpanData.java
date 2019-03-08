package com.sky.hyh.customviewsamples.span.spandata;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.util.Log;
import com.sky.hyh.customviewsamples.span.CustomTextSpan;
import com.sky.hyh.customviewsamples.span.TypeConfig;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sky.hyh.customviewsamples.span.TypeConfig.ALIGN_BOTTOM;
import static com.sky.hyh.customviewsamples.span.TypeConfig.TYPE_ABS_SIZE_SPAN;
import static com.sky.hyh.customviewsamples.span.TypeConfig.TYPE_CUSTOM_TEXT_SPAN;
import static com.sky.hyh.customviewsamples.span.TypeConfig.UNIT_PX;
import static com.sky.hyh.customviewsamples.span.TypeConfig.UNIT_SP;

/**
 * Created by hyh on 2018/12/18 16:18
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class CustomSpanData extends BaseSpanData {
    @TypeConfig.SpanType
    private int mSpanType;
    private String mOriStr;//完整文本
    private int mStartIndex;
    private int mEndIndex;
    @TypeConfig.Unit
    private int mUnit;
    private float mTextSize;
    private Typeface mTypeface;
    private int mColor;
    private int mLeftMarginDp;
    @TypeConfig.AlignType
    private int mAlign;

    public CustomSpanData(Builder builder) {
        mSpanType = builder.mSpanType;
        mOriStr = builder.mOriStr;
        mStartIndex = builder.mStartIndex;
        mEndIndex = builder.mEndIndex;
        mUnit = builder.mUnit;
        mTextSize = builder.mTextSize;
        mTypeface = builder.mTypeface;
        mColor = builder.mColor;
        mLeftMarginDp = builder.mLeftMarginDp;
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

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(@TypeConfig.Unit int unit,float textSize){
        mUnit = unit;
        mTextSize = textSize;
    }

    @Override
    public CharacterStyle onCreateSpan() {
        switch (mSpanType){
                case TYPE_ABS_SIZE_SPAN:
                    switch (mUnit){
                            case UNIT_PX:
                                return new AbsoluteSizeSpan((int) mTextSize);
                            case UNIT_SP:
                                return new AbsoluteSizeSpan((int) mTextSize,true);
                    }
                case TYPE_CUSTOM_TEXT_SPAN:
                    return new CustomTextSpan(mUnit,mTextSize,
                        mTypeface,
                        mColor,
                        mLeftMarginDp,
                        mAlign);

                default:
                    return new CustomTextSpan(mUnit,mTextSize,
                        mTypeface,
                        mColor,
                        mLeftMarginDp,
                        mAlign);
        }
    }

    @Override
    public String toString() {
        return "CustomSpanData{" +
            "mStartIndex=" + mStartIndex +
            ", mEndIndex=" + mEndIndex +
            ", mUnit=" + mUnit +
            ", mTextSize=" + mTextSize +
            '}';
    }

    public static class Builder{
        @TypeConfig.SpanType
        private int mSpanType;
        private String mOriStr;
        private int mStartIndex;
        private int mEndIndex;
        @TypeConfig.Unit
        private int mUnit;
        private float mTextSize;
        private Typeface mTypeface = Typeface.SANS_SERIF;
        private int mColor = Color.WHITE;
        private int mLeftMarginDp;
        @TypeConfig.AlignType
        private int mAlign = ALIGN_BOTTOM;

        public Builder(String oriStr,int startIndex,int endIndex) {
            mOriStr = oriStr;
            mStartIndex = startIndex;
            mEndIndex = endIndex;
        }

        public Builder(int startIndex,int endIndex) {
            mStartIndex = startIndex;
            mEndIndex = endIndex;
        }

        public Builder setSpanType(@TypeConfig.SpanType int spanType) {
            mSpanType = spanType;
            return this;
        }

        public Builder setTextSize(float textSize) {
            mTextSize = textSize;
            return this;
        }

        public Builder setTextSize(@TypeConfig.Unit int unit,float textSize) {
            mUnit = unit;
            mTextSize = textSize;
            return this;
        }

        /**
         * 设置字体风格，粗体，斜体，下划线等
         * @param style
         * @return
         */
        public Builder setTypefaceStyle(int style) {
            mTypeface = Typeface.create(mTypeface, style);
            return this;
        }

        public Builder setTypeface(Typeface typeface) {
            mTypeface = typeface;
            return this;
        }

        public Builder setColor(String color) {
            mColor = Color.parseColor(color);
            return this;
        }

        public Builder setColor(int color) {
            mColor = color;
            return this;
        }

        public Builder setLeftMargin(int leftMarginDp) {
            mLeftMarginDp = leftMarginDp;
            return this;
        }

        public Builder setAlign(@TypeConfig.AlignType int align) {
            mAlign = align;
            return this;
        }

        public CustomSpanData build(){
            return new CustomSpanData(this);
        }
    }
}
