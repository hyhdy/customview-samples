package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * 可以限制最大输入行数的EditText，
 * 当输入的文字大于最大行数时会自动截断多余的文字，
 * 截断过程不会触发视图重绘，用户无感知，达到完美体验
 */
public class LimitedLinesEditText extends AppCompatEditText {
    public static final int MAX_INPUT_LINES = 3;
    private TextPaint mTextPaint;
    /**
     * 输入框单行最大宽度
     */
    private float mSingleMaxWidth;
    /**
     * 输入框单行最小宽度
     */
    private float mSingleMinWidth;

    public LimitedLinesEditText(Context context) {
        this(context,null);
    }

    public LimitedLinesEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        initListeners();
    }

    private void initViews() {
        //这两个属性必须设置
        setHorizontallyScrolling(false);
        setMaxLines(Integer.MAX_VALUE);

        mTextPaint = getPaint();
        //默认单行最大宽度为填满10个中文符号宽度，多余的部分换行显示
        mSingleMaxWidth = mTextPaint.measureText("我是占位用的拉拉拉拉")+getPaddingLeft()+getPaddingRight();
        setMaxWidth((int) mSingleMaxWidth);
        //默认单行最小宽度为填满6个中文字符的宽度
        mSingleMinWidth = mTextPaint.measureText("我是占位用的")+getPaddingLeft()+getPaddingRight();
        setMinWidth((int) mSingleMinWidth);

        requestFocus();
    }

    private void initListeners() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //计算此时输入框的宽度
                float width = mTextPaint.measureText(s.toString())+getPaddingLeft()+getPaddingRight();
                //计算此时应该显示几行
                int lineCountMeasured = (int) (width / mSingleMaxWidth) + 1;
                if (lineCountMeasured == 1) {
                    //当只有1行时需要根据输入的内容动态调整EditText宽度
                    if (width > mSingleMinWidth) {
                        setWidth((int) width);
                    }
                }
                if (lineCountMeasured > 1) {
                    //当大于1行时需要固定EditText的宽度，这样通过EditText.getLineCount()方法获取的行数才是正确的
                    setWidth((int) mSingleMaxWidth);
                }

                int lineCount = getLineCount();
                if (lineCount > LimitedLinesEditText.MAX_INPUT_LINES) {
                    //发现输入的内容大于最大行数，则删除多余的内容
                    deleteExtra();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void deleteExtra() {
        String str = getText().toString();
        str = str.substring(0, str.length() - 1);
        setText(str);
        setSelection(getText().length());
    }
}
