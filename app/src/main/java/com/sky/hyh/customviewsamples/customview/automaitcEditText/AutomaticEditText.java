package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
    public static final float DEF_FONT_SIZE = 16;
    /**
     * 最大文本高度
     */
    private int mMaxTextHeight;
    /**
     * 最大文本宽度
     */
    private int mMaxTextWidth;
    /**
     * 默认字体大小，单位：px
     */
    private float mDefFontSizePx;

    private List<LineData> mLineDataList;
    private String mLastText = "";

    public AutomaticEditText(Context context) {
        this(context,null);
    }

    public AutomaticEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setTextSize(DEF_FONT_SIZE);
        mDefFontSizePx = SizeUtils.sp2px(DEF_FONT_SIZE);
        Log.d("hyh", "AutomaticEditText: init: mDefFontSizePx="+mDefFontSizePx);
        mLineDataList = new ArrayList<>();
        //设为false，且不设置行间距，这样每行高度的累加值才等于文本总高度，即Layout.getHeight == Layout.getLineCount * TextPaint.getFontMetricsInt
        setIncludeFontPadding(false);
        //不设置行间距
        setLineSpacing(0,1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w != oldw || h != oldh){
            mMaxTextHeight = h - getPaddingTop() - getPaddingBottom() - SizeUtils.dp2px(HEIGHT_OFFSET);
            mMaxTextWidth = w - getPaddingLeft() - getPaddingRight() - SizeUtils.dp2px(WIDTH_OFFSET);
            Log.d("hyh", "AutomaticEditText: onSizeChanged: mMaxTextHeight="+ mMaxTextHeight+ " ,mMaxTextWidth="+mMaxTextWidth);
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
            //注意这里需要构建一个text不是Spannable，字体大小是默认字体大小的Layout，这样才能准确得出每行应该显示的文本
            Layout defLayout = getDefLayout();
            String text = defLayout.getText().toString();
            Log.d("hyh", "AutomaticEditText: refresh: text="+text);
            boolean update = isUpdateText(defLayout);
            Log.d("hyh", "AutomaticEditText: refresh: update="+update);
            if(update) {
                spliteLineData(defLayout);
                matchMaxWidthFontSize();
                matchMaxHeightFontSize();
                updateText(text);
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
    private void spliteLineData(Layout layout){
        mLineDataList.clear();
        String text = layout.getText().toString();
        int lineCount = layout.getLineCount();
        Log.d("hyh", "AutomaticEditText: spliteLineData: text="+text+" ,lineCount="+lineCount);
        for (int i = 0; i < lineCount; i++) {
            int start = layout.getLineStart(i);
            int end = layout.getLineEnd(i);
            String rowStr = text.substring(start,end);
            LineData lineData = new LineData(getPaint(),rowStr,start,end);
            Log.d("hyh", "AutomaticEditText: spliteLineData: lineData="+lineData.toString());
            mLineDataList.add(lineData);
        }
    }

    /**
     * 计算匹配最大文本宽度的字体大小
     */
    private void matchMaxWidthFontSize(){
        for(LineData lineData: mLineDataList){
            Paint paint = lineData.getPaint();
            String lineText = lineData.getLineText();
            float fontSize = paint.getTextSize();
            if(!TextUtils.isEmpty(lineText)){
                float textWidth = paint.measureText(lineText);
                Log.d("hyh", "AutomaticEditText: matchMaxWidthFontSize: lineText="+lineText+" ,textWidth="+textWidth);
                fontSize = mMaxTextWidth / textWidth * fontSize;
                Log.d("hyh", "AutomaticEditText: matchMaxWidthFontSize: fontSize="+fontSize);
            }
            paint.setTextSize(fontSize);
            TextSizeAdjustHelper.calculateMatchWidthSize(paint,lineText,mMaxTextWidth);
        }
    }

    /**
     * 计算匹配最大文本高度的字体大小
     */
    private void matchMaxHeightFontSize(){
        List<Paint> paintList = new ArrayList<>();
        for(LineData lineData: mLineDataList){
            paintList.add(lineData.getPaint());
        }
        TextSizeAdjustHelper.calculateMatchHeightSize(paintList,mMaxTextHeight);
    }

    private void updateText(String text){
        Log.d("hyh", "AutomaticEditText: updateText: text="+text);
        SpannableString spannableString = new SpannableString(text);
        for(LineData lineData: mLineDataList){
            Log.d("hyh", "AutomaticEditText: updateText: lineText="+lineData.getLineText()+" ,lineFontSize="+lineData.getFontSizePx());
            CustomTextSpanData customTextSpanData = new CustomTextSpanData.Builder(lineData.getStartIndex(),lineData.getEndIndex())
                .setTextSize(lineData.getFontSizeSp())
                .build();
            spannableString.setSpan(customTextSpanData.onCreateSpan(),customTextSpanData.getStartIndex(),customTextSpanData.getEndIndex(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
        setSelection(text.length());
    }

    private Layout getDefLayout(){
        //注意这里的text是String不是Spannable
        String textString = getText().toString();
        DynamicLayout dynamicLayout = new DynamicLayout(textString,new TextPaint(getPaint()),getLayout().getWidth(),getLayout().getAlignment(),getLayout().getSpacingMultiplier(),getLayout().getSpacingAdd(),getIncludeFontPadding());
        return dynamicLayout;
    }

    public static class LineData{
        private Paint mPaint;
        //行文本
        private String mLineText;
        private int mStartIndex;
        private int mEndIndex;

        public LineData(Paint paint, String lineStr, int startIndex, int endIndex) {
            mPaint = new Paint(paint);
            mLineText = lineStr;
            mStartIndex = startIndex;
            mEndIndex = endIndex;
        }

        public Paint getPaint() {
            return mPaint;
        }

        public String getLineText() {
            return mLineText;
        }

        public int getStartIndex() {
            return mStartIndex;
        }

        public int getEndIndex() {
            return mEndIndex;
        }

        public float getFontSizeSp(){
            return SizeUtils.px2sp(mPaint.getTextSize());
        }

        public float getFontSizePx(){
            return mPaint.getTextSize();
        }

        @Override
        public String toString() {
            return "LineData{" +
                "mLineText='" + mLineText + '\'' +
                ", mStartIndex=" + mStartIndex +
                ", mEndIndex=" + mEndIndex +
                '}';
        }
    }
}
