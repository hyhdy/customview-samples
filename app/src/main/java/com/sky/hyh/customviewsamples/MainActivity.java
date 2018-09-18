package com.sky.hyh.customviewsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sky.hyh.customviewsamples.customview.CustomEmojiPanel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomEmojiPanel customEmojiPanel = findViewById(R.id.cmp_emoji);
        //customEmojiPanel.setLineCount(5);
        //customEmojiPanel.setItemCountPerLine(3);

        customEmojiPanel.setEmojiList(new String[]{
                "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊", "😋", "😎",
                "😄", "😅", "😆", "😉", "😊", "😋", "😎"
        });
        customEmojiPanel.setEmojiSize(26);
        customEmojiPanel.setButtonMoreSize(40);
    }
}
