package com.sky.hyh.customviewsamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sky.hyh.customviewsamples.Factory.BaseFragmentFactory;
import com.sky.hyh.customviewsamples.adapter.DataListAdapter;
import com.sky.hyh.customviewsamples.customview.CustomEmojiPanel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecyclerView mRvList = findViewById(R.id.rv_list);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mRvList.setLayoutManager(layoutManager);
//        DataListAdapter dataListAdapter = new DataListAdapter(this, new DataListAdapter.OnClickCallBack() {
//            @Override
//            public void onClick(BaseFragmentFactory baseFragmentFactory) {
//                baseFragmentFactory.createFragment();
//            }
//        });
//        mRvList.setAdapter(dataListAdapter);

        CustomEmojiPanel customEmojiPanel = findViewById(R.id.cmp_emoji);

        customEmojiPanel.setItemCountPerLine(5);
        customEmojiPanel.setEmojiList(new String[]{
                "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊", "😋", "😎",
                "😄", "😅", "😆", "😉", "😊", "😋", "😎"
        });
        customEmojiPanel.setEmojiSize(26);
        customEmojiPanel.setButtonMoreSize(40);
    }
}
