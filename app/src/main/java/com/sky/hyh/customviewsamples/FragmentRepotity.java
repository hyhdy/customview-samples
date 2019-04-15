package com.sky.hyh.customviewsamples;

import com.sky.hyh.customviewsamples.factory.impl.AutomaticEditTextFactory;
import com.sky.hyh.customviewsamples.factory.impl.ClipChildrenFactory;
import com.sky.hyh.customviewsamples.factory.impl.ColorMatrixFactory;
import com.sky.hyh.customviewsamples.factory.impl.ConLimitedEditTextFactory;
import com.sky.hyh.customviewsamples.factory.impl.ContactFactory;
import com.sky.hyh.customviewsamples.factory.impl.CoordinatorLayoutFactory;
import com.sky.hyh.customviewsamples.factory.impl.CustomEmojiPanelFactory;
import com.sky.hyh.customviewsamples.factory.impl.DyLimitedEditTextFactory;
import com.sky.hyh.customviewsamples.factory.impl.DynamicProxyFactory;
import com.sky.hyh.customviewsamples.factory.impl.DynamicRoundTextViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.FlexBoxLayoutManagerFactory;
import com.sky.hyh.customviewsamples.factory.impl.GraduleTitleViewPagerFactory;
import com.sky.hyh.customviewsamples.factory.impl.HappyNewYear2019Factory;
import com.sky.hyh.customviewsamples.factory.impl.HollowTextViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.RoundMaskViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.SpanFactory;
import com.sky.hyh.customviewsamples.factory.impl.SpanLineEditTextFactory;
import com.sky.hyh.customviewsamples.factory.impl.StrokeTextViewFactory;
import com.sky.hyh.customviewsamples.factory.impl.WrappedContainerFactory;

import java.util.ArrayList;
import java.util.List;

public class FragmentRepotity {
    public static List<Class> sDataList = new ArrayList<>();
    static {
        sDataList.add(HappyNewYear2019Factory.class);
        sDataList.add(CustomEmojiPanelFactory.class);
        sDataList.add(DynamicRoundTextViewFactory.class);
        sDataList.add(ClipChildrenFactory.class);
        sDataList.add(RoundMaskViewFactory.class);
        sDataList.add(ConLimitedEditTextFactory.class);
        sDataList.add(DyLimitedEditTextFactory.class);
        sDataList.add(ColorMatrixFactory.class);
        sDataList.add(HollowTextViewFactory.class);
        sDataList.add(SpanFactory.class);
        sDataList.add(GraduleTitleViewPagerFactory.class);
        sDataList.add(StrokeTextViewFactory.class);
        sDataList.add(ContactFactory.class);
        sDataList.add(WrappedContainerFactory.class);
        sDataList.add(AutomaticEditTextFactory.class);
        sDataList.add(SpanLineEditTextFactory.class);
        sDataList.add(FlexBoxLayoutManagerFactory.class);
        sDataList.add(CoordinatorLayoutFactory.class);
        sDataList.add(DynamicProxyFactory.class);
    }
}
