package com.sky.hyh.customviewsamples.fragment.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.hyh.customviewsamples.R;
import com.sky.hyh.customviewsamples.customview.CustomEmojiPanel;
import com.sky.hyh.customviewsamples.fragment.BaseFragment;

public class CustomEmojiPanelFragment extends BaseFragment {
    public static final int RES_ID = R.layout.fragment_custom_emoji_panel;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("hyh", "CustomEmojiPanelFragment: onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("hyh", "CustomEmojiPanelFragment: onCreate");
    }

    @Override
    protected int getResId() {
        return RES_ID;
    }

    @Override
    protected void initViews(View rootView) {
        CustomEmojiPanel customEmojiPanel = rootView.findViewById(R.id.cmp_emoji);
        customEmojiPanel.setItemCountPerLine(5);
        customEmojiPanel.setEmojiList(new String[]{
                "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊", "😋", "😎",
                "😄", "😅", "😆", "😉", "😊", "😋", "😎"
        });
        customEmojiPanel.setEmojiSize(26);
        customEmojiPanel.setButtonMoreSize(40);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("hyh", "CustomEmojiPanelFragment: onActivityCreated ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("hyh", "CustomEmojiPanelFragment: onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("hyh", "CustomEmojiPanelFragment: onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("hyh", "CustomEmojiPanelFragment: onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("hyh", "CustomEmojiPanelFragment: onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("hyh", "CustomEmojiPanelFragment: onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hyh", "CustomEmojiPanelFragment: onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("hyh", "CustomEmojiPanelFragment: onDetach ");
    }
}
