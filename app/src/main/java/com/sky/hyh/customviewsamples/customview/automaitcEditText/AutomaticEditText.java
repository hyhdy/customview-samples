package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.sky.hyh.customviewsamples.span.spandata.CustomTextSpanData;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyh on 2019/2/26 15:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 自动排版输入框,文本每行的字体大小可能不一样
 */
public class AutomaticEditText extends AppCompatEditText {
    public static final int WIDTH_OFFSET = 10;
    public static final int HEIGHT_OFFSET = 0;

    //默认字体大小
    public static final float DEF_FONT_SIZE_SP = 16;
    /**
     * 控件的初始宽度，随着输入文本的变化，控件的宽度可能会改变
     */
    private int mInitWidgetWidth;
    private boolean mResetWidgetSize;
    /**
     * 最大文本高度
     */
    private int mMaxTextHeight;
    /**
     * 每行可输入文本的最大宽度
     */
    private int mMaxTextWidth;
    private float mDefFontSizePx;
    private List<LineData> mLineDataList;
    private String mLastText = "";
    private TextSizeAdjustHelper mTextSizeAdjustHelper;

    public AutomaticEditText(Context context) {
        this(context,null);
    }

    public AutomaticEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setTextSize(DEF_FONT_SIZE_SP);
        mDefFontSizePx = SizeUtils.sp2px(DEF_FONT_SIZE_SP);
        mLineDataList = new ArrayList<>();
        mTextSizeAdjustHelper = new TextSizeAdjustHelper(this);
        //设为false，且不设置行间距，这样每行高度的累加值才等于文本总高度，即Layout.getHeight == Layout.getLineCount * TextPaint.getFontMetricsInt
        setIncludeFontPadding(false);
        //不设置行间距
        setLineSpacing(0,1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if((w != oldw || h != oldh) && !mResetWidgetSize){
            mResetWidgetSize = false;
            mMaxTextHeight = h - getPaddingTop() - getPaddingBottom() - SizeUtils.dp2px(HEIGHT_OFFSET);
            mInitWidgetWidth = w;
            mMaxTextWidth = w - getPaddingLeft() - getPaddingRight() - SizeUtils.dp2px(WIDTH_OFFSET);
            Log.d("hyh", "AutomaticEditText: onSizeChanged: mMaxTextHeight="+ mMaxTextHeight+ " ,mMaxTextWidth="+mMaxTextWidth);
        }
    }

    @Override
    public boolean onPreDraw() {
        Log.d("hyh", "AutomaticEditText: onPreDraw");
        boolean result = super.onPreDraw();
        refresh();
        return result;
    }

    private void refresh(){
        if(getLayout() != null){
            //这里的Layout不能用EditText的Layout，不然无法正确拿到每行文本，需要构建一个默认Layout，文本的换行都依据该Layout算出
            Layout correctLayout = getCorrectLayout();
            String text = correctLayout.getText().toString();
            Log.d("hyh", "AutomaticEditText: refresh: text="+text);
            boolean update = isUpdateText(correctLayout);
            Log.d("hyh", "AutomaticEditText: refresh: update="+update);
            if(update) {
                spliteLineData(correctLayout);
                matchMaxWidthFontSize();
                matchMaxHeightFontSize();
                updateText(text);
                int maxLineWidth = (int) calculateMaxLineWidth();
                if(maxLineWidth > 0){
                    mResetWidgetSize = true;
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = maxLineWidth + getPaddingLeft() +getPaddingRight() + SizeUtils.dp2px(WIDTH_OFFSET);
                    Log.d("hyh", "AutomaticEditText: refresh: layoutParams.width="+layoutParams.width);
                    setLayoutParams(layoutParams);
                }
            }
            mLastText = text;
        }
    }

    /**
     * 判断是否需要更新文本
     * @return
     */
    private boolean isUpdateText(Layout layout){
        boolean update = false;
        String text = layout.getText().toString();
        if(!mLastText.equals(text)) {
            Log.d("hyh", "AutomaticEditText: isUpdateText: 1");
            update = true;
        }else{
            int lineCount = layout.getLineCount();
            int size = mLineDataList.size();
            if(lineCount != size){
                Log.d("hyh", "AutomaticEditText: isUpdateText: 2");
                update = true;
            }else{
                for (int i = 0; i < lineCount; i++) {
                    int start = layout.getLineStart(i);
                    int end =layout.getLineEnd(i);
                    String rowStr = text.substring(start, end);
                    LineData lineData = mLineDataList.get(i);
                    String lineText = lineData.getLineText();
                    if (!rowStr.equals(lineText)) {
                        Log.d("hyh", "AutomaticEditText: isUpdateText: 3");
                        //原本的每行文字跟现在的每行文字不相同，说明排版变了，需要重新更新文本
                        update = true;
                        break;
                    }
                }
            }
        }
        return  update;
    }

    /**
     * 按行分割文本
     */
    private void spliteLineData(Layout layout){
        mLineDataList.clear();
        String text = layout.getText().toString();
        int lineCount = layout.getLineCount();
        Log.d("hyh", "AutomaticEditText: spliteLineData: text="+text+" ,lineCount="+lineCount);
        for (int i = 0; i < lineCount; i++) {
            int start = layout.getLineStart(i);
            int end = layout.getLineEnd(i);
            String rowStr = text.substring(start,end);
            CustomTextSpanData customTextSpanData = new CustomTextSpanData.Builder(start,end)
                .setTextSizePx(mDefFontSizePx)
                .build();
            LineData lineData = new LineData(rowStr,customTextSpanData);
            Log.d("hyh", "AutomaticEditText: spliteLineData: lineData="+lineData.toString());
            mLineDataList.add(lineData);
        }
    }

