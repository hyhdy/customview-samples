package com.sky.hyh.customviewsamples.fragment.impl;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.CustomEditText;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

/**
 * Created by hyh on 2018/10/8 13:29
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class TextViewTestFragment extends BaseFragment {
    private Button mBtnSend;
    private RelativeLayout mRlContent;
    private int mLineCount;

    @Override
    protected int getResId() {
        return R.layout.fragment_textview_test;
    }

    @Override
    protected void initViews(View rootView) {
        mRlContent = rootView.findViewById(R.id.rl_content);

        mBtnSend = rootView.findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomEditText customEditText = new CustomEditText(getContext());
                customEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        Log.d("hyh", "TextViewTestFragment: beforeTextChanged: s="+s+" ,start="+start+" ,count="+count+" ,after="+after);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        int lineCount = customEditText.getLineCount();
//                        Log.d("hyh", "TextViewTestFragment: onTextChanged: lineCount="+lineCount+" ,mLineCount="+mLineCount);
//                        if(lineCount!=mLineCount){
//                            Log.d("hcamera", "TextViewTestFragment: onTextChanged: 1");
//                            mLineCount = lineCount;
//                            customEditText.setText(customEditText.getText());
//                            customEditText.setSelection(start+1);
//                        }
//                        Log.d("hyh", "TextViewTestFragment: onTextChanged: s="+s+" ,start="+start+" ,before="+before+" ,count="+count);

//                        String str = s.toString();
//                        String[] lineStr = str.split("\n");
//                        StringBuilder result = new StringBuilder();
//                        int n = 0;
//                        for(int i=0;i<lineStr.length;i++){
//                            StringBuilder stringBuilder = new StringBuilder(lineStr[i]);
//                            if(stringBuilder.length() == 0 || stringBuilder.charAt(0)!=' '){
//                                n++;
//                                stringBuilder.insert(0," ");
//                            }
//                            if(lineStr.length!=(i+1)) {
//                                result.append(stringBuilder+"\n");
//                            }else{
//                                result.append(stringBuilder);
//                            }
//                        }
//                        if(n>0) {
//                            customEditText.setText(result.toString());
//                            customEditText.setSelection(start + 2);
//                        }

//                        char target = s.charAt(start);
//                        if (target == '\n') {
//                            if((start+1)<s.length()){
//                                char next = s.charAt(start + 1);
//                                if (next != ' ') {
//                                    Log.d("hyh", "TextViewTestFragment: onTextChanged: 增加空白符");
//                                    String text = customEditText.getText().toString();
//                                    StringBuilder stringBuilder = new StringBuilder(text);
//                                    stringBuilder.insert(start+1," ");
//                                    customEditText.setText(stringBuilder.toString());
//                                    customEditText.setSelection(start+1);
//                                }
//                            }else{
//                                Log.d("hyh", "TextViewTestFragment: onTextChanged: 增加空白符 2");
//                                String text = customEditText.getText().toString();
//                                StringBuilder stringBuilder = new StringBuilder(text);
//                                stringBuilder.insert(start+1," ");
//                                customEditText.setText(stringBuilder.toString());
//                                customEditText.setSelection(start+1);
//                            }
//                        }

//                        String str = s.toString();
//                        int index = str.lastIndexOf(" ");
//                        Log.d("hyh", "TextViewTestFragment: onTextChanged: str="+str+" ,index="+index);
//                        if(index != (str.length()-1)){
//                            String text = String.format("%s%s",customEditText.getText()," ");
//                            customEditText.setText(text);
//                            customEditText.setSelection(customEditText.getText().length()-1);
//                            Log.d("hyh", "TextViewTestFragment: onTextChanged: 增加空白符");
//                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        Log.d("hyh", "TextViewTestFragment: afterTextChanged: s="+s);
                    }
                });
                customEditText.requestFocus();
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                }
                customEditText.setTextSize(20);
                customEditText.setGravity(Gravity.TOP);
                customEditText.setLineSpacing(0,1.6f);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                customEditText.setLayoutParams(params);
                customEditText.setIncludeFontPadding(true);

                mRlContent.addView(customEditText);
            }
        });
    }
}
