package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import com.sky.hyh.customviewsamples.span.spandata.CustomTextSpanData;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hyh on 2019/2/27 16:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 不同行字体大小不一样的EditText
 */
public class SpanLineEditText extends AppCompatEditText {
    private List<CustomTextSpanData> mCustomTextSpanDataList;
    private String mLastText = "";

    public SpanLineEditText(Context context) {
        super(context,null);
    }

    public SpanLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mCustomTextSpanDataList = new ArrayList<>();
    }

    @Override
    public boolean onPreDraw() {
        boolean result = super.onPreDraw();
        refresh();
        return result;
    }

    private void refresh(){
        Layout layout = getLayout();
        if(layout != null){
            List<CustomTextSpanData> copySpanData = new ArrayList<>();
            copySpanData.addAll(mCustomTextSpanDataList);
            mCustomTextSpanDataList.clear();

            int lineCount = layout.getLineCount();
            Log.d("hyh", "SpanLineEditText: refresh: lineCount="+lineCount);
            String text = layout.getText().toString();
            int size = copySpanData.size();
            boolean update = false;
            for (int i = 0; i < lineCount; i++) {
                int start = layout.getLineStart(i);
                int end = layout.getLineEnd(i);
                String rowStr = text.substring(start,end);
                Log.d("hyh", "SpanLineEditText: refresh: rowStr="+rowStr);
                if(!update && i < size){
                    CustomTextSpanData customTextSpanData = copySpanData.get(i);
                    String segmentStr = customTextSpanData.getSegmentStr();
                    Log.d("hyh", "SpanLineEditText: refresh: segmentStr="+segmentStr);
                    if(segmentStr==null || !segmentStr.equals(rowStr)){
                        //原本的每行文字跟现在的每行文字不相同，说明排版变了，需要重新更新文本
                        update = true;
                    }
                }
                CustomTextSpanData customTextSpanData = new CustomTextSpanData.Builder(text,start,end)
                    .setTextSize(getRangeRandomInt())
                    .setSegmentStr(rowStr)
                    .build();
                mCustomTextSpanDataList.add(customTextSpanData);
            }
            if(!mLastText.equals(text)||update) {
                updateText(text);
            }
            mLastText = text;
        }
    }

    public static int getRangeRandomInt() {
        int start = SizeUtils.sp2px(10);
        int end = SizeUtils.sp2px(30);
        Random random = new Random();
        int result = random.nextInt(end - start) + start + 1;
        return result;
    }

    private void updateText(String text){
        Log.d("hyh", "SpanLineEditText: updateText: text="+text);
        SpannableString spannableString = new SpannableString(getText().toString());
        for(CustomTextSpanData customTextSpanData: mCustomTextSpanDataList){
            spannableString.setSpan(customTextSpanData.onCreateSpan(),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
        setSelection(text.length());
    }
}