    /**
     * 计算匹配最大文本宽度的字体大小
     */
    private void matchMaxWidthFontSize(){
        for(LineData lineData: mLineDataList){
            String lineText = lineData.getLineText();
            if(!TextUtils.isEmpty(lineText)){
                Paint paint = copyPaint();
                float textWidth = paint.measureText(lineText);
                float lineMaxWidth = getLineMaxWidth();
                Log.d("hyh", "AutomaticEditText: matchMaxWidthFontSize: textWidth="+textWidth+" ,lineMaxWidth="+lineMaxWidth);
                //按照宽比缩放字体
                float textSize = lineMaxWidth / textWidth * mDefFontSizePx;
                Log.d("hyh", "AutomaticEditText: matchMaxWidthFontSize: textSize="+textSize);
                paint.setTextSize(textSize);
                //缩放字体后还得检查行宽度是否大于最大文本宽度，如果大于则还需要调小字体
                textSize = mTextSizeAdjustHelper.calculateMatchWidthSize(paint,lineText,mMaxTextWidth);
                lineData.setFontSizePx(textSize);
            }else{
                lineData.setFontSizePx(mDefFontSizePx);
            }
        }
    }

    /**
     * 计算匹配最大文本高度的字体大小
     */
    private void matchMaxHeightFontSize(){
        List<CustomTextSpanData> customTextSpanDataList = new ArrayList<>();
        for(LineData lineData: mLineDataList){
            CustomTextSpanData customTextSpanData = lineData.getCustomTextSpanData();
            customTextSpanDataList.add(customTextSpanData);
        }
        mTextSizeAdjustHelper.calculateMatchHeightSize(customTextSpanDataList,mMaxTextHeight);
    }

    private void updateText(String text){
        SpannableString spannableString = new SpannableString(text);
        for(LineData lineData: mLineDataList){
            Log.d("hyh", "AutomaticEditText: updateText: lineText="+lineData.getLineText()+" ,lineFontSize="+lineData.getFontSizePx());
            CustomTextSpanData customTextSpanData = lineData.getCustomTextSpanData();
            spannableString.setSpan(customTextSpanData.onCreateSpan(),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
        setSelection(text.length());
    }

    private float calculateMaxLineWidth(){
        String maxLengthText = "";
        LineData maxLengthLineData = null;
        Paint paint = copyPaint();
        float maxLineWidth = 0;
        for(LineData lineData: mLineDataList){
            String lineText = lineData.getLineText();
            Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: lineText="+lineText);
            if(lineText.length() > maxLengthText.length()){
                maxLengthText = lineText;
                maxLengthLineData = lineData;
            }

            paint.setTextSize(lineData.getFontSizePx());
            float width = paint.measureText(lineData.getLineText());
            if(width > maxLineWidth){
                maxLineWidth = width;
            }
        }

        if(maxLengthLineData == null){
            return maxLineWidth;
        }

        float maxLengthLineWidth = calculateMaxLengthLineWidth(maxLengthLineData);
        Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: maxLineWidth="+maxLineWidth+" ,maxLengthLineWidth="+maxLengthLineWidth);
        float maxWidth = maxLineWidth > maxLengthLineWidth? maxLineWidth:maxLengthLineWidth;
        Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: maxWidth="+maxWidth+" ,mMaxTextWidth="+mMaxTextWidth);
        if(maxWidth > mMaxTextWidth){
            maxWidth = mMaxTextWidth;
        }

        return maxWidth;
    }

    private float calculateMaxLengthLineWidth(LineData lineData){
        Paint paint = copyPaint();
        float width = paint.measureText(lineData.getLineText());
        float rate = getLineMaxWidth() / width;
        paint.setTextSize(lineData.getFontSizePx());
        float textWidth = paint.measureText(lineData.getLineText());
        float maxWidth = textWidth * rate;
        return maxWidth;
    }

    private Layout getCorrectLayout(){
        TextPaint paint = copyPaint();
        int width = getLineMaxWidth();
        //注意这里的text是String不是Spannable
        String textString = getText().toString();
        DynamicLayout dynamicLayout = new DynamicLayout(textString,paint,width,getLayout().getAlignment(),getLayout().getSpacingMultiplier(),getLayout().getSpacingAdd(),getIncludeFontPadding());
        return dynamicLayout;
    }

    private int getLineMaxWidth(){
        return mInitWidgetWidth - getPaddingLeft() - getPaddingRight();
    }

    private TextPaint copyPaint(){
        TextPaint paint = new TextPaint(getPaint());
        paint.setTextSize(mDefFontSizePx);
        return paint;
    }

    public static class LineData{
        //行文本
        private String mLineText;
        private CustomTextSpanData mCustomTextSpanData;

        public LineData(String lineStr, CustomTextSpanData customTextSpanData) {
            mLineText = lineStr;
            mCustomTextSpanData = customTextSpanData;
        }

        public String getLineText() {
            return mLineText;
        }

        public int getStartIndex() {
            return mCustomTextSpanData.getStartIndex();
        }

        public int getEndIndex() {
            return mCustomTextSpanData.getEndIndex();
        }

        public float getFontSizePx(){
            return mCustomTextSpanData.getTextSizePx();
        }

        public void setFontSizePx(float textSizePx){
            mCustomTextSpanData.setTextSizePx(textSizePx);
        }

        public CustomTextSpanData getCustomTextSpanData() {
            return mCustomTextSpanData;
        }

        @Override
        public String toString() {
            return "LineData{" +
                "mLineText='" + mLineText + '\'' +
                ", mStartIndex=" + mCustomTextSpanData.getStartIndex() +
                ", mEndIndex=" + mCustomTextSpanData.getEndIndex() +
                '}';
        }
    }
}
