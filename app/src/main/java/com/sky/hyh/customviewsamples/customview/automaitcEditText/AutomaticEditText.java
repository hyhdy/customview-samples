package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
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
    private static final String SYM_CHANGE_LINE = "\n";
    //默认字体大小
    private static final float DEF_FONT_SIZE_SP = 20;
    private boolean mResetWidgetSize;
    /**
     * 最大文本高度
     */
    private int mMaxTextHeight;
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

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refresh();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if((w != oldw || h != oldh) && !mResetWidgetSize){
            mMaxTextHeight = h - getPaddingTop() - getPaddingBottom();
            int maxTextWidth = w - getPaddingLeft() - getPaddingRight();
            mLayoutHelper.setLayoutWidth(maxTextWidth);
            Log.d("hyh", "AutomaticEditText: onSizeChanged: mMaxTextHeight="+ mMaxTextHeight
                + " ,maxTextWidth="+maxTextWidth);
        }
        if(mResetWidgetSize){
            mResetWidgetSize = false;
        }
    }

    private void refresh(){
        if(getLayout() != null){
            //这里的Layout不能用EditText的Layout，不然无法正确拿到每行文本，需要构建一个辅助Layout，文本的换行都依据该Layout算出
            Layout calculateLayout = getCalculateLayout();
            String text = calculateLayout.getText().toString();
            boolean update = isUpdateText(calculateLayout,text);
            if(update) {
                mLastText = text;
                spliteLineData(calculateLayout,text);
                matchMaxWidthFontSize();
                matchMaxHeightFontSize();
                updateText(text);
                updateTextSize();
                int maxLineWidth = (int) calculateMaxLineWidth();
                if(maxLineWidth > 0){
                    mResetWidgetSize = true;
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = maxLineWidth + getPaddingLeft() +getPaddingRight();
                    Log.d("hyh", "AutomaticEditText: refresh: layoutParams.width="+layoutParams.width);
                    setLayoutParams(layoutParams);
                }
            }
        }
    }

    /**
     * 判断是否需要更新文本
     * @return
     */
    private boolean isUpdateText(Layout layout,String text){
        boolean update = false;
        if(!mLastText.equals(text)) {
            update = true;
        }else{
            int lineCount = layout.getLineCount();
            int size = mLineDataList.size();
            if(lineCount != size){
                update = true;
            }else{
                for (int i = 0; i < lineCount; i++) {
                    int start = layout.getLineStart(i);
                    int end =layout.getLineEnd(i);
                    String rowStr = text.substring(start, end);
                    //去除掉换行符
                    if(rowStr.contains(SYM_CHANGE_LINE)){
                        rowStr = rowStr.replace(SYM_CHANGE_LINE,"");
                    }
                    LineData lineData = mLineDataList.get(i);
                    String lineText = lineData.getLineText();
                    if (!rowStr.equals(lineText)) {
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
    private void spliteLineData(Layout layout,String text){
        mLineDataList.clear();
        int lineCount = layout.getLineCount();
        Log.d("hyh", "AutomaticEditText: spliteLineData: text="+text+" ,lineCount="+lineCount);
        for (int i = 0; i < lineCount; i++) {
            int start = layout.getLineStart(i);
            int end = layout.getLineEnd(i);
            String rowStr = text.substring(start,end);
            //去除掉换行符
            if(rowStr.contains(SYM_CHANGE_LINE)){
                rowStr = rowStr.replace(SYM_CHANGE_LINE,"");
            }
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
    private void matchMaxHeightFontSize(){
        List<CustomSpanData> customTextSpanDataList = new ArrayList<>();
        for(LineData lineData: mLineDataList){
            CustomSpanData customTextSpanData = lineData.getCustomTextSpanData();
            customTextSpanDataList.add(customTextSpanData);
        }
        mLayoutHelper.claculateMatchHeightFontSize(customTextSpanDataList, mMaxTextHeight);
    }

    private void updateText(String text){
        SpannableString spannableString = new SpannableString(text);
        for(LineData lineData: mLineDataList){
            Log.d("hyh", "AutomaticEditText: updateText: lineText="+lineData.getLineText()+" ,lineFontSize="+lineData.getFontSizePx());
            CustomSpanData customSpanData = lineData.getCustomTextSpanData();
            spannableString.setSpan(customSpanData.onCreateSpan(),customSpanData.getStartIndex(),customSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        int start = getSelectionStart();
        setText(spannableString);
        setSelection(start);
    }

    private void updateTextSize(){
        float minFontSize = mLayoutHelper.getFontSize();
        for(LineData lineData: mLineDataList){
            CustomSpanData customSpanData = lineData.getCustomTextSpanData();
            float fontSize = customSpanData.getTextSize();
            if(fontSize < minFontSize){
                minFontSize = fontSize;
            }
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX,minFontSize);
    }

    private float calculateMaxLineWidth(){
        Paint paint = mLayoutHelper.copyPaint();
        float maxLineWidth = 0;
        for(LineData lineData: mLineDataList){
            paint.setTextSize(lineData.getFontSizePx());
            float width = paint.measureText(lineData.getLineText());
            if(width > maxLineWidth){
                maxLineWidth = width;
            }
        }
        if(maxLineWidth > mLayoutHelper.getLayoutWidth()){
            maxLineWidth = mLayoutHelper.getLayoutWidth();
        }
        Log.d("hyh", "AutomaticEditText: calculateMaxLineWidth: maxLineWidth="+maxLineWidth);
        return maxLineWidth;
    }

    private Layout getCalculateLayout(){
        //注意这里的text是String不是Spannable
        String textString = getText().toString();
        return mLayoutHelper.buildCalculateLayout(textString);
    }

    public static class LineData{
        //行文本
        private String mLineText;
        private CustomSpanData mCustomSpanData;

        public LineData(String lineStr, CustomSpanData customSpanData) {
            mLineText = lineStr;
            mCustomSpanData = customSpanData;
        }

        public String getLineText() {
            return mLineText;
        }

        public float getFontSizePx(){
            return mCustomSpanData.getTextSize();
        }

        public void setFontSizePx(float textSizePx){
            mCustomSpanData.setTextSize(UNIT_PX,textSizePx);
        }

        public CustomSpanData getCustomTextSpanData() {
            return mCustomSpanData;
        }

        @Override
        public String toString() {
            return "LineData{" +
                "mLineText='" + mLineText + '\'' +
                ", mStartIndex=" + mCustomSpanData.getStartIndex() +
                ", mEndIndex=" + mCustomSpanData.getEndIndex() +
                '}';
        }
    }
}
