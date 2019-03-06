package com.sky.hyh.customviewsamples.span;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hyh on 2019/3/6 22:18
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TypeConfig {
    public static final int UNIT_SP = 0;
    public static final int UNIT_PX = 1;
    @IntDef({UNIT_SP,UNIT_PX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit{};

    public static final int TYPE_CUSTOM_TEXT_SPAN = 0;//自定义文本span -> CustomTextSpan
    public static final int TYPE_ABS_SIZE_SPAN = 1;//固定字体大小span -> AbsoluteSizeSpan
    @IntDef({TYPE_CUSTOM_TEXT_SPAN,TYPE_ABS_SIZE_SPAN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SpanType{};

    public static final int ALIGN_BOTTOM = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_TOP = 2;
    @IntDef({ALIGN_BOTTOM,ALIGN_CENTER,ALIGN_TOP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AlignType{};
}
