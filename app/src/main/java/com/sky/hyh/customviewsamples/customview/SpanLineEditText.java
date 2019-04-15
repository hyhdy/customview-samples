package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.Log;
import com.sky.hyh.customviewsamples.span.spandata.CustomSpanData;
import com.hyh.base_lib.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sky.hyh.customviewsamples.span.TypeConfig.TYPE_ABS_SIZE_SPAN;

/**
 * Created by hyh on 2019/2/27 16:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 不同行字体大小不一样的EditText
 */
public class SpanLineEditText extends AppCompatEditText {
    private List<CustomSpanData> mCustomTextSpanDataList;
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

        addTextChangedListener(new TextWatcher() {
            /**
             *
             * @param s：改变前的文本
             * @param start
             * @param count：改变的文本长度
             * @param after
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("hyh", "SpanLineEditText: beforeTextChanged");
            }

            /**
             *
             * @param s：改变时的文本
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("hyh", "SpanLineEditText: onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("hyh", "SpanLineEditText: afterTextChanged");
            }
        });
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
            List<CustomSpanData> copySpanData = new ArrayList<>();
            copySpanData.addAll(mCustomTextSpanDataList);
            mCustomTextSpanDataList.clear();

            int lineCount = layout.getLineCount();
            String text = layout.getText().toString();
            int index = text.indexOf("\n");
            Log.d("hyh", "SpanLineEditText: refresh: index="+index);
            Log.d("hyh", "SpanLineEditText: refresh: text="+text+" ,lineCount="+lineCount);
            for (int i = 0; i < lineCount; i++) {
                int start = layout.getLineStart(i);
                int end = layout.getLineEnd(i);
                String rowStr = text.substring(start,end);
                Log.d("hyh", "SpanLineEditText: refresh: rowStr="+rowStr+" ,start="+start+" ,end="+end);
                CustomSpanData customTextSpanData = new CustomSpanData.Builder(text,start,end)
                    .setTextSize(getRangeRandomInt())
                    .setSpanType(TYPE_ABS_SIZE_SPAN)
                    .build();
                mCustomTextSpanDataList.add(customTextSpanData);
            }
            if(!mLastText.equals(text)) {
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
        SpannableString spannableString = new SpannableString(text);
        for(CustomSpanData customTextSpanData: mCustomTextSpanDataList){
            spannableString.setSpan(customTextSpanData.onCreateSpan(),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        //for(CustomSpanData customTextSpanData: mCustomTextSpanDataList){
        //    spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
        //        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //}

        //for(int i=0;i<text.length();i++){
        //    spannableString.setSpan(new ImageSpan(this.getContext(), R.drawable.p2, ImageSpan.ALIGN_BASELINE),i,i+1,
        //        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //}

        //spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),0,text.length(),
        //    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spannableString.setSpan(new URLSpan(text),0,text.length(),
        //    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int start = getSelectionStart();
        int end = getSelectionEnd();
        Log.d("hyh", "SpanLineEditText: updateText: start="+start+" ,end="+end);
        setText(spannableString);
        setSelection(start);
    }
}
