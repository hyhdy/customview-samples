package com.sky.hyh.customviewsamples.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * 可以限制最大输入行数的EditText，文本宽度固定
 * 当输入的文字大于最大行数时会自动截断多余的文字，
 * 截断过程不会触发视图重绘，用户无感知，达到完美体验
 */
public class ConLimitedLinesEditText extends AppCompatEditText {
    public static final int MAX_INPUT_LINES = 3;

    public ConLimitedLinesEditText(Context context) {
        this(context,null);
    }

    public ConLimitedLinesEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        initListeners();
    }

    private void initViews() {
        requestFocus();
    }

    private void initListeners() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int lineCount = getLineCount();
                if (lineCount > MAX_INPUT_LINES) {
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
