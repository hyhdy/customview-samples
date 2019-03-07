package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.sky.hyh.customviewsamples.span.spandata.CustomSpanData;
import com.sky.hyh.customviewsamples.utils.SizeUtils;
import java.util.ArrayList;
import java.util.List;

import static com.sky.hyh.customviewsamples.span.TypeConfig.TYPE_ABS_SIZE_SPAN;
import static com.sky.hyh.customviewsamples.span.TypeConfig.UNIT_PX;

/**
 * Created by hyh on 2019/2/26 15:47
 * E-Mail Address：fjnuhyh122@gmail.com
 * 自动排版输入框,文本每行的字体大小可能不一样
 */
public class AutomaticEditText extends AppCompatEditText {
    //默认字体大小
    public static final float DEF_FONT_SIZE_SP = 16;
    private boolean mResetWidgetSize;
    /**
     * 文本有效的显示高度
     */
    private int mValidShowHeight;
    private List<LineData> mLineDataList;
    private String mLastText = "";
    private LayoutHelper mLayoutHelper;

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
        mLayoutHelper = new LayoutHelper(this,SizeUtils.sp2px(DEF_FONT_SIZE_SP));
        mLineDataList = new ArrayList<>();
        //设为false，且不设置行间距，这样每行高度的累加值才等于文本总高度，即Layout.getHeight == Layout.getLineCount * TextPaint.getFontMetricsInt
        setIncludeFontPadding(false);
        //不设置行间距
        setLineSpacing(0,1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if((w != oldw || h != oldh) && !mResetWidgetSize){
            mResetWidgetSize = false;
            mValidShowHeight = h - getPaddingTop() - getPaddingBottom();
            int maxTextWidth = w - getPaddingLeft() - getPaddingRight();
            mLayoutHelper.setLayoutWidth(maxTextWidth);
            Log.d("hyh", "AutomaticEditText: onSizeChanged: mValidShowHeight="+ mValidShowHeight
                + " ,maxTextWidth="+maxTextWidth);
        }
    }

    @Override
    public boolean onPreDraw() {
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
                matchMaxHeightFontSize(text);
                updateText(text);
                int maxLineWidth = (int) calculateMaxLineWidth();
                if(maxLineWidth > 0){
                    mResetWidgetSize = true;
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = maxLineWidth + getPaddingLeft() +getPaddingRight();
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
            CustomSpanData customTextSpanData = new CustomSpanData.Builder(start,end)
                .setSpanType(TYPE_ABS_SIZE_SPAN)
                .setTextSize(UNIT_PX,mLayoutHelper.getFontSize())
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
                lineData.setFontSizePx(mLayoutHelper.getMatchWidthFontSize(lineText));
            }else{
                lineData.setFontSizePx(mLayoutHelper.getFontSize());
            }
        }
    }

    /**
     * 计算匹配最大文本高度的字体大小
     */
    private void matchMaxHeightFontSize(String text){
        List<CustomSpanData> customTextSpanDataList = new ArrayList<>();
        for(LineData lineData: mLineDataList){
            CustomSpanData customTextSpanData = lineData.getCustomTextSpanData();
            customTextSpanDataList.add(customTextSpanData);
        }
        //maxTextHeight不能直接等于mValidShowHeight，需要预留一行的高度，这样在手动换行时就不会出现抖动的问题
        int maxTextHeight = mValidShowHeight - getPaint().getFontMetricsInt(null);
        mLayoutHelper.claculateMatchHeightFontSize(customTextSpanDataList,maxTextHeight);
    }

    private void updateText(String text){
        SpannableString spannableString = new SpannableString(text);
        for(LineData lineData: mLineDataList){
            Log.d("hyh", "AutomaticEditText: updateText: lineText="+lineData.getLineText()+" ,lineFontSize="+lineData.getFontSizePx());
            CustomSpanData customTextSpanData = lineData.getCustomTextSpanData();
            spannableString.setSpan(customTextSpanData.onCreateSpan(),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        int start = getSelectionStart();
        setText(spannableString);
        setSelection(start);
    }

    private float calculateMaxLineWidth(){
        String maxLengthText = "";
        LineData maxLengthLineData = null;
        Paint paint = mLayoutHelper.copyPaint();
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

        float maxLengthLineWidth = mLayoutHelper.calculateMaxLengthLineWidth(maxLengthLineData.getLineText(),maxLengthLineData.getFontSizePx());
        Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: maxLineWidth="+maxLineWidth+" ,maxLengthLineWidth="+maxLengthLineWidth);
        float maxWidth = maxLineWidth > maxLengthLineWidth? maxLineWidth:maxLengthLineWidth;
        if(maxWidth > mLayoutHelper.getLayoutWidth()){
            maxWidth = mLayoutHelper.getLayoutWidth();
        }
        Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: maxWidth="+maxWidth);
        return maxWidth;
    }

    private Layout getCorrectLayout(){
        //注意这里的text是String不是Spannable
        String textString = getText().toString();
        return mLayoutHelper.buildFakeLayout(textString);
    }

    public static class LineData{
        //行文本
        private String mLineText;
        private CustomSpanData mCustomTextSpanData;

        public LineData(String lineStr, CustomSpanData customTextSpanData) {
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
            return mCustomTextSpanData.getTextSize();
        }

        public void setFontSizePx(float textSizePx){
            mCustomTextSpanData.setTextSize(UNIT_PX,textSizePx);
        }

        public CustomSpanData getCustomTextSpanData() {
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
